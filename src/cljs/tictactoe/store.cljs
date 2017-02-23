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
(defn send-event! [e] (swap! app-state logic/handle-event e))
