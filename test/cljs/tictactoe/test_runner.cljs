(ns ^:figwheel-always tictactoe.test-runner
  (:require
    [cljs.test :refer-macros [run-all-tests]]
    ))

;; https://nvbn.github.io/2015/06/08/cljs-test/
;; http://www.lispcast.com/testing-clojurescript
;; https://8thlight.com/blog/eric-smith/2016/10/05/a-testable-clojurescript-setup.html

(run-all-tests #"tictactoe.*")
