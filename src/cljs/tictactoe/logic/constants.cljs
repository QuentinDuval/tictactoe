(ns tictactoe.logic.constants
  (:require
    [tictactoe.utils.algo :as algo]))


(def board-size 3)

(def coordinates
  (for [x (range board-size) y (range board-size)] [x y]))

(def cell-count (count coordinates))

(def diags
  [(filter #(= (first %) (second %)) coordinates)
   (filter #(= (dec board-size) (reduce + %)) coordinates)])

(def rows (partition board-size coordinates))
(def cols (algo/transpose rows))
(def winning-cell-sets (concat rows cols diags))