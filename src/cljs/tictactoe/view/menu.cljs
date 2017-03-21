(ns tictactoe.view.menu
  (:require
    [tictactoe.view.svg.title :as title]
    [tictactoe.view.svg.utils :as utils]))


(defn- make-button
  [on-click txt]
  [:button.top-button {:on-click on-click} txt])

(defn render-top-menu
  "Render the top menu:
   * The restart game button
   * The title of the game
   * The undo button"
  [turn {:keys [on-restart on-undo]}]
  [:div.scores
   [:h1#title (title/get-title turn)]
   ])
