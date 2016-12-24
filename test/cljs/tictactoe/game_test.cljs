(ns tictactoe.game-test
  (:require-macros
    [cljs.test :refer (is deftest testing)]
    [clojure.test.check.clojure-test :refer [defspec]]
    )
  (:require
    [cljs.test :as test]
    [clojure.test.check :as tc]
    [clojure.test.check.generators :as gen]
    [clojure.test.check.properties :as prop :include-macros true]
    [tictactoe.logic.constants :as cst]
    [tictactoe.logic.game :as logic]
    ))


;; Utils for the tests

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
    (is (= cst/cell-count (count-empty-cells init-game)))
    ))

(deftest test-game-over
  (let [init-game (logic/new-game)
        end-game (play-moves init-game cst/coordinates)]
    (is (logic/game-over? end-game))
    (is (> cst/cell-count (count-empty-cells end-game)))
    ))


;; Generative testing

(def player-gen
  (gen/elements #{:cell/cross :cell/circle}))

(def cell-gen
  (gen/elements #{:cell/empty :cell/cross :cell/circle}))

(def board-gen
  (apply gen/hash-map
    (interleave
      cst/coordinates
      (repeat cst/cell-count cell-gen)
      )))

(defn valid-on-move-reaction
  [game-state]
  (prop/for-all [[x y] (gen/elements cst/coordinates)]
    (let [next-game-state (logic/on-move game-state x y)]
      (or
        (= (count-empty-cells game-state) (count-empty-cells next-game-state))
        (not= (logic/get-next-player game-state) (logic/get-next-player next-game-state))
        ))))

(defspec next-player-at-start
  100
  (valid-on-move-reaction (logic/new-game)))

(defspec try-move-for-any-board
  100
  (prop/for-all [b board-gen]
    (valid-on-move-reaction b)))


;; Running the tests

(test/run-tests)
