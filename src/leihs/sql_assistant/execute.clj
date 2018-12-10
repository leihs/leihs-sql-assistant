(ns leihs.sql-assistant.execute
  (:refer-clojure :exclude [get])
  (:require [compojure.core :as cpj]
            [leihs.sql-assistant.paths :refer [path]]))

(defn get [request]
  {:status 200
   :body "ok"})

(defn post [request]
  {:status 200
   :body "ok"})

(def routes
  (cpj/routes
    (cpj/GET (path :execute) [] #'get)
    (cpj/POST (path :execute) [] #'post)))
