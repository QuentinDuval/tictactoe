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
  (some #(board/one-owner? board %) cst/lines))


;; --------------------------------------------------------
;; Game logic (public)
;; --------------------------------------------------------

(def start-turn
  {:board cst/empty-board
   :player :cell/cross})

(defn game-over?
  [turn]
  (or
    (board/full? (:board turn))
    (has-winner? (:board turn))))

(defn valid-move?
  [turn x y]
  (and
    (board/empty-position? (:board turn) x y)
    (not (game-over? turn))))

(defn play-move
  [turn x y]
  (-> turn
    (update :board board/convert-cell (:player turn) x y)
    (update :player next-player)))
