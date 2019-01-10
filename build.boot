(set-env!
  :source-paths  #{"boot/" "src/all" "src/dev" "leihs-clj-shared/src"}
  :resource-paths #{"resources"}
  :project 'leihs-sql-assistant
  :version "0.1.0-SNAPSHOT"
  :dependencies '[
                  [aleph "0.4.6"]
                  [bidi "2.1.3"]
                  [cheshire "5.8.0"]
                  [clojure-humanize "0.2.2"]
                  [com.github.mfornos/humanize-slim "1.2.2"]
                  [compojure "1.6.1"]
                  [hiccup "1.0.5"]
                  [hikari-cp "1.8.3"]
                  [honeysql "0.9.4"]
                  [environ "1.1.0"]
                  [io.dropwizard.metrics/metrics-core "4.0.3"]
                  [io.dropwizard.metrics/metrics-healthchecks "4.0.3"]
                  [io.forward/yaml "1.0.9"]
                  [log4j/log4j "1.2.17" :exclusions [javax.mail/mail
                                                     javax.jms/jms
                                                     com.sun.jdmk/jmxtools
                                                     com.sun.jmx/jmxri]]
                  [logbug "4.2.2"]
                  [nilenso/honeysql-postgres "0.2.4"]
                  [org.bouncycastle/bcprov-jdk15on "1.54"]
                  [org.clojure/data.generators "0.1.2"]
                  [org.clojure/java.jdbc "0.7.8"]
                  [org.clojure/tools.cli "0.3.7"]
                  [org.clojure/tools.logging "0.4.1"]
                  [org.clojure/tools.namespace "0.3.0-alpha4"]
                  [org.slf4j/slf4j-log4j12 "1.7.25"]
                  [pandect "0.6.1"]
                  [pg-types "2.4.0-PRE.1"]
                  [ring "1.6.3"]
                  [ring-middleware-accept "2.0.3"]
                  [ring/ring-json "0.4.0"]
                  [ring/ring-jetty-adapter "1.7.1"]
                  ])

(task-options!
  target {:dir #{"target"}}
  aot {:all true}
  sift {:include #{#"leihs-sql-assistant.jar"}}
  jar {:file "leihs-sql-assistant.jar"
       :main 'leihs.sql-assistant.main})

(deftask uberjar
  "Build an uberjar of the application."
  []
  (comp (aot) (uber) (jar) (sift) (target))) 

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;; DEV ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(require 'app)
(require '[clojure.tools.namespace.repl :as ctnr])
(deftask reset
  "Reload all changed namespaces on the classpath
  and reset the application state continuously."
  []
  (with-pass-thru _
    (apply ctnr/set-refresh-dirs (get-env :directories))
    (with-bindings {#'*ns* *ns*}
      (app/reset))))

(deftask focus
  []
  (comp (repl "-s") (watch) (reset)))

(require 'leihs.sql-assistant.main)
(deftask run
  "Run the application with given opts."
  []
  (->> *args*
       (cons "run")
       (apply leihs.sql-assistant.main/-main))
  (wait))
