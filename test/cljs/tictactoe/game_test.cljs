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
    [tictactoe.logic.turn :as turn]
    ))


;; ----------------------------------------------------------------------------
;; Utils for the tests
;; ----------------------------------------------------------------------------

(defn get-player
  [game-state]
  (get (logic/current-turn game-state) :player))

(defn get-board
  [game-state]
  (get (logic/current-turn game-state) :board))

(defn game-over?
  [game-state]
  (turn/game-over? (logic/current-turn game-state)))

(defn get-cells
  [game-state]
  (map second (get-board game-state)))

(defn count-equal
  [value coll]
  (count (filter #(= % value) coll)))

(defn count-cells
  [cell-type game-state]
  (count-equal cell-type (get-cells game-state)))

(defn count-empty-cells
  [game-state]
  (count-cells :cell/empty game-state))

(defn play-moves
  [init-game moves]
  (reduce logic/play-move init-game moves))


;; ----------------------------------------------------------------------------
;; Example based tests
;; ----------------------------------------------------------------------------

(deftest test-init-game
  (let [init-game (logic/new-game)]
    (testing "New game is not over yet"
      (is (not (game-over? init-game))))
    (testing "All cells are empty"
      (is (= cst/cell-count (count-empty-cells init-game))))
    ))

(deftest test-game-over
  (let [init-game (logic/new-game)
        end-game (play-moves init-game cst/coordinates)]
    (testing "Playing all coordinates results in game over"
      (is (game-over? end-game)))
    (testing "To be game over, some cells have to be taken"
      (is (> cst/cell-count (count-empty-cells end-game))))
    ))

(deftest test-undo-all
  (let [init-game (logic/new-game)
        end-game (play-moves init-game cst/coordinates)
        undo-game (reduce #(logic/undo-last-move %1) end-game cst/coordinates)]
    (testing "Rollbacking all moves should yield the initial game"
      (is (= init-game undo-game)))
    ))

(deftest test-winning-line
  (doseq [i (range (dec cst/board-size))]
    (testing "If each player plays one row, the first player will win"
      (let [hor-moves (interleave (nth cst/rows i) (nth cst/rows (inc i)))
            end-game (play-moves (logic/new-game) hor-moves)]
        (is (game-over? end-game))))
    (testing "If each player plays one column, the first player will win"
      (let [ver-moves (interleave (nth cst/cols i) (nth cst/cols (inc i)))
            game-2 (play-moves (logic/new-game) ver-moves)]
        (is (game-over? game-2))))))


;; ----------------------------------------------------------------------------
;; Generators
;; ----------------------------------------------------------------------------

;; This is the only valid generator to define:
;; - Game generator based on board generator + player generator would lead to
;; invalid state constructions. We would test too much and not precisely.
;; - The test should be based on visible API only: it means we should not
;; try to generate the internal state of the game. It would breach
;; encapsulation, and would thus break at any implementation change.
(def move-gen
  (gen/elements cst/coordinates))

(def game-gen
  (gen/fmap
    #(play-moves (logic/new-game) %)
    (gen/vector move-gen 0 cst/cell-count)
    ))

;; The following are all bad ideas to base our game generation upon

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


;; ----------------------------------------------------------------------------
;; Generative testing
;; ----------------------------------------------------------------------------

(defn valid-game-transition?
  [old-game new-game move]
  (let [new-player (get-player new-game)
        old-player (get-player old-game)
        new-board (get-board new-game)
        old-board (get-board old-game)]
    (and
      (not= old-player new-player)
      (= :cell/empty (get old-board move))
      (= old-player (get new-board move))
      (= (dissoc old-board move) (dissoc new-board move))
      )))

(defn game-move-properties
  [old-game]
  (prop/for-all [move move-gen]
    (let [new-game (logic/play-move old-game move)]
      (or (= old-game new-game) (valid-game-transition? old-game new-game move))
      )))

(defn game-undo-properties
  [old-game]
  (prop/for-all [move move-gen]
    (let [new-game (logic/play-move old-game move)]
      (or
        (= old-game new-game)
        (= old-game (logic/undo-last-move new-game))
        ))))

(defspec try-move-from-start-game 100
  (game-move-properties (logic/new-game)))

(defspec try-move-from-valid-game 100
  (prop/for-all [g game-gen] (game-move-properties g)))

(defspec try-undo-from-valid-game 100
  (prop/for-all [g game-gen] (game-undo-properties g)))


;; ----------------------------------------------------------------------------
;; Running the tests
;; ----------------------------------------------------------------------------

(test/run-tests)
