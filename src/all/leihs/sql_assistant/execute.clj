(ns leihs.sql-assistant.execute
  (:refer-clojure :exclude [get])
  (:require [clojure.tools.logging :as log]
            [clojure.java.jdbc :as jdbc]
            [leihs.core.ds :refer [get-ds]]
            [compojure.core :as cpj]
            [hiccup.core :refer [html]]
            [leihs.sql-assistant.paths :refer [path]]))

(def form [:form {:action "/sql/execute", :method :post}
           [:textarea {:name :sql}]
           [:br]
           [:button {:type :submit} "Execute"]])

(defn get [request]
  {:status 200
   :body (html form)})

(defn post [request]
  (let [sql (-> request :params :sql)
        result (jdbc/query (get-ds) [sql])]
    {:status 200
     :body (html
            [:div
             form
             [:br]
             result])}))

(def routes
  (cpj/routes
    (cpj/GET (path :execute) [] #'get)
    (cpj/POST (path :execute) [] #'post)))
