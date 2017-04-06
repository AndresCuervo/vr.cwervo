(ns vr.cwervo.scenes.vr-capstone
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
    (make-box-row (dec n) (list ^{:key (str "string-"n)} [:a-box
                                 {:color "#4CC3D9"
                                  :depth "1"
                                  :height "1"
                                  :width "1"
                                  :rotation "0 45 0"
                                  ;; :animation__rotate_lol (str
                                  ;;                          "property: rotation;
                                  ;;                          from: 0 45 0;
                                  ;;                          loop: true;
                                  ;;                          easing: linear;
                                  ;;                          to: 0 " (+ 360 (*  45 (Math/cos n))) " 0;")
                                  :position (string/join
                                              " "
                                              [(+ -2 n 1) (+ 0.5 (Math/cos n)) -3])}] result))
    result))

(def srcs
  [;; Here be dragons.
   ;;
   ;; JK, here are all the extenal scripts, maybe they'll move to CLJSJS compilation
   ;; some day, if I can muster it.
   "https://rawgit.com/ngokevin/aframe-animation-component/master/dist/aframe-animation-component.min.js"
   ;; Custom scripts to be ported over:
   ;; [:script {:src"/a-frame-js/click-component.js"}]
   ;; [:script {:src"/a-frame-js/specifyPosition.js"}]
   ;; <!-- [:script {:src"/a-frame-js/tree-geom.js"}] -->
   ;; <!-- Working with .ply models -->
   "https://rawgit.com/donmccurdy/aframe-extras/v2.1.1/dist/aframe-extras.loaders.min.js"
   ;; <!-- Entity generator -->
   "https://unpkg.com/aframe-entity-generator-component@^3.0.0/dist/aframe-entity-generator-component.min.js"
   "https://unpkg.com/aframe-randomizer-components@^3.0.1/dist/aframe-randomizer-components.min.js"
   "https://unpkg.com/aframe-layout-component@4.0.1/dist/aframe-layout-component.min.js"
   "https://unpkg.com/aframe-template-component@^3.1.1/dist/aframe-template-component.min.js"
   ])

(def head js/document.head)
(def body js/document.body)



;; Taken from this wonderful SO answer:
;; http://stackoverflow.com/a/19514077/4230661
;; if you need to load a js file
;; (set! (.-type the-script) "text/javascript")
;; (set! (.-src the-script) "url_file")
;; (set! (.-innerHTML the-script) the-script-value)
;; (set! (.-src the-script) url)
;; (.appendChild the-head the-script)

(defn add-scripts []
  (for [source srcs
        :let [new-script (.createElement js/document "script")]]
    ;; Check that it's not already in the head
    (when (not (.contains head new-script))
      (do
        (set! (.-type new-script) "text/javascript")
        (set! (.-src new-script) source)
        (.appendChild head new-script)
        ;; (.appendChild body new-script)
        (js/console.log (str "Adding " (.-src new-script) " to the head."))))))

(def aframe-assets
  [:a-assets
        [:a-mixin {:id "tree-color" :material "color: green"}]
        [:a-mixin {:id "bark-color" :material "color: #926239"}]
        [:img {:id "pine-needles-texture" :src "/assets/images/textures/pine-needles.jpg"}]
        [:img {:id "bark-cylinder-texture" :src "/assets/images/textures/bark.jpg"}]
        {:a-mixin {:id "tree-texture" :material "src:#pine-needles-texture"}}
        [:a-mixin {:id "bark-texture" :material "src:#bark-cylinder-texture"}]
        [:script {:id "treeTemplate" :type "html"}
            [:a-entity {:id "conicTree-${number}" :position "${pos}"}
             [:a-cone {:mixin "tree-texture" :radius-bottom "2" :radius-top "0" :height "2"}
              [:a-cone {:mixin "tree-texture" :radius-bottom "2" :radius-top "0" :position "0 -1   0"}]
              [:a-cone {:mixin "tree-texture" :radius-bottom "2" :radius-top "0" :position "0 -1.5 0"}]]
             ;; [:a-cylinder {:mixin "bark-color" :height "1" :radius "0.5" :position "0 -2.5 0"}]
             [:a-cylinder {:random-rotation "min: 0 0 0; max: 0 360 0" :mixin "bark-texture" :height "2" :radius "0.5" :position "0 -2.5 0"}]]]
        ])

(def add-scene
  [:a-scene
     aframe-assets
     ;; [:a-entity {:template "src: #treeTemplate" :data-number "1" :data-pos "-2 3 -4"}]
     ;; [:a-entity {:template "src: #treeTemplate" :data-number "2" :data-pos "1 3 -4"}]
     ;; [:a-entity {:template "src: #treeTemplate" :data-number "3" :data-pos "0 3 -4"}]
     ;; Ugh, just add it to the head, and be done with it for now :/
     [:a-sphere {:color "#EF2D5E", :radius "1.25", :position "0 1.25 -5"}]
     ;; Here we're just unwrapping
     ;; Taken from: https://github.com/r0man/sablono/issues/40
     ;; It's worth noting, this is expressing distinct a-box elements,
     ;; whereas we could also just use the list and add the meta prop:
     ;; ^{:key (str "box-"n)} before [:a-box in the list function above.
     ;;
     ;; Anyway, this is fine since I'm just replicating the work I did
     ;; in the first scene in HTML *shrug*
     (make-box-row 20 '())
     ;; Actually, you can make a function to generate these trees, like a mixin!!

      [:a-cone
       {:id "testCone"
        :position "-4 0 -3"
        :radius-bottom "40"
        :radius-top "0"
        :height "2"
        :material "color: blue;"
        ;; :material "src:#pine-needles-texture"
        }]

     [:a-entity {:id "conicTree-1" :position "-2 3 -4"}
      [:a-cone {:material "src:#pine-needles-texture" :radius-bottom "2" :radius-top "0" :height "2"}
       [:a-cone {:material "src:#pine-needles-texture" :radius-bottom "2" :radius-top "0" :position "0 -1   0"}]
       [:a-cone {:material "src:#pine-needles-texture" :radius-bottom "2" :radius-top "0" :position "0 -1.5 0"}]]
      ;; [:a-cylinder {:mixin "bark-color" :height "1" :radius "0.5" :position "0 -2.5 0"}]
      ;; [:a-cylinder {:random-rotation "min: 0 0 0; max: 0 360 0" :mixin "bark-texture" :height "2" :radius "0.5" :position "0 -2.5 0"}]
      ]
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
     [:a-sky {:color "#ECECEC"}]])

(defn page []
  (let [the-head js/document.head
        the-script (.createElement js/document "script")
        the-script-value "console.log(\"The script was run\");"
        url "https://rawgit.com/ngokevin/aframe-animation-component/master/dist/aframe-animation-component.min.js"]
    ;; --------
    ;; TODO
    ;; --------
    ;; okay, so this works for the animation component, but??
    ;; - don't append if it's already in the head [x]
    ;; - get it to work in a map binding! (make a function that take a url and does the appaend thing!) [x]
    ;; - put this in a utils ns because it'll be useful in other places! [-] ;; TODO -ac - Need to get this working first by reloading the scene component, RIP

    ;; Disable adding scripts and just chuck them all in the index.html :((((((
    ;; (add-scripts)

    add-scene))
