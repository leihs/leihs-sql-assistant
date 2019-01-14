(ns app
  (:require [clojure.tools.logging :as log]
            [clojure.tools.namespace.repl :refer [refresh]]
            [leihs.sql-assistant.main :refer [-main]]))

(defn run []
  (-main "run"))

(defn reset []
  (when-let [ex (refresh :after 'app/run)] 
    (clojure.repl/pst ex)))
