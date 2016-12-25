(ns tictactoe.view.board
  (:require
    [tictactoe.view.cell :as cell]
    [tictactoe.view.constants :as cst]
    [tictactoe.logic.constants :as logic-cst]
    ))


(defn svg-board-plane
  [height-in-cells height-in-pixel]
  [:svg.board
   {:view-box (str "0 0 " height-in-cells " " height-in-cells)
    :style {:max-height (str height-in-pixel "px")}}
   ])

(defn render-board
  "Render the board: pure component that only depends on inputs"
  [board on-move]
  (into
    (svg-board-plane logic-cst/board-size cst/board-pixel-size)
    (for [[[x y] cell-owner] board]
      (cell/render-cell cell-owner x y {:on-click #(on-move x y)}))
    ))
