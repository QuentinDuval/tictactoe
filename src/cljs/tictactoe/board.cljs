(ns tictactoe.board
  (:require
    [tictactoe.cell :as cell]
    [tictactoe.display :as display]
    [tictactoe.logic :as logic]
    ))


(defn render-board-cell
  "Render a cell on the board"
  [board x y on-move]
  (cell/render-cell
    (get board [x y]) x y
    {:on-click #(on-move x y)}))

(defn render-board
  "Render the board: pure component that only depends on inputs"
  [board on-move]
  (into
    [:svg.board
     {:view-box (str "0 0 " logic/board-size " " logic/board-size)
      :style {:max-height (str display/board-pixel-size "px")}}]
    (for [[x y] logic/coordinates]
      [render-board-cell board x y on-move])
    ))
