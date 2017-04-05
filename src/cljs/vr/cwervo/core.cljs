(ns vr.cwervo.core
  (:require-macros [secretary.core :refer [defroute]])
  (:import goog.History)
  (:require
   [clojure.string :as string]
   [secretary.core :as secretary]
   [goog.events :as events]
   [goog.history.EventType :as EventType]
   [reagent.core :as reagent]
   [reagent.session :as session]
   [vr.cwervo.homepage :as homepage]
   [vr.cwervo.vr-capstone :as vr-capstone]
   ))


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Vars

(defonce debug?
  ^boolean js/goog.DEBUG)

(defonce app-state
  (reagent/atom
   {:text "Hello, what is your name? "
    :page :nil}))



;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Routes
;; I think eventually you should replace this with the following, or maybe
;; more precisely, add the Accountant library!
;; https://gist.github.com/city41/aab464ae6c112acecfe1

(defn hook-browser-navigation! []
  (doto (History.)
    (events/listen
     EventType/NAVIGATE
     (fn [event]
       (secretary/dispatch! (.-token event))))
    (.setEnabled true)))

(defn app-routes []
  (secretary/set-config! :prefix "#")

  (defroute "/" []
    (swap! app-state assoc :page :home))
  (defroute "/about" []
    (swap! app-state assoc :page :about))

  ;; add routes here
  (defroute "/vr-capstone" []
    (swap! app-state assoc :page :vr-capstone))

  (hook-browser-navigation!))



;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Pages

(defn about [ratom]
  [:div [:h1 "About Page"]
   [:a {:href "#/"} "home page"]])

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Initialize App

(defmulti page identity)
(defmethod page :home [] homepage/page)
(defmethod page :vr-capstone [] vr-capstone/page)
(defmethod page :about [] about)
(defmethod page :default [] (fn [_] [:div]))

(defn current-page [ratom]
  (let [page-key (:page @ratom)]
    [(page page-key) ratom]))

(defn dev-setup []
  (when debug?
    (enable-console-print!)
    (println "dev mode")
    ))

(defn reload []
  (reagent/render [current-page app-state]
                  (.getElementById js/document "app")))

(defn ^:export main []
  (dev-setup)
  (app-routes)
  (reload))
