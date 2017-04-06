(defproject vr.cwervo "0.1.0-SNAPSHOT"
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/clojurescript "1.9.229"]
                 ;; [org.clojure/core.async "0.3.442"] ;; Maybeeee
                 [reagent "0.6.0"]
                 [reagent-utils "0.2.1"]
                 [com.andrewmcveigh/cljs-time "0.4.0"]
                 [secretary "1.2.3"]
                 [venantius/accountant "0.1.9"]]

  :min-lein-version "2.5.3"

  :source-paths ["src/clj"]

  :plugins [[lein-cljsbuild "1.1.4"]
            [lein-sassy "1.0.8"]]

  :clean-targets ^{:protect false} ["resources/public/js/compiled"
                                    "target"]

  :figwheel {:css-dirs ["resources/public/css"]}

  :sass {:src "src/scss"
         :dst "resources/public/css/"}

  :profiles
  {:dev
   {:dependencies []

    :plugins      [[lein-figwheel "0.5.8"]]
    }}

  :cljsbuild
  {:builds
   [{:id           "dev"
     :source-paths ["src/cljs"]
     :figwheel     {:on-jsload "vr.cwervo.core/reload"}
     :compiler     {:main                 vr.cwervo.core
                    :optimizations        :none
                    :output-to            "resources/public/js/compiled/app.js"
                    :output-dir           "resources/public/js/compiled/dev"
                    :asset-path           "js/compiled/dev"
                    :source-map-timestamp true}}

    {:id           "min"
     :source-paths ["src/cljs"]
     :compiler     {:main            vr.cwervo.core
                    :optimizations   :advanced
                    :output-to       "resources/public/js/compiled/app.js"
                    :output-dir      "resources/public/js/compiled/min"
                    :closure-defines {goog.DEBUG false}
                    :pretty-print    false}}

    ]})
