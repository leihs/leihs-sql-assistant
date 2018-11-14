(set-env!
  :resource-paths #{"src"}
  :dependencies '[[honeysql "0.9.4"]
                  [org.clojure/java.jdbc "0.7.8"]])

(task-options!
  pom {:project 'leihs-sql-assistant
       :version "0.1.0"})
