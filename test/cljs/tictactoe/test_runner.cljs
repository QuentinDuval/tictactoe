(ns tictactoe.test-runner
  (:require
    [doo.runner :refer-macros [doo-tests]]
    [tictactoe.common-test]
    [tictactoe.game-test]
    ))

(enable-console-print!)

(doo-tests
  'tictactoe.game-test
  'tictactoe.common-test
  )
