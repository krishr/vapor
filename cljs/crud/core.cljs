(ns crud.core
  (:require [clojure.browser.repl :as repl]
            [jayq.core :as jq])
  (:require-macros [enfocus.macros :as em]))

(defn log [data & body]
  (if (empty? body)
    (.log js/console data)
    (.log js/console (str data body))))

(defn get-hostname [] (.-hostname (.-location js/window)))

(defn repl-connect []
  (repl/connect (str "http://" (get-hostname) ":9000/repl")))

(defn list-fruits []
  (jq/ajax "/api/list"
           {:success (fn [data] (log "AJAX: " data))}))

(defn start []
  (list-fruits)
  (log "Initialized."))

(set! (.-onload js/window) start)
