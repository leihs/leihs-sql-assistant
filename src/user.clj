(ns user
  (:require [clojure.tools.namespace.repl :refer [refresh refresh-all]]
            [leihs.sql-assistant.main :refer [-main]]
            [leihs.core.http-server :as http-server]))

(def http-server nil)

(defn start []
  (alter-var-root #'http-server
                  (fn [_]
                    (-main "run")
                    @http-server/_server)))

(defn reset []
  (.close http-server)
  (refresh :after 'user/start))
