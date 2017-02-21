(ns tictactoe.view.board
  (:require
    [tictactoe.logic.board :as board]
    [tictactoe.view.svg.cell :as cell]
    [tictactoe.view.svg.constants :as cst]
    [tictactoe.view.svg.utils :as utils]
    ))


(defmulti render-cell
  "Dispatch the rendering of the cell based on the player"
  (fn [type cell-pos options] type))

(defn render-board
  "Render the board:
   * Creates a SVG panel
   * Render the cells in it"
  [board {:keys [on-move-event]}]
  (into
    (utils/square-svg-panel
      {:pixel-size cst/board-pixel-size
       :model-size (board/get-size board)})
    (for [[cell-pos cell-owner] (board/get-cells board)]
      [render-cell cell-owner cell-pos {:on-click #(on-move-event cell-pos)}]
      )))

(defmethod render-cell :cell/empty
  [_ cell-pos options]
  (cell/render-square cell-pos options))

(defmethod render-cell :cell/circle
  [_ cell-pos options]
  (cell/render-circle cell-pos options))

(defmethod render-cell :cell/cross
  [_ cell-pos options]
  (cell/render-cross cell-pos options))