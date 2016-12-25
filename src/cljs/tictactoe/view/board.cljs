(ns tictactoe.view.board
  (:require
    [tictactoe.view.cell :as cell]
    [tictactoe.view.constants :as cst]
    [tictactoe.logic.constants :as logic-cst]
    ))


(defn render-board
  "Render the board: pure component that only depends on inputs"
  [board on-move]
  (into
    [:svg.board
     {:view-box (str "0 0 " logic-cst/board-size " " logic-cst/board-size)
      :style {:max-height (str cst/board-pixel-size "px")}}]
    (for [[[x y] cell-owner] board]
      [cell/render-cell cell-owner x y {:on-click #(on-move x y)}])
    ))
