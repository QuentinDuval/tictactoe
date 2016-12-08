(ns tictactoe.common-test
  #? (:cljs (:require-macros [cljs.test :refer (is deftest testing)]))
  (:require [tictactoe.common :as sut]
            #?(:clj [clojure.test :refer :all]
               :cljs [cljs.test])))

(deftest example-passing-test-cljc
  (is (= 1 1)))
