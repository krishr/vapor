(ns vapor.db
  (:require [clojure.java.jdbc :as j]))

(def ddb {:subprotocol "derby"
          :subname "vaporDb"
          :create true})

(defn seed-data []
  (j/with-connection ddb
    (j/insert-rows :fruits
                   [1 "Mango" "Green" 1.1 2]
                   [2 "Apple" "Red" 1 3])))

(defn create-db []
  (j/with-connection ddb
    (j/create-table
     :fruits
     [:id :int "DEFAULT 0"]
     [:name "VARCHAR(32)" "PRIMARY KEY"]
     [:appearance "VARCHAR(32)"]
     [:grade :real "DEFAULT 0.0"]
     [:cost :int "DEFAULT 0"]
     :table-spec "")))

(defn add-fruit [id name appearance grade cost]
  (j/with-connection ddb
    (j/insert-rows :fruits [id name appearance grade cost])))
