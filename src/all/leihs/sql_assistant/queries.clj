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

(defn pg-cancel-query [pid]
  (-> (sql/select (sql/call :pg_cancel_backend pid))
      sql/format
      (->> (jdbc/query (get-ds)))
      ))

(defn pg-find-active-query [query-id]
  (-> (sql/select :*)
      (sql/from :pg_stat_activity)
      (sql/where [:and
                  ["~~*" :query (str "%" query-id "%")]
                  [:not ["~~*" :query "%pg_stat_activity%"]]
                  [:= :state "active"]])
      sql/format
      (->> (jdbc/query (get-ds)))
      first))

(defn abort-query [{{query-id :query-id} :route-params}]
  (when-let [pg-q (pg-find-active-query query-id)]
    (pg-cancel-query (:pid pg-q)))
  {:status 204})

(defn query [sql]
  (jdbc/query (get-ds) [sql]))

(defn new-query [{{q :query, s :sleep} :params}]
  (let [uuid (str (cdg/uuid))
        q-with-uuid (str "WITH query_id AS (SELECT '" uuid "') " q)]
    (-> (response
          (ring-io/piped-input-stream
            #(do (Thread/sleep (or s 0))
                 (->> (io/make-writer % {})
                      (json/generate-stream (query q-with-uuid))))))
        (header "X-Query-Id" uuid))))

(def routes
  (cpj/routes
    (cpj/POST (path :queries) [] #'new-query)
    (cpj/POST (path :abort-query {:query-id ":query-id"}) [] #'abort-query)))
