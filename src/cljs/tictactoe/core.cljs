(ns tictactoe.core
  (:require
    [reagent.core :as reagent :refer [atom]]
    [tictactoe.cell :as cell]
    [tictactoe.panel :as panel]
    [tictactoe.utils :as utils])
  (:require-macros
    [reagent.ratom :refer [reaction]]
    ))

(enable-console-print!)


;; Constants

(def board-size 3)
(def coordinates
  (for [x (range board-size)
        y (range board-size)]
    [x y]))

(def rows (partition 3 coordinates))
(def cols (utils/transpose rows))
(def diags
  [(filter #(= (first %) (second %)) coordinates)
   (filter #(= (dec board-size) (reduce + %)) coordinates)])
(def lines (concat rows cols diags))


;; Game logic

(defn new-empty-board
  []
  (into {} (map (fn [c] [c :cell/empty])) coordinates))

(defn new-game
  "Instantiate a new game (empty board and reset player)"
  []
  [{:board (new-empty-board) :player :cell/cross}])

(defn convert-cell
  "Convert the cell to the new player"
  [board player x y]
  (assoc board [x y] player))

(defn next-player [current]
  (case current
    :cell/cross :cell/circle
    :cell/circle :cell/cross))

(defn winning-line?
  [board line]
  (let [owners (utils/get-all board line)]
    (and
      (not-any? #{:cell/empty} owners)
      (= 1 (count (set owners)))
      )))

(defn game-over?
  [board]
  (some #(winning-line? board %) lines))

(defn valid-move?
  [board x y]
  (and
    (not (game-over? board))
    (= :cell/empty (get board [x y]))
    ))

(defn on-move
  "Convert the cell to current player, switch player, look at win conditions"
  [game-state x y]
  (let [current (peek game-state)]
    (conj game-state
      (-> current
        (update :board convert-cell (:player current) x y)
        (update :player next-player)
        ))))

(defn on-undo
  "Remove the last game if there is enough game played"
  [game-state]
  (if (< 1 (count game-state)) (pop game-state) game-state))



;; Plugging to game state

(defonce app-state (atom (new-game)))
(def current-state (reaction (peek @app-state)))

(defn on-move-event [x y] (swap! app-state on-move x y))
(defn on-restart-event [] (reset! app-state (new-game)))
(defn on-undo-event [] (swap! app-state on-undo))


;; Rendering

(defn render-board-cell
  [board x y]
  (cell/render-cell (get board [x y]) x y
    (if (valid-move? board x y)
      {:on-click #(on-move-event x y)} {})))

(defn render-board
  [board]
  (into
    [:svg.board
     {:view-box (str "0 0 " board-size " " board-size)
      :style {:max-height "500px"}}]
    (for [[x y] coordinates]
      [render-board-cell board x y])
    ))

(defn tictactoe
  []
  [:div
   (panel/render-top-panel {:on-restart on-restart-event :on-undo on-undo-event})
   (render-board (:board @current-state))
   ])


;; Plug to reagent

(reagent/render [tictactoe] (js/document.getElementById "app"))
