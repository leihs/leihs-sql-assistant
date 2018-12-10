(set-env!
  :source-paths #{"src" "leihs-clj-shared/src"}
  :resource-paths #{"resources"}
  :dependencies '[[aleph "0.4.6"]
                  [bidi "2.1.3"]
                  [cheshire "5.8.0"]
                  [clojure-humanize "0.2.2"]
                  [com.github.mfornos/humanize-slim "1.2.2"]
                  [compojure "1.6.1"]
                  [hikari-cp "1.8.3"]
                  [honeysql "0.9.4"]
                  [environ "1.1.0"]
                  [io.dropwizard.metrics/metrics-core "4.0.3"]
                  [io.dropwizard.metrics/metrics-healthchecks "4.0.3"]
                  [log4j/log4j "1.2.17" :exclusions [javax.mail/mail
                                                     javax.jms/jms
                                                     com.sun.jdmk/jmxtools
                                                     com.sun.jmx/jmxri]]
                  [logbug "4.2.2"]
                  [org.clojure/java.jdbc "0.7.8"]
                  [org.clojure/tools.cli "0.3.7"]
                  [org.clojure/tools.logging "0.4.1"]
                  [io.forward/yaml "1.0.9"]
                  [org.slf4j/slf4j-log4j12 "1.7.25"]
                  [pg-types "2.4.0-PRE.1"]
                  [ring "1.6.3"]])

(task-options!
  pom {:project 'leihs-sql-assistant
       :version "0.1.0"}
  repl {:init-ns 'leihs.sql-assistant.main}
  jar {:main 'leihs.sql-assistant.main})
