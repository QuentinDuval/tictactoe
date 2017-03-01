(ns tictactoe.store
  (:require
    [reagent.core :as reagent]
    [tictactoe.logic.game :as game])
  (:require-macros
    [reagent.ratom :refer [reaction]]))


;; --------------------------------------------------------
;; Game state (game loop: use event to trigger logic)
;; --------------------------------------------------------

(defonce app-state (reagent/atom (game/new-game)))
(def current-turn (reaction (game/current-turn @app-state)))
(defn send-event! [e] (swap! app-state game/handle-event e))
