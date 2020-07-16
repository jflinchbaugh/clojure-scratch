(ns scratch.2019-12
  (:require [clojure.core.logic :refer :all]))

(run* [q]
  (== q [10 12]))


(run* [a b]
  (fresh [c]
    (membero a (range 12))
    (membero b [1 4 5 6 true])
    (== a b)
    (== a c)))


(run* (fresh [a]
       (membero a [1 2 3])))

