(ns leihs.sql-assistant.run
  (:require [environ.core :refer [env]]
            [leihs.core.ds :as ds]))

(defn init []
  (ds/init {:username nil
            :password nil
            :database "leihs_v5_prod"
            :host "localhost"
            :port 5432}
           nil))
