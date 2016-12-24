(ns cljs.tictactoe.test-runner
  (:require
    ;;[tictactoe.common-test]
    ;;[tictactoe.game-logic-test] ;; TODO - The import does not work. WHY?
    [cljs.test :as test]
    ))

(test/run-tests
  'tictactoe.common-test
  'tictactoe.game-logic-test)
