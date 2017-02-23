(ns tictactoe.logic.game
  (:require
    [tictactoe.logic.turn :as turn]))


(defn new-game
  "Create a new fresh game, with empty board and no history"
  []
  [turn/start-turn])

(defn current-turn
  "Access the current turn"
  [game-state]
  (peek game-state))

(defn play-move
  "On reception of the move command"
  [game-state [x y]]
  (let [current (current-turn game-state)]
    (if-let [next-turn (turn/play-move current x y)]
      (conj game-state next-turn)
      game-state)))

(defn undo-last-move
  "Remove the last game if there is enough game played"
  [game-state]
  (if (< 1 (count game-state))
    (pop game-state)
    game-state))

(defn handle-event
  "Callback to dispath the event on the game"
  [game-state event]
  (cond
    (= event :restart) (new-game)
    (= event :undo) (undo-last-move game-state)
    :else (play-move game-state event)
    ))
