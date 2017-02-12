(ns tictactoe.store
  (:require
    [reagent.core :as reagent]
    [tictactoe.logic.game :as logic]
    )
  (:require-macros
    [reagent.ratom :refer [reaction]]
    ))

;; --------------------------------------------------------
;; Game state management
;; --------------------------------------------------------

(defonce app-state (reagent/atom (logic/new-game)))
(def current-board (reaction (logic/get-board @app-state)))

(defn on-move-event [pos] (swap! app-state logic/on-move pos))
(defn on-restart-event [] (reset! app-state (logic/new-game)))
(defn on-undo-event [] (swap! app-state logic/on-undo))
