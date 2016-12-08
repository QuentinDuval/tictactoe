(ns tictactoe.core
  (:require
    [reagent.core :as reagent :refer [atom]]
    [tictactoe.cell :as cell]
    ))

(enable-console-print!)


;; Constants

(def board-size 3)


;; Game logic

(defn new-empty-board
  []
  (into {}
    (for [x (range board-size)
          y (range board-size)]
      [[x y] :cell/empty]
      )))

(defn new-game
  "Instantiate a new game (empty board and reset player)"
  []
  {:board (new-empty-board) :player :cell/cross})

(defn convert-cell
  "Convert the cell to the new player"
  [board player x y]
  (assoc board [x y] player))

(defn next-player
  [current]
  (if (= current :cell/cross) :cell/circle :cell/cross))

(defn on-move
  "Convert the cell to current player, switch player, look at win conditions"
  [game-state x y]
  (-> game-state
    (update-in [:board] convert-cell (:player game-state) x y)
    (update-in [:player] next-player)
    ))


;; Plugging to game state

(defonce app-state (atom (new-game)))
(defn on-move-event [x y]  (swap! app-state on-move x y))


;; Rendering

(defn render-board-cell
  [app-state x y]
  (let [cell-state (get (:board app-state) [x y])]
    (cell/render-cell cell-state x y
      (if (= :cell/empty cell-state)
        {:on-click #(on-move-event x y)} {}))
    ))

(defn tictactoe
  []
  [:div
   [:h1 "Tic Tac Toe"]
   (into
     [:svg.board
      {:view-box (str "0 0 " board-size " " board-size)
       :style {:max-height "500px"}}]
     (for [x (range board-size)
           y (range board-size)]
       [render-board-cell @app-state x y]
       ))
   ])


;; Plug to reagent

(reagent/render
  [tictactoe]
  (js/document.getElementById "app"))
