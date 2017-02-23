(ns tictactoe.logic.turn
  (:require
    [tictactoe.logic.board :as board]
    [tictactoe.logic.constants :as cst]))


;; --------------------------------------------------------
;; Game logic (private)
;; --------------------------------------------------------

(defn- next-player [current]
  (if (= :cell/cross current) :cell/circle :cell/cross))

(defn- has-winner?
  [board]
  (some #(board/same-owner? board %) cst/lines))


;; --------------------------------------------------------
;; Game logic (public)
;; --------------------------------------------------------

;; TODO - Add spec for the model?

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
