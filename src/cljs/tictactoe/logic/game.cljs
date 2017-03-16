(ns tictactoe.logic.game
  (:require
    [tictactoe.logic.turn :as turn]))


(defn new-game []
  [turn/start-turn])

(defn current-turn
  [game]
  (peek game))

(defn play-move
  "On reception of the move command"
  [game coord]
  game)

(defn undo-last-move
  "Remove the last game if there is enough game played"
  [game]
  game)

(defn handle-event
  "Callback to dispath the event on the game"
  [game event]
  game)
