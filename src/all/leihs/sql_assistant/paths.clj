(ns leihs.sql-assistant.paths
  (:require leihs.core.paths
            [bidi.verbose :refer [branch param leaf]]))

(def paths
  (branch ""
          leihs.core.paths/core-paths
          (branch "/sql/queries"
                  (leaf "" :queries)
                  (branch "/" (param :query-id)
                          (leaf "/abort" :abort-query)))))

(reset! leihs.core.paths/paths* paths)

(def path leihs.core.paths/path)
