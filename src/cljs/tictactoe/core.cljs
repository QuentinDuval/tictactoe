(ns tictactoe.core
  (:require
    [reagent.core :as reagent :refer [atom]]
    [tictactoe.cell :as cell]
    [tictactoe.panel :as panel]
    [tictactoe.logic :as logic])
  (:require-macros
    [reagent.ratom :refer [reaction]]
    ))

;; --------------------------------------------------------
;; Game state management
;; --------------------------------------------------------

(defonce app-state (atom (logic/new-game)))
(def current-state (reaction (peek @app-state)))

(defn on-move-event [x y] (swap! app-state logic/on-move x y))
(defn on-restart-event [] (reset! app-state (logic/new-game)))
(defn on-undo-event [] (swap! app-state logic/on-undo))

;; --------------------------------------------------------
;; Main rendering
;; --------------------------------------------------------

(defn render-board-cell
  "Render a cell on the board"
  [board x y]
  (cell/render-cell
    (get board [x y]) x y
    {:on-click #(on-move-event x y)}))

(defn render-board
  "Render the board: pure component that only depends on inputs"
  [board]
  (into
    [:svg.board
     {:view-box (str "0 0 " logic/board-size " " logic/board-size)
      :style {:max-height "500px"}}]
    (for [[x y] logic/coordinates]
      [render-board-cell board x y])
    ))

(defn tictactoe
  "Main rendering entry: assemble the game logic, the view and control"
  []
  [:div
   (panel/render-top-panel {:on-restart on-restart-event :on-undo on-undo-event})
   (render-board (:board @current-state))
   ])


;; Plug to reagent

(reagent/render [tictactoe] (js/document.getElementById "app"))
