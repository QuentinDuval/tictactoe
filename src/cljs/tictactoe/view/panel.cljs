(ns tictactoe.view.panel
  (:require
    [tictactoe.view.svg.utils :as utils]))


(defn- make-button
  [on-click txt]
  [:button.top-button {:on-click on-click} txt])

(defn render-top-panel
  "Render the top panel:
   * The restart game button
   * The title of the game
   * The undo button"
  [{:keys [on-restart-event on-undo-event]}]
  [:div.scores
   [make-button on-restart-event (utils/circle-arrow)]
   [:h1#title "Tic Tac Toe"]
   [make-button on-undo-event (utils/back-arrow)]
   ])
