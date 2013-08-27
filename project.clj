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
                 [enfocus "2.0.0-SNAPSHOT"]]
  :plugins [[lein-cljsbuild "0.3.2"]]
  :cljsbuild {:builds
              [{:source-paths ["cljs"]
                :compiler
                {:output-to "resources/public/js/main.js"
                 :optimizations :simple
                 :pretty-print false}}]})
