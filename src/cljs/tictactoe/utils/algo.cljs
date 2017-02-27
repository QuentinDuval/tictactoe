(ns tictactoe.utils.algo)


(defn transpose
  "Transpose a matrix"
  [rows]
  (apply mapv vector rows))

#_(defn get-all
  "Get all the values associated to the key"
  [col keys]
  (eduction (map #(get col %)) keys))
