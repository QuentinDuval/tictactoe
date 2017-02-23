(ns tictactoe.core
  (:require
    [reagent.core :as reagent]
    [tictactoe.view.frame :as frame]
    [tictactoe.store :as store]))


;; TODO - Add message to indicate winner
;; TODO - Add ready unit tests
;; TODO - Move constants and board - hidden in logic

(defn tic-tac-toe
  "Main entry point, assemble:
   * the game state
   * the game view"
  []
  [frame/render @store/current-board
   {:on-restart-event store/on-restart-event
    :on-undo-event store/on-undo-event
    :on-move-event store/on-move-event}])

(reagent/render [tic-tac-toe]
  (js/document.getElementById "app"))
