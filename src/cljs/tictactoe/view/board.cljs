(ns tictactoe.view.board
  (:require
    [tictactoe.view.svg.cell :as cell]
    [tictactoe.view.svg.utils :as utils]))


(defn- render-cell
  "Dispatch the rendering of the cell based on the player"
  [[coord owner] on-move-event]
  (let [renderer (case owner
                   :owner/none cell/render-square
                   :owner/cross cell/render-cross
                   :owner/circle cell/render-circle)]
    (renderer coord {:on-click #(on-move-event coord)})))


(defn render-board
  "Render the board:
   * Creates a SVG panel
   * Render the cells in it"
  [board {:keys [on-move-event]}]
  (utils/in-square-svg-panel
    (map (fn [cell] [render-cell cell on-move-event]) board)
    ))
