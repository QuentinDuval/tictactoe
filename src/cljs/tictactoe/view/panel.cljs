(ns tictactoe.view.panel
  (:require
    [tictactoe.logic.turn :as turn]
    [tictactoe.view.svg.utils :as utils]))


(defn- get-title
  [turn]
  (if (turn/game-over? turn) "Game over" "Tic Tac Toe"))

(defn- make-button
  [on-click txt]
  [:button.top-button {:on-click on-click} txt])

(defn render-top-panel
  "Render the top panel:
   * The restart game button
   * The title of the game
   * The undo button"
  [turn {:keys [on-restart on-undo]}]
  [:div.scores
   [make-button on-restart utils/circle-arrow]
   [:h1#title (get-title turn)]
   [make-button on-undo utils/back-arrow]
   ])
