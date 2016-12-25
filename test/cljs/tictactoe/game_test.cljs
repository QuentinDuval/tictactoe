(ns tictactoe.game-test
  (:require-macros
    [cljs.test :refer (is deftest testing)]
    [clojure.test.check.clojure-test :refer [defspec]]
    )
  (:require
    ;;[tictactoe.common-test]
    [cljs.test :as test]
    [clojure.test.check :as tc]
    [clojure.test.check.generators :as gen]
    [clojure.test.check.properties :as prop :include-macros true]
    [tictactoe.logic.constants :as cst]
    [tictactoe.logic.game :as logic]
    ))


;; ----------------------------------------------------------------------------
;; Utils for the tests
;; ----------------------------------------------------------------------------

(defn get-cells
  [game-state]
  (map second (logic/get-board game-state)))

(defn count-if
  [pred coll]
  (count (filter pred coll)))

(defn count-equal
  [value coll]
  (count-if #(= % value) coll))

(defn count-cells
  [cell-type game-state]
  (count-equal cell-type (get-cells game-state)))

(defn count-empty-cells
  [game-state]
  (count-cells :cell/empty game-state))

(defn play-moves
  [init-game moves]
  (reduce
    (fn [game [x y]] (logic/on-move game x y))
    init-game
    moves))


;; ----------------------------------------------------------------------------
;; Example based tests
;; ----------------------------------------------------------------------------

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

(deftest test-undo-game
  (let [init-game (logic/new-game)
        end-game (play-moves init-game cst/coordinates)
        undo-game (reduce #(logic/on-undo %1) end-game cst/coordinates)]
    (is (logic/game-over? end-game))
    (is (= init-game undo-game))
    ))


;; ----------------------------------------------------------------------------
;; Generators
;; ----------------------------------------------------------------------------

;; This is the only valid generator to define:
;; - Game generator based on board generator + player generator would lead to
;; invalid state constructions. We would test too much and not precisely.
;; - The test should be based on visible API only: it means we should not
;; try to generate the internal state of the game. It would breach
;; encapsulation, and would thus break at any implementation change.
(def coord-gen
  (gen/elements cst/coordinates))

(def game-gen
  (gen/fmap
    #(play-moves (logic/new-game) %)
    (gen/vector coord-gen 0 cst/cell-count)
    ))

;; The following are all bad ideas

#_(def player-gen
    (gen/elements #{:cell/cross :cell/circle}))

#_(def cell-gen
    (gen/elements #{:cell/empty :cell/cross :cell/circle}))

#_(def board-gen
    (apply gen/hash-map
      (interleave
        cst/coordinates
        (repeat cst/cell-count cell-gen)
        )))


;; ----------------------------------------------------------------------------
;; Generative testing
;; ----------------------------------------------------------------------------

(defn valid-next-game?
  [old-game new-game]
  (let [next-player (logic/get-next-player new-game)]
    (and
      (not= (logic/get-next-player old-game) next-player)
      (= (dec (count-empty-cells old-game)) (count-empty-cells new-game))
      (= (count-cells next-player old-game) (count-cells next-player new-game))
      )))

(defn valid-move-properties
  [old-game]
  (prop/for-all [[x y] coord-gen]
    (let [new-game (logic/on-move old-game x y)]
      (or (= old-game new-game) (valid-next-game? old-game new-game))
      )))

(defspec try-move-from-start-game 100
  (valid-move-properties (logic/new-game)))

(defspec try-move-from-valid-game 100
  (prop/for-all [game game-gen] (valid-move-properties game)))

(defn valid-undo-properties
  [old-game]
  (prop/for-all [[x y] coord-gen]
    (let [new-game (logic/on-move old-game x y)]
      (or
        (= old-game new-game)
        (= old-game (logic/on-undo new-game))
        ))))

(defspec try-undo-from-valid-game 100
  (prop/for-all [game game-gen] (valid-undo-properties game)))


;; ----------------------------------------------------------------------------
;; Running the tests
;; ----------------------------------------------------------------------------

(test/run-tests)
