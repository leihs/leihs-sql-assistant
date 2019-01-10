(ns app
  (:require [clojure.tools.logging :as log]
            [clojure.tools.namespace.repl :refer [refresh]]
            [leihs.sql-assistant.main :refer [-main]]
            [leihs.core.http-server :as http-server]
            [clojure.java.jdbc :as jdbc]
            [boot.util :refer [dbug info]]
            [leihs.core.ds :refer [get-ds]]))

(defn start []
  (-main "run"))

(defn stop []
  (some-> @http-server/_server .close))

(defn reset []
  (stop)
  (when-let [ex (refresh :after 'app/start)] 
    (clojure.repl/pst ex)))
