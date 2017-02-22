(ns tictactoe.store
  (:require
    [reagent.core :as reagent]
    [tictactoe.logic.game :as logic])
  (:require-macros
    [reagent.ratom :refer [reaction]]))


;; --------------------------------------------------------
;; Game state (game loop: use event to trigger logic)
;; --------------------------------------------------------

(defonce app-state (reagent/atom (logic/new-game)))
(def current-board (reaction (logic/get-board @app-state)))

(defn on-move-event [pos]
  (swap! app-state logic/handle-event pos))

(defn on-restart-event []
  (swap! app-state logic/handle-event :restart))

(defn on-undo-event []
  (swap! app-state logic/handle-event :undo))
