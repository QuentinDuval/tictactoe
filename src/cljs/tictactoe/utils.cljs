(ns tictactoe.utils)


(defn transpose
  "Transpose a matrix"
  [rows]
  (apply mapv vector rows))
