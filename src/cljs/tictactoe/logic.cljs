(ns tictactoe.logic                                         ;; Game logic
  (:require
    [tictactoe.utils :as utils]
    ))

;; --------------------------------------------------------
;; Constants
;; --------------------------------------------------------

(def board-size 3)

(def coordinates
  (for [x (range board-size)
        y (range board-size)]
    [x y]))

(def empty-board
  (into {}
    (map (fn [c] [c :cell/empty]))
    coordinates))

(def diags
  [(filter #(= (first %) (second %)) coordinates)
   (filter #(= (dec board-size) (reduce + %)) coordinates)])

(def rows (partition board-size coordinates))
(def cols (utils/transpose rows))
(def lines (concat rows cols diags))

;; --------------------------------------------------------
;; Game logic (private)
;; --------------------------------------------------------

(defn- convert-cell
  [board player x y]
  (assoc board [x y] player))

(defn- next-player [current]
  (if (= :cell/cross current) :cell/circle :cell/cross))

(defn- winning-line?
  [board line]
  (let [owners (utils/get-all board line)]
    (and
      (not-any? #{:cell/empty} owners)
      (= 1 (count (set owners)))
      )))

(defn- has-winner?
  [board]
  (some #(winning-line? board %) lines))

(defn- is-empty-cell?
  [board x y]
  (= (get board [x y]) :cell/empty))

(defn- valid-move?
  [board x y]
  (and (is-empty-cell? board x y) (not (has-winner? board))))

(defn- next-state
  [current x y]
  (-> current
    (update :board convert-cell (:player current) x y)
    (update :player next-player)
    ))

;; --------------------------------------------------------
;; Game logic (public)
;; --------------------------------------------------------

(defn new-game
  "Create a new fresh game, with empty board and no history"
  []
  [{:board empty-board :player :cell/cross}])

(defn on-move
  "On reception of the move command"
  [game-state x y]
  (let [current (peek game-state)]
    (if (valid-move? (:board current) x y)
      (conj game-state (next-state current x y))
      game-state)))

(defn on-undo
  "Remove the last game if there is enough game played"
  [game-state]
  (if (< 1 (count game-state))
    (pop game-state)
    game-state))

(defn get-board
  "Get the current board of the game"
  [game-state]
  (:board (peek game-state)))

(defn get-next-player
  "Get the next player to play"
  [game-state]
  (:player (peek game-state)))

(defn game-over?
  "Indicates whether the game is over"
  [game-state]
  (let [current-board (get-board game-state)]
    (or
      (not (some #{:cell/empty} (map second current-board)))
      (has-winner? current-board)
      )))
