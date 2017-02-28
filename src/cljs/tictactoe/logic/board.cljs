(ns tictactoe.logic.board
  (:require
    [tictactoe.logic.constants :as cst]
    ))

(defn new-empty-board
  []
  (zipmap cst/coordinates (repeat :owner/none)))

(defn convert-cell
  "Assign the cell [x y] to a new player"
  [board player x y]
  (assoc board [x y] player))

(defn has-no-owner?
  "Check whether the cell [x y] is empty"
  [board x y]
  (= (get board [x y]) :owner/none))

(defn full-board?
  "Verifies whether the board has any empty cell left"
  [board]
  (not-any? #{:owner/none} (vals board)))

(defn get-size [_] cst/board-size)
(defn get-owners-by-coord [board] board)
