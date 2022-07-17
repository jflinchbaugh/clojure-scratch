(ns scratch.2022-07
  (:require [clojure.test :as t]
            [clojure.string :as str]))

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
    (t/is (= [1 2 3 4] (fltn '((1 2) (3 4))))))

  (fltn [:a :b :c])

  nil)

(def only-caps (fn [s]
                 (str/join (filter (comp (partial re-matches #"[A-Z]") str) s))))

(comment
  (t/is (= "HLOWRD" (only-caps "HeLlO, WoRlD!")))    (= (__ [1 1 2 1 1 1 3 3]) '((1 1) (2) (1 1 1) (3 3)))

  (= (__ [:a :a :b :b :c]) '((:a :a) (:b :b) (:c)))

  (= (__ [[1 2] [1 2] [3 4]]) '(([1 2] [1 2]) ([3 4])))

  nil)

(def compress (fn [c]
                (reduce
                 (fn [a i] (if (= (last a) i) a (conj a i)))
                 []
                 c)))

(comment
  (t/testing
   (t/is (= "Leroy" (apply str (compress "Leeeeeerrroyyy"))))
    (t/is (= '(1 2 3 2 3) (compress [1 1 2 3 3 2 2 3])))
    (t/is (= '([1 2] [3 4] [1 2]) (compress [[1 2] [1 2] [3 4] [1 2]]))))

  .)

(def pack
  (fn [c]
    (partition-by identity c)))

(comment
  (t/testing
   (t/is (= '((1 1) (2) (1 1 1) (3 3)) (pack [1 1 2 1 1 1 3 3])))
    (t/is (= '((:a :a) (:b :b) (:c)) (pack [:a :a :b :b :c])))
    (t/is (= '(([1 2] [1 2]) ([3 4])) (pack [[1 2] [1 2] [3 4]]))))

  .)

(def double-seq
  (fn [c] (reduce (fn [a i] (conj a i i)) [] c)))

(comment
  (double-seq [1 2 3])

  .)

(def dup-seq
  (fn [c n] (reduce (fn [a i] (apply conj a (repeat n i))) [] c)))

(comment
  (dup-seq [1 2 3] 3)

  .)

(def rng
  (fn rng
    ([a b]
     (rng [] a b))
    ([c a b]
     (if
         (>= a b) c
         (recur (conj c a) (inc a) b)))))

(comment

  (rng 1 5)

  .)
