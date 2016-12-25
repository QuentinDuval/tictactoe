(ns tictactoe.view.board
  (:require
    [tictactoe.view.cell :as cell]
    [tictactoe.view.constants :as cst]
    ))


(defn svg-board-plane
  [height-in-cells height-in-pixel]
  [:svg.board
   {:view-box (str "0 0 " height-in-cells " " height-in-cells)
    :style {:max-height (str height-in-pixel "px")}}
   ])

(defn render-board
  "Render the board: pure component that only depends on inputs"
  [board board-cell-size on-move]
  (into
    (svg-board-plane board-cell-size cst/board-pixel-size)
    (for [[cell-pos cell-owner] board]
      (cell/render-cell cell-owner cell-pos {:on-click #(on-move cell-pos)}))
    ))
