(ns tictactoe.test-runner
  (:require
    [cljs.test :as test]
    ))

(test/run-tests
  'tictactoe.logic-test)
