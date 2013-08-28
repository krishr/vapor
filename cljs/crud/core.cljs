(ns crud.core
  (:require [clojure.browser.repl :as repl]
            [jayq.core :refer (bind ajax css html append
                                    hide show)]
            [crate.core :as c]))

(defn $ [id] (if (keyword? id)
               (js/jQuery (str "#" (name id)))
               (js/jQuery (str "#" id))))

(defn log [data & body]
  (if (empty? body)
    (.log js/console data)
    (.log js/console (str data body))))

(defn get-hostname [] (.-hostname (.-location js/window)))

(defn repl-connect []
  (repl/connect (str "http://" (get-hostname) ":9000/repl")))

(defn render-table [data]
  (hide ($ :intro))
  (let [em ($ :listview)]
    (append em "<tr><th>Name</th><th>Appearance</th><th>Grade</th><th>Cost</th></tr>")
    (doseq [row data]
      (append em (str "<tr style='color:" (:appearance row)
                      "' id='fruit-" (:id row) "'>"
                      "<td>" (:name row) "</td>"
                      "<td>" (:appearance row) "</td>"
                      "<td>" (:grade row) "</td>"
                      "<td>" (:cost row) "</td></tr>")))))

(defn list-fruits []
  (ajax "/api/list"
        {:success
         (fn [data]
           (log "list-fruits: " data)
           (render-table data))}))

(defn initialize []
  (-> ($ :client-lang)
      (html "<b>ClojureScript</b>")
      (css {:color "blue"}))
  (bind ($ :start) :click list-fruits)
  (log "Initialized."))

(set! (.-onload js/window) initialize)
