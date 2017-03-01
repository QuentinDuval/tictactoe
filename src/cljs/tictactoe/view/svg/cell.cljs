(ns tictactoe.view.svg.cell
  (:require
    [tictactoe.view.svg.constants :as cst]))


(defn- render-square
  [[x y] options]
  [:rect
   (merge {:x (+ cst/cell-relative-margin x)
           :y (+ cst/cell-relative-margin y)
           :width cst/cell-relative-size
           :height cst/cell-relative-size
           :fill cst/empty-cell-background}
     options)])

(defn- render-circle
  [[x y] options]
  [:circle
   (merge {:cx (+ cst/cell-relative-middle x)
           :cy (+ cst/cell-relative-middle y)
           :r (/ cst/cell-relative-size 2)
           :fill cst/non-empty-cell-background
           :stroke-width cst/stroke-width
           :stroke cst/circle-cell-color}
     options)])

(defn- draw-cross-line
  [[x y] [dx dy] options]
  [:line {:x1 x :y1 y
          :x2 (+ x (* cst/cell-relative-size dx))
          :y2 (+ y (* cst/cell-relative-size dy))
          :fill cst/non-empty-cell-background
          :stroke-width cst/stroke-width
          :stroke cst/rectangle-cell-color}
   ])

(defn- render-cross
  [[x y] options]
  [:g
   [draw-cross-line [(+ x cst/cell-relative-margin) (+ y cst/cell-relative-margin)] [1 1]]
   [draw-cross-line [(+ x cst/cell-relative-margin) (+ y (- 1 cst/cell-relative-margin))] [1 -1]]
   ])


(defn render-cell
  "Dispatch the rendering of the cell based on the player"
  [[coord owner :as cell] on-move-event]
  (let [renderer
        (case owner
          :owner/cross render-cross
          :owner/circle render-circle
          render-square)]
    (renderer coord {:on-click #(on-move-event coord)})))