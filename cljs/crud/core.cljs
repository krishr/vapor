(ns crud.core
  (:require [enfocus.core :as ef]
            [enfocus.events :as events]
            [enfocus.effects :as effects])
  (:require-macros [enfocus.macros :as em]))

(defn start []
  (ef/at js/document
         ["body"] (ef/content "Hello world!")))

(set! (.-onload js/window) start)
