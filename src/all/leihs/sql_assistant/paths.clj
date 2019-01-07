(ns leihs.sql-assistant.paths
  (:require leihs.core.paths
            [bidi.verbose :refer [branch param leaf]]))

(def paths
  (branch ""
          leihs.core.paths/core-paths
          (branch "/sql"
                  (leaf "/execute" :execute))))

(reset! leihs.core.paths/paths* paths)

(def path leihs.core.paths/path)
