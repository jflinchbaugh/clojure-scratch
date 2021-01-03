(ns scratch.2020-07
  (:require [clojure.core.logic :as l]
            [clojure.math.combinatorics :as combo]))

(def ops [+ - * /])
(def vals [2 3 3 4])

(comment

  (l/run* [op1 val1 op2 val2 op3 val3 val4]
    (l/membero op1 ops)
    (l/membero op2 ops)
    (l/membero op3 ops)
    (l/membero [val1 val2 val3 val4] (combo/permutations vals)))

  nil)

