(ns tictactoe.test-runner
  (:require
    [cljs.test :as test]
    ))

(test/run-all-tests #"tictactoe.*")
