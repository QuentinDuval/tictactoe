(ns tictactoe.logic.turn
  (:require
    [tictactoe.logic.board :as board]
    [tictactoe.utils.algo :as algo]))


;; --------------------------------------------------------
;; Game logic (private)
;; --------------------------------------------------------

(def winning-diags
  [(filter #(= (first %) (second %)) board/coordinates)
   (filter #(= (dec board/size) (reduce + %)) board/coordinates)])

(def winning-rows (partition board/size board/coordinates))
(def winning-lines (algo/transpose winning-rows))
(def winning-cell-sets (concat winning-rows winning-lines winning-diags))

;; --------------------------------------------------------

(defn- next-player [current]
  (if (= :owner/cross current) :owner/circle :owner/cross))

(defn- same-owner?
  "Indicates whether all positions are owned by the same player"
  [board positions]
  (let [owners (set (map #(board/get-owner-at board %) positions))]
    (or
      (= owners #{:owner/circle})
      (= owners #{:owner/cross})
      )))

(defn- has-winner?
  "There is a winner if some winning line is owned by the same player"
  [board]
  (some #(same-owner? board %) winning-cell-sets))

(defn- invalid-move?
  "A move if valid if the target cell is empty"
  [turn x y]
  (board/is-cell-owned? (:board turn) x y))


;; --------------------------------------------------------
;; Game logic (public)
;; --------------------------------------------------------

(def start-turn
  {:board board/empty-board
   :player :owner/cross})

(defn game-over?
  "The game is over if either:
   * The board is full
   * There is a winner"
  [{:keys [board]}]
  (or
    (board/full-board? board)
    (has-winner? board)))

(defn next-turn
  "Convert a cell to the player color and switch player"
  [turn x y]
  (if-not (or (game-over? turn) (invalid-move? turn x y))
    (update
      (update turn :board board/convert-cell (:player turn) x y)
      :player next-player)))
