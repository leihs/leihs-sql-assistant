(ns leihs.sql-assistant.handler
  (:require
    [leihs.core.anti-csrf.back :as anti-csrf]
    [leihs.core.ds :as ds]
    [leihs.core.ring-exception :as ring-exception]
    [leihs.core.routing.back :as routing]
    [leihs.core.routing.dispatch-content-type :as dispatch-content-type]
    [leihs.core.auth.core :as auth]
    [leihs.core.shutdown :as shutdown]

    [leihs.sql-assistant.execute :as execute]
    [leihs.sql-assistant.paths :refer [paths]]
    [leihs.sql-assistant.status :as status]

    [clj-logging-config.log4j :as logging-config]

    [compojure.core :as cpj]
    [ring.middleware.content-type :refer [wrap-content-type]]
    [ring.middleware.cookies]
    [ring.middleware.json]
    [ring.middleware.params]
    [ring.util.response :refer [redirect]]

    [clojure.tools.logging :as log]
    [logbug.catcher :as catcher]
    [logbug.debug :as debug :refer [I>]]
    [logbug.ring :refer [wrap-handler-with-logging]]
    [logbug.thrown :as thrown]
    ))

; (def skip-authorization-handler-keys
;   #{:status})

(def resolve-table
  {:execute execute/routes
   :status status/routes})

(defn wrap-default-content-type
  [handler]
  (fn [request]
    (let [response (handler request)]
      (if (-> response :headers (get "Content-Type"))
        response
        (assoc-in response [:headers "Content-Type"] "text/html")))))

(defn init []
  (routing/init paths resolve-table)
  (-> routing/dispatch-to-handler
      ; (authorization/wrap skip-authorization-handler-keys)
      anti-csrf/wrap
      ; auth/wrap-authenticate
      ring.middleware.cookies/wrap-cookies
      routing/wrap-empty
      ; settings/wrap
      ds/wrap-tx
      status/wrap
      ring.middleware.json/wrap-json-response
      (ring.middleware.json/wrap-json-body {:keywords? true})
      ; dispatch-content-type/wrap-accept
      routing/wrap-add-vary-header
      routing/wrap-resolve-handler
      wrap-handler-with-logging
      routing/wrap-canonicalize-params-maps
      ring.middleware.params/wrap-params
      ; NOTE: we don't like `application/octet-stream` from `wrap-content-type`
      wrap-default-content-type
      ; (wrap-resource
      ;   "public" {:allow-symlinks? true
      ;             :cache-bust-paths ["/my/css/site.css"
      ;                                "/my/css/site.min.css"
      ;                                "/my/leihs-shared-bundle.js"
      ;                                "/my/js/app.js"]
      ;             :no-expire-paths [#".*font-awesome-[^\/]*\d\.\d\.\d\/.*"
      ;                                  #".+_[0-9a-f]{40}\..+"]
      ;             :enabled? (= env/env :prod)})
      ring-exception/wrap
      ))

;#### debug ###################################################################
;(logging-config/set-logger! :level :debug)
;(logging-config/set-logger! :level :info)
; (debug/debug-ns *ns*)
