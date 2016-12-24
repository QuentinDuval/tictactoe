(ns tictactoe.view.board
  (:require
    [tictactoe.view.cell :as cell]
    [tictactoe.view.constants :as cst]
    [tictactoe.logic.constants :as logic-cst]
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
     {:view-box (str "0 0 " logic-cst/board-size " " logic-cst/board-size)
      :style {:max-height (str cst/board-pixel-size "px")}}]
    (for [[x y] logic-cst/coordinates]
      [render-board-cell board x y on-move])
    ))
