(ns tictactoe.test-runner
  (:require
   [doo.runner :refer-macros [doo-tests]]
   [tictactoe.core-test]
   [tictactoe.common-test]))

(enable-console-print!)

(doo-tests 'tictactoe.core-test
           'tictactoe.common-test)
