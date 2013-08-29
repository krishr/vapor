(ns crud.core
  (:require [clojure.browser.repl :as repl]
            [jayq.core :refer (bind ajax css html append
                                    hide show val serialize)]
            [crate.core :as c]
            [ajax.core :refer (GET POST)]))

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

(defn error-handler [data]
  (log "ERROR: " (count data) "::::" (:parse-error data))
  (html ($ :alerts)
        (str "<div class= \"alert alert-danger alert-dismissable\">
             <button type= \"button\" class= \"close\" data-dismiss= \"alert\" aria-hidden= \"true\">&times;</button>
             <strong>Error: </strong>" data "</div>") ))

(defn show-form []
  (hide ($ :crud))
  (html ($ :form-title) "Add new fruit")
  (hide ($ :delete))
  (show ($ :form-view)))

(defn render-update-form [data]
  (show-form)
  (log "Updating... " data)
  (html ($ :form-title) (str "Update Fruit " (:name data)))
  (show ($ :delete))
  (val ($ :inputId) (:id data))
  (val ($ :inputName) (:name data))
  (val ($ :inputAppearance) (:appearance data))
  (val ($ :inputGrade) (:grade data))
  (val ($ :inputCost) (:cost data)))

(defn render-table [data]
  (hide ($ :intro))
  (hide ($ :form-view))
  (show ($ :crud))
  (let [em ($ :listview)]
    (html em "")
    (append em "<h3>List View</h3>")
    (append em "<tr><th>Name</th><th>Appearance</th><th>Grade</th><th>Cost</th></tr>")
    (doseq [row data]
      (let [id (str "fruit-" (:id row))]
        (append em (str "<tr style='color:" (:appearance row)
                        "' id='fruit-" (:id row) "'>"
                        "<td>" (:name row) "</td>"
                        "<td>" (:appearance row) "</td>"
                        "<td>" (:grade row) "</td>"
                        "<td>" (:cost row) "</td></tr>"))
        (bind ($ id) :click (fn [ev] (render-update-form row)))))))

(defn list-fruits []
  (ajax "/api/list"
        {:success
         (fn [data]
           (log "list-fruits: " data)
           (render-table data))}))

(defn save-new []
  (POST "/api/add"
      {:format :raw
       :handler list-fruits
       :error-handler error-handler
       :params {:id (val ($ :inputId))
                :name (val ($ :inputName))
                :appearance (val ($ :inputAppearance))
                :grade (val ($ :inputGrade))
                :cost (val ($ :inputCost))}}))

(defn cancel-form []
  (hide ($ :form-view))
  (show ($ :crud)))

(defn initialize []
  (-> ($ :client-lang)
      (html "<b>ClojureScript</b>")
      (css {:color "blue"}))
  (bind ($ :start) :click list-fruits)
  (bind ($ :create-new) :click show-form)
  (bind ($ :save) :click save-new)
  (bind ($ :cancel) :click cancel-form)
  (log "Initialized."))

(set! (.-onload js/window) initialize)
