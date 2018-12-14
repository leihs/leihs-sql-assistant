(ns leihs.sql-assistant.execute
  (:refer-clojure :exclude [get])
  (:require [clojure.tools.logging :as log]
            [compojure.core :as cpj]
            [hiccup.core :refer [html]]
            [leihs.sql-assistant.paths :refer [path]]))

(defn get [request]
  {:status 200
   :body (html
           [:form {:action "/sql/execute", :method :post}
            [:textarea {:name :sql}]
            [:br]
            [:button {:type :submit} "Execute Nitaai"]])})

(defn post [request]
  (let [sql (-> request :params :sql)]
    {:status 200
     :body "ok"}))

(def routes
  (cpj/routes
    (cpj/GET (path :execute) [] #'get)
    (cpj/POST (path :execute) [] #'post)))
