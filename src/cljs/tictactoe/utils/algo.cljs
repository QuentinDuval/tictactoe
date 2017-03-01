(ns tictactoe.utils.algo)


(defn transpose
  "Transpose a matrix"
  [rows]
  (apply mapv vector rows))
