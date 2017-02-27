(ns tictactoe.logic.turn
  (:require
    [tictactoe.logic.board :as board]
    [tictactoe.logic.constants :as cst]))


;; --------------------------------------------------------
;; Game logic (private)
;; --------------------------------------------------------

(defn- next-player [current]
  (if (= :cell/cross current) :cell/circle :cell/cross))

(defn- same-owner?
  "Indicates whether all positions are owned by the same player"
  [board positions]
  (let [owners (set (map #(get board %) positions))]
    (and
      (not (owners :cell/empty))
      (= 1 (count owners))
      )))

(defn- has-winner?
  [board]
  (some #(same-owner? board %) cst/lines))


;; --------------------------------------------------------
;; Game logic (public)
;; --------------------------------------------------------

(def start-turn
  {:board (board/new-empty-board)
   :player :cell/cross})

(defn game-over?
  [{:keys [board]}]
  (or
    (board/full-board? board)
    (has-winner? board)))

(defn play-move
  [turn x y]
  (if (and (board/empty-cell? (:board turn) x y) (not (game-over? turn)))
    (-> turn
      (update :board board/convert-cell (:player turn) x y)
      (update :player next-player))))
