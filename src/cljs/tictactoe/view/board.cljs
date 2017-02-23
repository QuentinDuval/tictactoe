(ns tictactoe.view.board
  (:require
    [tictactoe.logic.board :as board]
    [tictactoe.view.svg.cell :as cell]
    [tictactoe.view.svg.utils :as utils]))


(defn render-one-cell
  "Dispatch the rendering of the cell based on the player"
  [type cell-pos options]
  (let [renderer
        (case type
          :cell/empty cell/render-square
          :cell/cross cell/render-cross
          :cell/circle cell/render-circle)]
    (renderer cell-pos options)))


(defn render-board
  "Render the board:
   * Creates a SVG panel
   * Render the cells in it"
  [board {:keys [on-move-event]}]
  (into
    (utils/square-svg-panel board)
    (for [[cell-pos cell-owner] (board/get-cells board)]
      [render-one-cell cell-owner cell-pos {:on-click #(on-move-event cell-pos)}]
      )))
