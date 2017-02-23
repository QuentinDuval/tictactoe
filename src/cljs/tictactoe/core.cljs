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
   {:on-restart-event #(store/send-event! :restart)
    :on-undo-event #(store/send-event! :undo)
    :on-move-event #(store/send-event! %)}])

(reagent/render [tic-tac-toe]
  (js/document.getElementById "app"))
