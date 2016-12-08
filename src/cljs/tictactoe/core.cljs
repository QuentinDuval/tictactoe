(ns tictactoe.core
  (:require [reagent.core :as reagent :refer [atom]]))

(enable-console-print!)


;; Constants

(def board-size 3)

(def cell-state?
  #{:cell/empty :cell/cross :cell/circle})


;; Game logic

(defn init-board
  []
  (into {}
    (for [x (range board-size)
          y (range board-size)]
      [[x y] :cell/empty]
      )))

(defonce app-state
  (atom {:board (init-board)
         :player :cell/cross}))

(defn play-move
  [board player x y]
  (assoc board [x y] player))

(defn next-player
  [current]
  (if (= current :cell/cross) :cell/circle :cell/cross))

(defn on-move
  [app-state x y]
  (-> app-state
    (update-in [:board] play-move (:player app-state) x y)
    (update-in [:player] next-player)
    ))

(defn on-move-event
  [x y]
  (swap! app-state on-move x y))


;; Rendering

(defmulti render-cell
  "Draw a rectangle cell on the screen"
  (fn [type x y] type))

(defmethod render-cell :cell/empty
  [_ x y]
  [:rect
   {:x (+ 0.05 x) :width 0.9
    :y (+ 0.05 y) :height 0.9
    :fill "lightgrey"
    :on-click #(on-move-event x y)}
   ])

(defmethod render-cell :cell/circle
  [_ x y]
  [:circle
   {:cx (+ 0.5 x) :cy (+ 0.5 y)
    :r 0.45 :fill "none"
    :stroke-width 0.03
    :stroke "green"}
   ])

(defn draw-cross-line
  [[x y] [dx dy]]
  [:line {:x1 x :y1 y
          :x2 (+ x dx) :y2 (+ y dy)
          :fill "none" :stroke-width 0.04 :stroke "red"}
   ])

(defmethod render-cell :cell/cross
  [_ x y]
  [:g
   [draw-cross-line [(+ x 0.05) (+ y 0.05)] [0.9 0.9]]
   [draw-cross-line [(+ x 0.05) (+ y 0.95)] [0.9 -0.9]]
   ])

(defn render-board-cell
  [app-state x y]
  (render-cell
    (get (:board app-state) [x y])
    x y))

(defn tictactoe
  []
  [:div
   [:h1 "tic tac toe"]
   (into
     [:svg.board
      {:view-box (str "0 0 " board-size " " board-size)
       :style {:max-height "500px"}}]
     (for [x (range board-size)
           y (range board-size)]
       [render-board-cell @app-state x y]
       ))
   ])


;; Plug to reagent

(reagent/render
  [tictactoe]
  (js/document.getElementById "app"))
