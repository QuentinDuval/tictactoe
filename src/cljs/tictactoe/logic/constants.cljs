(ns tictactoe.logic.constants
  (:require
    [tictactoe.utils.algo :as algo]))


(def board-size 3)

(def coordinates
  (for [x (range board-size) y (range board-size)] [x y]))

(def coordinate? (set coordinates))

(def cell-count (count coordinates))

(def winning-diags
  [(filter #(= (first %) (second %)) coordinates)
   (filter #(= (dec board-size) (reduce + %)) coordinates)])

(def winning-rows (partition board-size coordinates))

(def winning-lines (algo/transpose winning-rows))

(def winning-cell-sets
  (concat winning-rows winning-lines winning-diags))