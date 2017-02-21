(ns tictactoe.view.frame
  (:require
    [tictactoe.view.board :as board]
    [tictactoe.view.panel :as panel]))


(defn render
  "Rendering the main frame of the game,
   takes as input the callbacks to trigger events"
  [board {:keys [on-restart-event on-undo-event on-move-event]}]
  [:div
   (panel/render-top-panel
     {:on-restart on-restart-event
      :on-undo on-undo-event})
   (board/render-board board
     {:on-move-event on-move-event})])
