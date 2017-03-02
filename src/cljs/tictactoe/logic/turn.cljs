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

(defn- sole-owner
  "Indicates whether all positions are owned by the same player"
  [board positions]
  (let [owners (set (map #(board/get-owner-at board %) positions))]
    (case owners
      #{:owner/circle} :owner/circle
      #{:owner/cross} :owner/cross
      nil)))

(defn- invalid-move?
  "A move if valid if the target cell is empty"
  [turn coord]
  (board/has-owner? (:board turn) coord))


;; --------------------------------------------------------
;; Game logic (public)
;; --------------------------------------------------------

(def start-turn
  {:board board/empty-board
   :player :owner/cross})

(defn get-winner
  "Return the winner, or nil if the game has none"
  [{:keys [board]}]
  (some #(sole-owner board %) winning-cell-sets))

(defn game-over?
  "The game is over if either:
   * The board is full
   * There is a winner"
  [{:keys [board] :as turn}]
  (or
    (board/full-board? board)
    (get-winner turn)))

(defn next-turn
  "Convert a cell to the player color and switch player"
  [turn coord]
  (if-not (or (game-over? turn) (invalid-move? turn coord))
    (-> turn
      (update :board board/convert-cell (:player turn) coord)
      (update :player next-player))))
