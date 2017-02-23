(ns tictactoe.view.frame
  (:require
    [tictactoe.view.board :as board]
    [tictactoe.view.panel :as panel]))


(defn render
  "Rendering the main frame of the game,
   takes as input the callbacks to trigger events"
  [{:keys [board] :as turn} callbacks]
  [:div
   [panel/render-top-panel turn callbacks]
   [board/render-board board callbacks]
   ])
