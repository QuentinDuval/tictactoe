(ns tictactoe.logic.turn
  (:require
    [tictactoe.logic.board :as board]
    [tictactoe.logic.constants :as cst]
    [tictactoe.utils :as utils]
    ))


;; --------------------------------------------------------
;; Game logic (private)
;; --------------------------------------------------------

(defn- next-player [current]
  (if (= :cell/cross current) :cell/circle :cell/cross))

(defn- winning-line?
  [board line]
  (let [owners (utils/get-all board line)]
    (and
      (not-any? #{:cell/empty} owners)
      (= 1 (count (set owners)))
      )))

(defn- has-winner?
  [board]
  (some #(winning-line? board %) cst/lines))


;; --------------------------------------------------------
;; Game logic (public)
;; --------------------------------------------------------

(def start-turn
  {:board cst/empty-board
   :player :cell/cross})

(defn valid-move?
  [{:keys [board] :as current} x y]
  (and
    (board/empty-cell? board x y)
    (not (has-winner? board))))

(defn play-move
  [current x y]
  (-> current
    (update :board board/convert-cell (:player current) x y)
    (update :player next-player)
    ))

(defn game-over?
  [turn]
  (or
    (board/has-empty-cell? (:board turn))
    (has-winner? (:board turn))
    ))