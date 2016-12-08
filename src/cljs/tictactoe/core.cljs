(ns tictactoe.core
  (:require [reagent.core :as reagent :refer [atom]]))

(enable-console-print!)

(def board-size 3)

(def cell-state?
  #{:cell/empty :cell/cross :cell/circle})

(defn init-board
  []
  (into {}
    (for [x (range board-size)
          y (range board-size)]
      [[x y] :cell/cross]
      )))

(defonce app-state
  (atom {:board (init-board)}))


(defmulti render-cell
  "Draw a rectangle cell on the screen"
  (fn [type x y] type))

(defmethod render-cell :cell/empty
  [_ x y]
  [:rect
   {:x (+ 0.05 x) :width 0.9
    :y (+ 0.05 y) :height 0.9
    :fill "lightgrey"
    :on-click #(println "titi")}
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
          :fill "none" :stroke-width 0.03 :stroke "red"}
   ])

(defmethod render-cell :cell/cross
  [_ x y]
  [:g
   [draw-cross-line [x y] [1 1]]
   [draw-cross-line [x (inc y)] [1 -1]]
   ])


(defn tictactoe
  []
  [:div
   [:h1 "tic tac toe"]
   (into
     [:svg.board
      {:view-box (str "0 0 " board-size " " board-size)
       :style {:max-height "500px"}}]
     (for [i (range board-size)
           j (range board-size)]
       [render-cell (get (:board @app-state) [i j]) i j]
       ))
   ])

(reagent/render
  [tictactoe]
  (js/document.getElementById "app"))
