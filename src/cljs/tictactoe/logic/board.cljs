(ns tictactoe.logic.board)


(defn convert-cell
  "Convert the cell [x y] of the board"
  [board player x y]
  (assoc board [x y] player))

(defn empty-cell?
  [board x y]
  (= (get board [x y]) :cell/empty))
