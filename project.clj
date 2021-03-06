(defproject vapor "0.1.0-SNAPSHOT"
  :description "Vaporware"
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [compojure "1.1.5"]
                 [ring/ring-devel "1.2.0"]
                 [ring/ring-core "1.2.0"]
                 [ring/ring-json "0.2.0"]
                 [ring-basic-authentication "1.0.2"]
                 [org.clojure/clojurescript "0.0-1859"]
                 [http-kit "2.1.10"]
                 [jayq "2.4.0"]
                 [crate "0.2.4"]
                 [cljs-ajax "0.2.0"]
                 [org.clojure/java.jdbc "0.3.0-alpha4"]
                 [org.apache.derby/derby "10.8.1.2"]]
  :plugins [[lein-cljsbuild "0.3.2"]]
  :cljsbuild {:builds
              [{:source-paths ["cljs"]
                :compiler
                {:output-to "resources/public/js/main.js"
                 :optimizations :simple
                 :pretty-print false}}]})
