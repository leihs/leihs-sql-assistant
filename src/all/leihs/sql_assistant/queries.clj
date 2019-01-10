(ns leihs.sql-assistant.queries
  (:require [clojure.data.generators :as cdg]
            [clojure.tools.logging :as log]
            [clojure.java.io :as io]
            [clojure.java.jdbc :as jdbc]
            [leihs.core.ds :refer [get-ds]]
            [leihs.core.sql :as sql]
            [cheshire.core :as json]
            [compojure.core :as cpj]
            [hiccup.core :refer [html]]
            [leihs.sql-assistant.paths :refer [path]]
            [ring.util.io :as ring-io]
            [ring.util.response :refer [header response]])
  (:import java.io.OutputStreamWriter
           java.io.BufferedWriter))

; (defn form
;   ([] (form nil))
;   ([value]
;    [:form {:action "/sql/execute", :method :post}
;     [:textarea {:name :sql} value]
;     [:br]
;     [:button {:type :submit} "Execute"]]))

(defn cancel-pg-query [pid]
  (-> (sql/select (sql/call :pg_cancel_backend pid))
      sql/format
      (->> (jdbc/query (get-ds)))))

(defn find-pg-query [query-id]
  (-> (sql/select :*)
      (sql/from :pg_stat_activity)
      (sql/where [:and
                  ["~~*" :query (str "%" query-id "%")]
                  [:not ["~~*" :query "%pg_stat_activity%"]]])
      sql/format
      (->> (jdbc/query (get-ds)))
      first))

(defn abort-query [{{query-id :query-id} :query-params}]
  (log/debug
    (-> (sql/select :*)
        (sql/from :pg_stat_activity)
        (sql/where [:and
                    ["~~*" :query (str "%" query-id "%")]
                    [:not ["~~*" :query "%pg_stat_activity%"]]])
        sql/format
        (->> (jdbc/query (get-ds)))))
  (when-let [pg-q (find-pg-query query-id)]
    (log/debug pg-q)
    (cancel-pg-query (:pid pg-q)))
  {:status 201})

(defn query [sql]
  (jdbc/query (get-ds) [sql]))

(defn new-query [request]
  (let [q (-> request :params :query)
        uuid (str (cdg/uuid))
        q-with-uuid (str q " -- " uuid)]
    (-> (response
          (ring-io/piped-input-stream
            #(->> (io/make-writer % {})
                  (json/generate-stream (query q)))))
        (header "X-Query-Id" uuid))))

(def routes
  (cpj/routes
    (cpj/POST (path :queries) [] #'new-query)
    (cpj/POST (path :abort-query {:query-id ":query-id"}) [] #'abort-query)))
