(ns tictactoe.view.board
  (:require
    [tictactoe.view.cell :as cell]
    [tictactoe.view.constants :as cst]
    [tictactoe.view.utils :as utils]
    ))


(defn render-board
  "Render the board:
   * Creates a SVG panel
   * Render the cells in it"
  [board board-cell-size on-move]
  (into
    (utils/square-svg-panel
      {:pixel-size cst/board-pixel-size
       :model-size board-cell-size})
    (for [[cell-pos cell-owner] board]
      [cell/render-cell cell-owner cell-pos {:on-click #(on-move cell-pos)}]
      )))
