(ns tictactoe.test-runner
  (:require
    [cljs.test :as test]
    ;;[tictactoe.game-logic-test :as logic-test]
    ;; TODO - Import does not work... why ?
    ))

(test/run-tests
  'tictactoe.game-logic-test)
