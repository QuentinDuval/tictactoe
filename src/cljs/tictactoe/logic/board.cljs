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

(defn empty-position?
  [board x y]
  (= (get board [x y]) :cell/empty))

(defn full?
  [board]
  (not-any? #{:cell/empty} (map second board)))
