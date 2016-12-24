(ns tictactoe.cell)


;; Constants

(def cell-relative-size 0.9)
(def cell-relative-margin (/ (- 1 cell-relative-size) 2))
(def cell-relative-middle 0.5)

(def stroke-width 0.04)
(def empty-cell-background "lightgrey")
(def non-empty-cell-background "none")
(def circle-cell-color "green")
(def rectangle-cell-color "red")


;; Rendering

(defmulti render-cell
  (fn [type x y options] type))

(defmethod render-cell :cell/empty
  [_ x y options]
  [:rect
   (merge {:x (+ cell-relative-margin x)
           :y (+ cell-relative-margin y)
           :width cell-relative-size
           :height cell-relative-size
           :fill empty-cell-background}
     options)
   ])

(defmethod render-cell :cell/circle
  [_ x y options]
  [:circle
   (merge {:cx (+ cell-relative-middle x)
           :cy (+ cell-relative-middle y)
           :r (/ cell-relative-size 2)
           :fill non-empty-cell-background
           :stroke-width stroke-width
           :stroke circle-cell-color}
     options)
   ])

(defn- draw-cross-line
  [[x y] [dx dy] options]
  [:line {:x1 x :y1 y
          :x2 (+ x (* cell-relative-size dx))
          :y2 (+ y (* cell-relative-size dy))
          :fill non-empty-cell-background
          :stroke-width stroke-width
          :stroke rectangle-cell-color}
   ])

(defmethod render-cell :cell/cross
  [_ x y options]
  [:g
   [draw-cross-line [(+ x cell-relative-margin) (+ y cell-relative-margin)] [1 1]]
   [draw-cross-line [(+ x cell-relative-margin) (+ y (- 1 cell-relative-margin))] [1 -1]]
   ])
