(ns vr.cwervo.homepage
  (:require-macros [secretary.core :refer [defroute]])
  (:import goog.History)
  (:require
    [clojure.string :as string]
    [secretary.core :as secretary]
    [goog.events :as events]
    [goog.history.EventType :as EventType]
    [reagent.core :as reagent]
    ))

;; (defn page [ratom]
;;   (let [text (:text @ratom)]

(defn page []
  (let []
    [:div.page ;; needs to return a single element, so this wraps it !
     [:div#wrapper.center
      [:section
       [:span
        "Oh, hello there! :)"]
       [:p
        "Here are some links to other VR things I've done:"
        [:ul.vr_list
         [:li
          [:a {:href "/#/vr-capstone"}
           "My VR CRWR capstone"]]
         [:li
          [:a {:href "/#/about"}
           "about"]]]]]]
     [:a-scene.background-scene
      [:a-sky {:src "/assets/images/panoramas/abandoned-room.jpg"}]]]))
