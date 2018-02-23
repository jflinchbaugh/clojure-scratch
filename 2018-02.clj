
(fn fib
  ([n]
    (fib n []))
  ([n col]
    (cond
      (zero? n)
        []
      (= 1 n)
        [1]
      (= 2 n)
        [1 1]
      :else
        (let
          [
            prev (fib (dec n) col)
            ult (first (reverse prev))
            pen (second (reverse prev))
          ]
          (conj prev (+ ult pen))
        )
    )
  )
)

(fib 0)
(fib 1)
(fib 2)
(fib 3)
(fib 4)
(fib 5)
(fib 6)
(fib 30)
(fib 60)

(first [])

(first (constantly 1))

(+ 1 1 1 1 1)

(apply + [1 1 1 1 1])

(conj [1 2 3] 4)

(
  (fn [n]
    (take n
      (map first
        (take 6 (iterate
          (fn [[a b]] [b (+ a b)])
          [1 1]
        ))
      )
    )
  )
  60
)

(reverse (reverse "racecar"))
(reverse "racecar")

(into () "racecar")

(seq "racecar")


(= (flatten '((1 2) 3 [4 [5 6]])) '(1 2 3 4 5 6))

(
  (fn flat [col]
   (reduce
      (fn [acc cur]
        (concat acc
          (cond
            (coll? cur) (flat cur)
            :else [cur]
          )
        )
      )
      []
      col
    )
  )
  '((1 2) 3 [4 [5 6]])
)

(seq? "a")

(seq? [1 2])

(seq? '(1 2))

(coll? "x")

(coll? [1 2])

(coll? '(1 2))

(clojure.string/join ""  [ 1 2 3 \a])

(apply str [ 1 2 3])

(
  (fn [s]
    (apply str
      (filter
        #(<= (int \A) (int %) (int \Z))
        s
      )
    )
  )
  "xYzZ"
)

(> (int \a) (int \A))

(range (int \A) (int \Z))

(
  (fn [x]
    (reduce
      (fn [acc cur]
        (if (= (last acc) cur)
          acc
          (conj acc cur)
        )
      )
      [] (seq x)
    )
  )
  [1 2 2 3 2]
)

(map first ( partition-by identity [1 1 2 3 4]))


;; 4clj #32
(#(reduce (fn [acc cur] (concat acc [cur cur])) [] %) [1 2])

(#(interleave % %) [1 2])

;; 4clj #33

(
  #(apply interleave (take %2 (repeat %1)))
  [4 5 6] 1
)

(apply interleave (take 1 (repeat [1 2 3])))

(if-let [test true]
  (str "true" test)
  (str "false" test)
)

(defn flower-colors [{:keys [flower-1 flower-2]}]
  (str "First is " flower-1 ", and second is " flower-2)
)

(flower-colors {:flower-1 "red", :flower-2 "white"})

;; cycle through 3 random numbers
(take 10 (cycle (take 3 (repeatedly #(rand-int 100)))))

(
  #(reduce (fn [acc cur] (concat acc (take %2 (repeat cur)))) [] %1)
  [4,5,6] 1
)

(repeat 2 1)

(mapcat (fn [x] [x x]) [1])

(
(fn [coll n]
  (mapcat (partial repeat n) coll))
  [4,5,6] 2
)

;; 4clj #34
(
  #((fn [acc cur end] (if (>= cur end) acc (recur (conj acc cur) (inc cur) end))) [] %1 %2)
  1 5
)

((fn [x y] (take (- y x) (iterate inc x))) 1 4)

(sort 1 2 3)

;; 4clj #38
(fn [& others] (-> others sort reverse first))

;; 4clj #39

#(mapcat (fn [x y] [x y]) %1 %2)

#(mapcat list %1 %2)

;; 4clj #40 interpose
(#(rest (mapcat list (repeat %1) %2)) ", " [:a :b])
