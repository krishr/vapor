(ns vapor.core
  (:require [org.httpkit.server :refer (run-server)]
            [compojure.core :refer (defroutes context GET POST)]
            [compojure.handler :as handler]
            [compojure.route :refer (resources not-found)]
            [ring.middleware.json :as json]
            [ring.middleware.reload :as reload]
            [vapor.db :as db])
  (:gen-class))

(def version "pre-alpha")

(defroutes api-routes
  (GET "/" [] "Hello World!!!!!")
  (GET "/list" []
    {:status 200
     :headers {"Content-Type" "application/clojure; charset=utf-8"}
            :body (pr-str (db/list-fruits))}))

(defroutes app-routes
  (context "/api" [] api-routes)
  (resources "/")
  (not-found "Page not found"))

(defn version-middleware [handler]
  (fn [req]
    (let [resp (handler req)
          headers (:headers resp)]
      (assoc resp :headers
             (assoc headers "X-APP-INFO" version)))))
(def app
  (-> (handler/api app-routes)
      (reload/wrap-reload '(vapor.core))
      (version-middleware)))

(def server (atom nil))

(defn start-server [port]
  (swap! server
         (fn [o]
           (run-server app {:port port}))))

(defn stop-server []
  (when (not (nil? @server))
    (@server)
    (swap! server (fn [o] nil))))

(defn -main [ & args]
  (start-server (Integer/parseInt (first args))))
