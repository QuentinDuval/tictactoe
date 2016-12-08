(ns tictactoe.core
  (:require
    [reagent.core :as reagent :refer [atom]]
    [tictactoe.cell :as cell]
    ))

(enable-console-print!)


;; Constants

(def board-size 3)

(def cell-state?
  #{:cell/empty :cell/cross :cell/circle})


;; Game logic

(defn init-board
  []
  (into {}
    (for [x (range board-size)
          y (range board-size)]
      [[x y] :cell/empty]
      )))

(defonce app-state
  (atom {:board (init-board)
         :player :cell/cross}))

(defn play-move
  [board player x y]
  (assoc board [x y] player))

(defn next-player
  [current]
  (if (= current :cell/cross) :cell/circle :cell/cross))

(defn on-move
  [app-state x y]
  (-> app-state
    (update-in [:board] play-move (:player app-state) x y)
    (update-in [:player] next-player)
    ))

(defn on-move-event
  [x y]
  (swap! app-state on-move x y))


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
   [:h1 "tic tac toe"]
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
