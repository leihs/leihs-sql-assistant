(ns scratch
  (:require [clojure.java.jdbc :as jdbc]
            [honeysql.core :as sql]
            [honeysql.helpers :as helpers]
            [leihs.core.ds :as ds]
            [leihs.sql-assistant.run :as run]))

(comment (run/init))

(-> (helpers/select :*)
    (helpers/from :users)
    (helpers/limit 1)
    sql/format
    (->> (jdbc/query (ds/get-ds))))
