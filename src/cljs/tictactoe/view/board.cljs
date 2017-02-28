(ns tictactoe.view.board
  (:require
    [tictactoe.logic.board :as board]
    [tictactoe.view.svg.cell :as cell]
    [tictactoe.view.svg.utils :as utils]))


(defn- render-cell
  "Dispatch the rendering of the cell based on the player"
  [type coord on-move-event]
  (let [renderer (case type
                   :owner/none cell/render-square
                   :owner/cross cell/render-cross
                   :owner/circle cell/render-circle)]
    (renderer coord {:on-click #(on-move-event coord)})))


(defn render-board
  "Render the board:
   * Creates a SVG panel
   * Render the cells in it"
  [board {:keys [on-move-event]}]
  (into
    (utils/square-svg-panel board)
    (for [[coord owner] (board/get-owners-by-coord board)]
      [render-cell owner coord on-move-event]
      )))
