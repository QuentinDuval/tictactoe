(ns tictactoe.view.frame
  (:require
    [tictactoe.view.board :as board]
    [tictactoe.view.panel :as panel]))


(defn render
  "Rendering the main frame of the game,
   takes as input the callbacks to trigger events"
  [board callbacks]
  [:div
   (panel/render-top-panel callbacks)
   (board/render-board board callbacks)])
