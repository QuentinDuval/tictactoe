(ns tictactoe.cell
  (:require
    [tictactoe.display :as display]
    ))


(defmulti render-cell
  (fn [type x y options] type))

(defmethod render-cell :cell/empty
  [_ x y options]
  [:rect
   (merge {:x (+ display/cell-relative-margin x)
           :y (+ display/cell-relative-margin y)
           :width display/cell-relative-size
           :height display/cell-relative-size
           :fill display/empty-cell-background}
     options)
   ])

(defmethod render-cell :cell/circle
  [_ x y options]
  [:circle
   (merge {:cx (+ display/cell-relative-middle x)
           :cy (+ display/cell-relative-middle y)
           :r (/ display/cell-relative-size 2)
           :fill display/non-empty-cell-background
           :stroke-width display/stroke-width
           :stroke display/circle-cell-color}
     options)
   ])

(defn- draw-cross-line
  [[x y] [dx dy] options]
  [:line {:x1 x :y1 y
          :x2 (+ x (* display/cell-relative-size dx))
          :y2 (+ y (* display/cell-relative-size dy))
          :fill display/non-empty-cell-background
          :stroke-width display/stroke-width
          :stroke display/rectangle-cell-color}
   ])

(defmethod render-cell :cell/cross
  [_ x y options]
  [:g
   [draw-cross-line [(+ x display/cell-relative-margin) (+ y display/cell-relative-margin)] [1 1]]
   [draw-cross-line [(+ x display/cell-relative-margin) (+ y (- 1 display/cell-relative-margin))] [1 -1]]
   ])
