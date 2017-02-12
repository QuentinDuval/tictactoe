(ns tictactoe.logic.game
  (:require
    [tictactoe.logic.turn :as turn]
    ))


;; --------------------------------------------------------
;; Game logic (public)
;; --------------------------------------------------------

(defn new-game
  "Create a new fresh game, with empty board and no history"
  []
  [turn/start-turn])

(defn on-move
  "On reception of the move command"
  [game-state [x y]]
  (let [current (peek game-state)]
    (if (turn/valid-move? current x y)
      (conj game-state (turn/play-move current x y))
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
  (turn/game-over? (peek game-state)))
