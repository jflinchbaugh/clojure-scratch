(ns scratch.2022-07
  (:require [clojure.test :as t]))

(defn fltn
  ([c] (fltn [] c))
  ([a c]
   (cond
     (empty? c) a
     (coll? (first c)) (recur a (concat [] (first c) (rest c)))
     :else (recur (conj a (first c)) (rest c)))))

(comment
  ;; https://4clojure.oxal.org/#/problem/28
  (t/testing
   (t/is (= '(1 2 3 4 5 6) (fltn '((1 2) 3 [4 [5 6]]))))
    (t/is (= '("a" "b" "c") (fltn ["a" ["b"] "c"])))
    (t/is (= '(:a) (fltn '((((:a))))))))

  (t/testing
   (t/is (= [] (fltn '())))
    (t/is (= [1 2 3] (fltn '(1 2 3))))
    (t/is (= [1 2 3] (fltn '((1 2 3)))))
    (t/is (= [1 2 3] (fltn '(((1 2 3))))))
    (t/is (= [1 2 3] (fltn '((1) 2 3))))
    (t/is (= [1 2 3] (fltn '(1 (2) 3))))
    (t/is (= [1 2 3] (fltn '(1 (2 3)))))
    (t/is (= [1 2 3 4] (fltn '((1 2) (3 4)))))
    )

  (fltn [:a :b :c])

  nil)
