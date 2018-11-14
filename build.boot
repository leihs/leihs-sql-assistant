(set-env!
  :source-paths #{"src" "leihs-clj-shared/src"}
  :dependencies '[[hikari-cp "2.6.0"]
                  [honeysql "0.9.4"]
                  [io.dropwizard.metrics/metrics-core "4.0.3"]
                  [io.dropwizard.metrics/metrics-healthchecks "4.0.3"]
                  [log4j/log4j "1.2.17" :exclusions [javax.mail/mail
                                                     javax.jms/jms
                                                     com.sun.jdmk/jmxtools
                                                     com.sun.jmx/jmxri]]
                  [logbug "4.2.2"]
                  [org.clojure/java.jdbc "0.7.8"]
                  [org.clojure/tools.logging "0.4.1"]
                  [org.slf4j/slf4j-log4j12 "1.7.25"]
                  [pg-types "2.4.0-PRE.1"]
                  [ring/ring-core "1.6.3"]])

(task-options!
  pom {:project 'leihs-sql-assistant
       :version "0.1.0"})
