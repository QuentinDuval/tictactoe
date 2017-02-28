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
  [game [x y]]
  (if-let [next-turn (turn/next-turn (current-turn game) x y)]
    (conj game next-turn)
    game))

(defn undo-last-move
  "Remove the last game if there is enough game played"
  [game]
  (if (< 1 (count game)) (pop game) game))

(defn handle-event
  "Callback to dispath the event on the game"
  [game event]
  (cond
    (= event :restart) (new-game)
    (= event :undo) (undo-last-move game)
    :else (play-move game event)
    ))
