(ns tictactoe.logic.game
  (:require
    [tictactoe.logic.turn :as turn]))


(defn new-game
  "Create a new fresh game, with empty board and no history"
  []
  [turn/start-turn])

(defn play-move
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

(def get-board (comp :board peek))



;; ---- TODO - remove (only there for the tests, but could be done better)

(def get-player (comp :player peek))
(def game-over? (comp turn/game-over? peek))
