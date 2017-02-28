(ns tictactoe.logic.turn
  (:require
    [tictactoe.logic.board :as board]
    [tictactoe.logic.constants :as cst]))


;; --------------------------------------------------------
;; Game logic (private)
;; --------------------------------------------------------

(defn- next-player [current]
  (if (= :owner/cross current)
    :owner/circle
    :owner/cross))

(defn- same-owner?
  "Indicates whether all positions are owned by the same player"
  [board positions]
  (let [owners (set (map #(get board %) positions))]
    (and
      (not= :owner/none (first owners))
      (= 1 (count owners)))
    ))

(defn- has-winner?
  "There is a winner if some winning line is owned by the same player"
  [board]
  (some #(same-owner? board %) cst/winning-cell-sets))


;; --------------------------------------------------------
;; Game logic (public)
;; --------------------------------------------------------

(def start-turn
  {:board (board/new-empty-board)
   :player :owner/cross})

(defn game-over?
  "The game is over if either:
   * The board is full
   * There is a winner"
  [{:keys [board]}]
  (or
    (board/full-board? board)
    (has-winner? board)))

(defn valid-move?
  "A move if valid if the target cell is empty"
  [turn x y]
  (board/has-no-owner? (:board turn) x y))

(defn next-turn
  "Convert a cell to the player color and switch player"
  [turn x y]
  (if (and (valid-move? turn x y) (not (game-over? turn)))
    (update
      (update turn :board board/convert-cell (:player turn) x y)
      :player next-player)))
