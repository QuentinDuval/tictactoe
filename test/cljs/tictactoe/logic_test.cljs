(ns tictactoe.logic-test
  (:require-macros
    [cljs.test :refer (is deftest testing)])
  (:require
    [cljs.test :as test]
    [clojure.test.check :as tc]
    [clojure.test.check.generators :as gen]
    [clojure.test.check.properties :as prop :include-macros true]
    [tictactoe.logic :as logic]
    ))


;; Utils for the tests

(def cell-count (* logic/board-size logic/board-size))

(defn get-cells
  [game-state]
  (map second (logic/get-board game-state)))

(defn count-if
  [pred coll]
  (count (filter pred coll)))

(defn count-equal
  [value coll]
  (count-if #(= % value) coll))

(defn count-empty-cells
  [game-state]
  (count-equal :cell/empty (get-cells game-state)))

(defn play-moves
  [init-game moves]
  (reduce
    (fn [game [x y]] (logic/on-move game x y))
    moves))


;; Tests

(deftest test-init-game
  (let [init-game (logic/new-game)]
    (is (not (logic/game-over? init-game)))
    (is (= cell-count (count-empty-cells init-game)))
    ))

(deftest test-game-over
  (let [init-game (logic/new-game)
        end-game (play-moves init-game logic/coordinates)]
    (is (logic/game-over? end-game))
    (is (> cell-count (count-empty-cells end-game)))
    ))

;; TODO - Play random move: either the move is done and player changed + one less cell, or not



;; Runner

(test/run-tests)
