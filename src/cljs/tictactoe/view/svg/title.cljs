(ns tictactoe.view.svg.title
  (:require [tictactoe.logic.turn :as turn]))


(defn- get-winner-name
  [turn]
  (if-let [winner (turn/get-winner turn)]
    (clojure.string/capitalize (name winner))))

(defn get-title
  [turn]
  (if-let [winner-name (get-winner-name turn)]
    (str winner-name " wins")
    (if (turn/game-over? turn)
      "Draw game"
      "Tic Tac Toe")))
