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
  (t/testing "fltn"
    (t/is (= '(1 2 3 4 5 6) (fltn '((1 2) 3 [4 [5 6]]))))
    (t/is (= '("a" "b" "c") (fltn ["a" ["b"] "c"])))
    (t/is (= '(:a) (fltn '((((:a))))))))

  (t/testing "fltn"
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
  (t/testing "compress"
    (t/is (= "Leroy" (apply str (compress "Leeeeeerrroyyy"))))
    (t/is (= '(1 2 3 2 3) (compress [1 1 2 3 3 2 2 3])))
    (t/is (= '([1 2] [3 4] [1 2]) (compress [[1 2] [1 2] [3 4] [1 2]]))))

  .)

(def pack
  (fn [c]
    (partition-by identity c)))

(comment
  (t/testing "pack"
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

(def my-max (fn [& c] (-> c sort last)))

(def intlv
  (fn [a b]
    (mapcat (fn [x y] [x y]) a b)))

(comment

  (t/is (= '(1 :a 2 :b 3 :c) (intlv [1 2 3] [:a :b :c])))

  .)

(def intps
  (fn [v c]
    (rest (mapcat (fn [x y] [x y]) (repeat v) c))))

(comment
  (t/is (= [1 0 2 0 3] (intps 0 [1 2 3])))

  .)

(def drop-nth
  (fn [c n]
    (mapcat
     (fn [v i] (if (= i n) [] [v]))
     c
     (cycle (range 1 (inc n))))))

(comment

  (t/testing "drop-nth"
    (t/is (= [1 3 5] (drop-nth [1 2 3 4 5 6] 2)))
    (t/is (= [1 3 5] (drop-nth [1 2 3 4 5] 2))))

  .)

(def fac
  (fn fac [x]
    (cond
      (= 0 x) 1
      :else (* x (fac (dec x))))))

(comment

  (t/testing "fac"
    (t/is (= 1 (fac 1)))
    (t/is (= 6 (fac 3)))
    (t/is (= 120 (fac 5)))
    (t/is (= 40320 (fac 8))))

  .)

(def rev-intlv
  (fn [c n]
    (map (fn [l] (map first l))
         (vals
          (group-by second
                    (map
                     (fn [v i] [v i])
                     c
                     (cycle (range 1 (inc n)))))))))

(comment
  (t/testing "rev-intlv"
    (t/is (= '((1 3 5) (2 4 6)) (rev-intlv [1 2 3 4 5 6] 2)))
    (t/is (= '((0 3 6) (1 4 7) (2 5 8)) (rev-intlv (range 9) 3)))
    (t/is (= '((0 5) (1 6) (2 7) (3 8) (4 9)) (rev-intlv (range 10) 5))))

  .)

(def rotate
  (fn [n c]
    (let [l (count c)
          rn (mod n l)]
      (concat (drop rn c) (take rn c)))))

(comment

  (t/testing "rotate"
    (t/is (= '(3 4 5 1 2) (rotate 2 [1 2 3 4 5])))
    (t/is (= '(4 5 1 2 3) (rotate -2 [1 2 3 4 5])))
    (t/is (= '(2 3 4 5 1) (rotate 6 [1 2 3 4 5])))
    (t/is (= '(:b :c :a) (rotate 1 '(:a :b :c))))
    (t/is (= '(:c :a :b) (rotate -4 '(:a :b :c)))))

  .)

(def longest-sub-seq
  (fn [c]
    (let [increasing (->> c
                          (partition 2 1)
                          (partition-by (partial apply <))
                          (filter (fn [i] (apply < (first i))))
                          (map
                           (fn [i]
                             (concat
                              [(ffirst i)]
                              (map second i)))))
          length (apply max (cons 0 (map count increasing)))]
      (->> increasing
        (cons [])
        (filter (fn [i] (= length (count i))))
        first))))

(comment

  (t/testing "longest-sub-seq"
    (t/is (= [0 1 2 3] (longest-sub-seq [1 0 1 2 3 0 4 5])))
    (t/is (= [5 6] (longest-sub-seq [5 6 1 3 2 7])))
    (t/is (= [3 4 5] (longest-sub-seq [2 3 3 4 5])))
    (t/is (= [] (longest-sub-seq [7 6 5 4]))))

  .)

(def prtn
  (fn prtn [n c]
    (if (> n (count c)) []
        (concat [(take n c)] (prtn n (drop n c))))
    ))

(comment
  (t/testing "partition"
    (t/is (= '((0 1 2) (3 4 5) (6 7 8)) (prtn 3 (range 9))))
    (t/is (= '((0 1) (2 3) (4 5) (6 7)) (prtn 2 (range 8))))
    (t/is (= '((0 1 2) (3 4 5)) (prtn 3 (range 8)))))


  .)

(def freq
  (fn [c]
    (->> c (group-by identity) (map (fn [[k v]] [k (count v)])) (into {}))))
(comment
  (t/is (= {:a 2, :b 3} (freq [:b :a :b :a :b])))

  .)

(def dedup
  (fn [c]
    (reduce (fn [a i] (if (some #{i} a) a (conj a i))) [] c)
    ))

(comment

  (t/is (= [:a :b :c] (dedup [:a :a :b :b :c :c])))

  (dedup [1 2 3])

  (#{1} 2)

  (type #{})


  ((fn foo [x] (when (> x 0) (conj (foo (dec x)) x))) 5)

  .)

(def compose
  (fn [& fs] (reduce #(fn [& r] (% (apply %2 r))) fs )))

(comment
  (t/testing "compose"
    (t/is (= ((compose rest reverse) [1 2 3 4]) [3 2 1]))
    (t/is (= ((compose (partial + 3) second) [1 2 3 4]) 5))

    (t/is (= ((compose zero? #(mod % 8) +) 3 5 7 9) true))

    (t/is (= ((compose #(.toUpperCase %) #(apply str %) take) 5 "hello world") "HELLO")))


  .)

