(ns tictactoe.core
  (:require
    [reagent.core :as reagent]
    [tictactoe.view.board :as board]
    [tictactoe.view.panel :as panel]
    [tictactoe.logic :as logic])
  (:require-macros
    [reagent.ratom :refer [reaction]]
    ))

;; --------------------------------------------------------
;; Game state management
;; --------------------------------------------------------

(defonce app-state (reagent/atom (logic/new-game)))
(def current-board (reaction (logic/get-board @app-state)))

(defn on-move-event [x y] (swap! app-state logic/on-move x y))
(defn on-restart-event [] (reset! app-state (logic/new-game)))
(defn on-undo-event [] (swap! app-state logic/on-undo))

;; --------------------------------------------------------
;; Main rendering
;; --------------------------------------------------------

(defn tic-tac-toe
  "Main rendering entry: assemble the game logic, the view and control"
  []
  [:div
   (panel/render-top-panel {:on-restart on-restart-event :on-undo on-undo-event})
   (board/render-board @current-board on-move-event)
   ])

(reagent/render [tic-tac-toe] (js/document.getElementById "app"))
