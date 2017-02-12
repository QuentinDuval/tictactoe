(ns tictactoe.core
  (:require
    [reagent.core :as reagent]
    [tictactoe.view.board :as board]
    [tictactoe.view.panel :as panel]
    [tictactoe.logic.constants :as cst]
    [tictactoe.store :as store])
  (:require-macros
    [reagent.ratom :refer [reaction]]
    ))


(defn tic-tac-toe
  "Main rendering entry: assemble the game logic, the view and control"
  []
  [:div
   (panel/render-top-panel
     {:on-restart store/on-restart-event
      :on-undo store/on-undo-event})
   (board/render-board
     @store/current-board
     cst/board-size
     store/on-move-event)
   ])

(reagent/render
  [tic-tac-toe]
  (js/document.getElementById "app"))
