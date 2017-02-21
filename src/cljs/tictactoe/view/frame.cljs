(ns tictactoe.view.frame
  (:require
    [tictactoe.view.board :as board]
    [tictactoe.view.panel :as panel]
    [tictactoe.store :as store]
    ))

(defn render
  "Rendering the main frame of the game"
  [board]
  [:div
   (panel/render-top-panel
     {:on-restart store/on-restart-event                    ;; TODO - remove deps
      :on-undo store/on-undo-event})
   (board/render-board
     board
     store/on-move-event)])
