(ns tictactoe.view.panel
  (:require
    [tictactoe.view.svg.utils :as utils]
    ))


(defn- top-button
  [on-click txt]
  [:button.top-button {:on-click on-click} txt])

(defn render-top-panel
  "Render the top panel:
   * The restart game button
   * The title of the game
   * The undo button"
  [{:keys [on-restart on-undo]}]
  [:div.scores
   [top-button on-restart utils/circle-arrow]
   [:h1#title "Tic Tac Toe"]
   [top-button on-undo utils/back-arrow]
   ])
