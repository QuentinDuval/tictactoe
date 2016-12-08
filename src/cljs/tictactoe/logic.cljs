(ns tictactoe.logic                                         ;; Game logic
  (:require [tictactoe.utils :as utils]))


;; Constants

(def board-size 3)
(def coordinates
  (for [x (range board-size)
        y (range board-size)]
    [x y]))

(def rows (partition 3 coordinates))
(def cols (utils/transpose rows))
(def diags
  [(filter #(= (first %) (second %)) coordinates)
   (filter #(= (dec board-size) (reduce + %)) coordinates)])
(def lines (concat rows cols diags))


;; Game logic

(defn new-empty-board
  []
  (into {} (map (fn [c] [c :cell/empty])) coordinates))

(defn new-game
  "Instantiate a new game (empty board and reset player)"
  []
  [{:board (new-empty-board) :player :cell/cross}])

(defn convert-cell
  "Convert the cell to the new player"
  [board player x y]
  (assoc board [x y] player))

(defn next-player [current]
  (case current
    :cell/cross :cell/circle
    :cell/circle :cell/cross))

(defn winning-line?
  [board line]
  (let [owners (utils/get-all board line)]
    (and
      (not-any? #{:cell/empty} owners)
      (= 1 (count (set owners)))
      )))

(defn game-over?
  [board]
  (some #(winning-line? board %) lines))

(defn is-empty-cell?
  [board x y]
  (= (get board [x y]) :cell/empty))

(defn valid-move?
  [board x y]
  (and (is-empty-cell? board x y) (not (game-over? board))))

(defn on-move
  "Convert the cell to current player, switch player, look at win conditions"
  [game-state x y]
  (let [current (peek game-state)]
    (conj game-state
      (-> current
        (update :board convert-cell (:player current) x y)
        (update :player next-player)
        ))))

(defn on-undo
  "Remove the last game if there is enough game played"
  [game-state]
  (if (< 1 (count game-state)) (pop game-state) game-state))


