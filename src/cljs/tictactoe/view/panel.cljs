(ns tictactoe.view.panel
  (:require
    [tictactoe.view.svg.title :as title]
    [tictactoe.view.svg.utils :as utils]))


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
   [:h1#title (title/get-title turn)]
   ])
