(ns tictactoe.logic.board
  (:require
    [tictactoe.logic.constants :as cst]
    ))


(defn new-empty-board
  []
  cst/empty-board)

(defn convert-cell
  [board player x y]
  (assoc board [x y] player))

(defn empty-cell?
  [board x y]
  (= (get board [x y]) :cell/empty))

(defn has-empty-cell?
  [board]
  (not-any? #{:cell/empty} (map second board)))
