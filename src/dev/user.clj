(ns user
  (:require [clojure.tools.logging :as log]
            [clojure.tools.namespace.repl :refer [refresh refresh-all]]
            [leihs.sql-assistant.main :refer [-main]]
            [leihs.core.http-server :as http-server]))

(defn start []
  (-main "run"))

(defn reset []
  (some-> @http-server/_server .close)
  (when-let [ex (refresh :after 'user/start)] 
    (clojure.repl/pst ex)))
