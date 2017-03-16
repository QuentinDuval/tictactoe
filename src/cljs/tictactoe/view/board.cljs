(ns tictactoe.view.board
  (:require
    [tictactoe.logic.board :as board]
    [tictactoe.view.svg.cell :as cell]
    [tictactoe.view.svg.constants :as cst]
    [tictactoe.view.svg.utils :as utils]))


(defn render-board
  "Render the board:
   * Creates a SVG panel
   * Render the cells in it"
  [board {:keys [on-move]}]
  (utils/square-svg-panel
    {:model-size board/size
     :pixel-size cst/board-pixel-size}
    []))
