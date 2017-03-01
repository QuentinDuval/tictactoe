(ns tictactoe.logic.board)


;; TODO - investigate the possibility to have a truely empty board
;; TODO - full-board? would be a check of coordinates vs keys of map

(def size "The size of the board" 3)

(def coordinates
  "All the valid coordinates on the board"
  (for [x (range size) y (range size)] [x y]))

(def coordinates? (set coordinates))
(def empty-board (zipmap coordinates (repeat :owner/none)))

(defn get-owner-at
  "Get the owner associated to the cell"
  [board coord]
  {:pre [(coordinates? coord)]}
  (get board coord))

(defn is-cell-owned?
  "Check whether the cell [x y] is empty"
  [board x y]
  (not= (get-owner-at board [x y]) :owner/none))

(defn convert-cell
  "Assign the cell [x y] to a new player"
  [board player x y]
  {:pre [(coordinates? [x y])
         (not (is-cell-owned? board x y))]}
  (assoc board [x y] player))

(defn full-board?
  "Verifies whether the board has any empty cell left"
  [board]
  (not-any? #{:owner/none} (vals board)))
