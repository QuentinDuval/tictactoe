(ns tictactoe.logic-test
  (:require-macros
    [cljs.test :refer (is deftest testing)])
  (:require
    [cljs.test :as test]
    [tictactoe.logic :as logic]
    ))


;; Utils for the tests

(defn get-cells
  [game-state]
  (let [board (:board (peek game-state))]
    (map second board)))

(defn count-if
  [pred coll]
  (count (filter pred coll)))

(defn count-equal
  [value coll]
  (count-if #(= % value) coll))


;; Tests

(deftest test-init-game
  (let [init-game (logic/new-game)]
    (is (=
          (* logic/board-size logic/board-size)
          (count-equal :cell/empty (get-cells init-game))))
    ))


;; Runner

(test/run-tests)
