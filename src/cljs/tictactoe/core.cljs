(ns tictactoe.core
  (:require
    [reagent.core :as reagent]
    [tictactoe.view.frame :as frame]
    [tictactoe.store :as store]))


;; TODO - Add ready unit tests
;; TODO - Move constants and board - hidden in logic

(defn tic-tac-toe
  "Main entry point, assemble:
   * the game state
   * the game view"
  []
  [frame/render @store/current-turn
   {:on-restart #(store/send-event! :restart)
    :on-undo #(store/send-event! :undo)
    :on-move #(store/send-event! %)}])

(reagent/render [tic-tac-toe]
  (js/document.getElementById "app"))
