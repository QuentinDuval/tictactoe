(ns tictactoe.cell)

(defn render-rect
  [x y options]
  [:rect
   (merge
     {:x (+ 0.05 x) :width 0.9
      :y (+ 0.05 y) :height 0.9
      :fill "lightgrey"}
     options)
   ])

(defn render-circle
  [x y]
  [:circle
   {:cx (+ 0.5 x) :cy (+ 0.5 y)
    :r 0.45 :fill "none"
    :stroke-width 0.03
    :stroke "green"}
   ])

(defn- draw-cross-line
  [[x y] [dx dy]]
  [:line
   {:x1 x :y1 y
    :x2 (+ x dx) :y2 (+ y dy)
    :fill "none" :stroke-width 0.04 :stroke "red"}
   ])

(defn render-cross
  [x y]
  [:g
   [draw-cross-line [(+ x 0.05) (+ y 0.05)] [0.9 0.9]]
   [draw-cross-line [(+ x 0.05) (+ y 0.95)] [0.9 -0.9]]
   ])
