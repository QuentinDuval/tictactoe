(ns tictactoe.cell)


(defmulti render-cell
  (fn [type x y options] type))

(defmethod render-cell :cell/empty
  [_ x y options]
  [:rect
   (merge {:x (+ 0.05 x) :width 0.9
           :y (+ 0.05 y) :height 0.9
           :fill "lightgrey"}
     options)
   ])

(defmethod render-cell :cell/circle
  [_ x y options]
  [:circle
   (merge {:cx (+ 0.5 x) :cy (+ 0.5 y)
           :r 0.45 :fill "none"
           :stroke-width 0.03
           :stroke "green"}
     options)
   ])

(defn- draw-cross-line
  [[x y] [dx dy] options]
  [:line
   {:x1 x :y1 y
    :x2 (+ x dx) :y2 (+ y dy)
    :fill "none" :stroke-width 0.04 :stroke "red"}
   ])

(defmethod render-cell :cell/cross
  [_ x y options]
  [:g
   [draw-cross-line [(+ x 0.05) (+ y 0.05)] [0.9 0.9]]
   [draw-cross-line [(+ x 0.05) (+ y 0.95)] [0.9 -0.9]]
   ])
