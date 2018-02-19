
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
