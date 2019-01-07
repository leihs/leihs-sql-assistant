(ns leihs.sql-assistant.execute
  (:refer-clojure :exclude [get])
  (:require [clojure.tools.logging :as log]
            [clojure.java.jdbc :as jdbc]
            [leihs.core.ds :refer [get-ds]]
            cheshire.core
            [compojure.core :as cpj]
            [hiccup.core :refer [html]]
            [leihs.sql-assistant.paths :refer [path]]))

(defn form
  ([] (form nil))
  ([value]
   [:form {:action "/sql/execute", :method :post}
    [:textarea {:name :sql} value]
    [:br]
    [:button {:type :submit} "Execute"]]))

(defn get [request]
  {:status 200
   :body (html (form))})

(defn post [request]
  (let [sql (-> request :params :sql)
        result (->> [sql]
                    (jdbc/query (get-ds))
                    cheshire.core/generate-string)]
    {:status 200
     :body (html
            [:div
             (form sql)
             [:br]
             result])}))

(def routes
  (cpj/routes
    (cpj/GET (path :execute) [] #'get)
    (cpj/POST (path :execute) [] #'post)))
