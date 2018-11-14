(ns scratch
  (:require [honeysql.core :as sql]
            [honeysql.helpers :as helpers]))

(comment
  (-> (helpers/select :*)
      (helpers/from :users)
      sql/format))
