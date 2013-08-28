(ns crud.core
  (:require [enfocus.core :as ef]
            [enfocus.events :as events]
            [enfocus.effects :as effects]
            [clojure.browser.repl :as repl])
  (:require-macros [enfocus.macros :as em]))

(defn log [data & body]
    (if (empty? body)
      (.log js/console data)
      (.log js/console (str data body))))

(defn get-hostname [] (.-hostname (.-location js/window)))

(defn repl-connect []
  (repl/connect (str "http://" (get-hostname) ":9000/repl")))

(defn start []
  (log "Initialized.")
  )

(set! (.-onload js/window) start)
