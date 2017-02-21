(ns tictactoe.logic.board
  (:require
    [tictactoe.logic.constants :as cst]
    [tictactoe.utils :as utils]))


(defn new-empty-board
  []
  cst/empty-board)

(defn convert-cell
  "Assign the cell [x y] to a new player"
  [board player x y]
  (assoc board [x y] player))

(defn empty-cell?
  "Check whether the cell [x y] is empty"
  [board x y]
  (= (get board [x y]) :cell/empty))

(defn full-board?
  "Verifies whether the board has any empty cell left"
  [board]
  (not-any? #{:cell/empty} (map second board)))

(defn same-owner?
  "Indicates whether all positions are owned by the same player"
  [board positions]
  (let [owners (utils/get-all board positions)]
    (and
      (not-any? #{:cell/empty} owners)
      (= 1 (count (set owners)))
      )))

(defn get-size [_] cst/board-size)
(defn get-cells [board] board)
