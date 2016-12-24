(ns tictactoe.view.cell
  (:require
    [tictactoe.view.constants :as cst]
    ))


(defmulti render-cell
  (fn [type x y options] type))

(defmethod render-cell :cell/empty
  [_ x y options]
  [:rect
   (merge {:x (+ cst/cell-relative-margin x)
           :y (+ cst/cell-relative-margin y)
           :width cst/cell-relative-size
           :height cst/cell-relative-size
           :fill cst/empty-cell-background}
     options)
   ])

(defmethod render-cell :cell/circle
  [_ x y options]
  [:circle
   (merge {:cx (+ cst/cell-relative-middle x)
           :cy (+ cst/cell-relative-middle y)
           :r (/ cst/cell-relative-size 2)
           :fill cst/non-empty-cell-background
           :stroke-width cst/stroke-width
           :stroke cst/circle-cell-color}
     options)
   ])

(defn- draw-cross-line
  [[x y] [dx dy] options]
  [:line {:x1 x :y1 y
          :x2 (+ x (* cst/cell-relative-size dx))
          :y2 (+ y (* cst/cell-relative-size dy))
          :fill cst/non-empty-cell-background
          :stroke-width cst/stroke-width
          :stroke cst/rectangle-cell-color}
   ])

(defmethod render-cell :cell/cross
  [_ x y options]
  [:g
   [draw-cross-line [(+ x cst/cell-relative-margin) (+ y cst/cell-relative-margin)] [1 1]]
   [draw-cross-line [(+ x cst/cell-relative-margin) (+ y (- 1 cst/cell-relative-margin))] [1 -1]]
   ])
