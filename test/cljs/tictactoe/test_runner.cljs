(ns tictactoe.test-runner
  (:require
    [cljs.test :refer-macros [run-tests]]
    [tictactoe.game-test]
    ))

(run-tests
  'tictactoe.game-test)
