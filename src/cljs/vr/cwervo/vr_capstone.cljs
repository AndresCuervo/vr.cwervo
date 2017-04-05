(ns vr.cwervo.vr-capstone
  (:require-macros [secretary.core :refer [defroute]])
  (:import goog.History)
  (:require
    [clojure.string :as string]
    [secretary.core :as secretary]
    [goog.events :as events]
    [goog.history.EventType :as EventType]
    [reagent.core :as reagent]
    ))

;; Okay, but make this spawn components actually lol, like you have in the other scene
;; Actually, maybe keep a high level ns just for working with VR scenes? :)
(defn make-box-row [n result]
  ;; Ohh, you can do trees like this, in raw html, maybe?
  (if (> n 0)
    (make-box-row (dec n) (cons [:a-box
                                 {:color "#4CC3D9",
                                  :depth "1",
                                  :height "1",
                                  :width "1",
                                  :rotation "0 45 0",
                                  :position (string/join
                                              " "
                                              [(+ -2 n 1) (+ 0.5 (Math/sin n)) -3])}] result))
    result))

;; (defn page [ratom]
;;   (let [text (:text @ratom)]
(defn page []
  (let [] ;; nada
    [:a-scene
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
       [:a-sky {:color "#ECECEC"}]]))

