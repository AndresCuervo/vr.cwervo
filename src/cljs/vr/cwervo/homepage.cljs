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

     #_[:div.text-container
        "Oh, hi!"
        [:a {:href "/#"}
         "this is a link :o"]]
     [:a-scene.background-scene
      [:a-sky {:src "/assets/images/panoramas/abandoned-room.jpg"}]]
     ]
    #_[:a-scene
       [:a-sphere {:color "#EF2D5E", :radius "1.25", :position "0 1.25 -5"}]
       (make-box-row 20 [])
       [:a-cylinder
        {:color "#FFC65D",
         :height "1.5",
         :radius "0.5",
         :position "1 0.75 -3"}]
       [:a-plane
        {:color "#7BC8A4",
         :height "4",
         :width "4",
         :rotation "-90 0 0",
         :position "0 0 -4"}]
       [:a-sky {:color "#ECECEC"}]]
    #_[:div [:h1 "Home Page"]
       [:p text "FIXME"]
       [:a {:href "#/about"} "about page"]]))
