(def col [1 2 3 ])

; 4clj #53
(def longest-sequential
  (fn [col]
    (letfn
      [
        (sublists [f]
          (fn [col]
            (for [i (reverse (range 2 (-> col count inc)))]
              (f i col)
            )
          )
        )
        (is-increasing? [c] (apply < c))
      ]
      (let
        [
          ends (sublists take-last)
          fronts (sublists take)
          increasing-seqs (filter is-increasing? (mapcat fronts (ends col)))
          lengths (group-by count increasing-seqs)
          max-length (-> lengths keys max)
          longest (-> lengths sort last second first)
        ]
        (or longest [])
      )
    )
  )
)

(is-increasing? [6 2 3 4])

(longest-sequential [1 2 3 4])

(longest-sequential [7 1 2 6 3 4])

(longest-sequential [2 3 3 4 5])

(longest-sequential [1 0 1 2 3 0 4 5])

(longest-sequential [7 6 5 4])

(def s [5 4 2 2 3 4 5 6 7 8 10 1 2 3 4 5 6 7 8 9])

(def holev
  (fn [s]
    (or
      (first
        (filter #(apply < %) (mapcat #(partition % 1 s) (range (count s) 1 -1)))
      )
      []
    )
  )
)

(holev s)

(or
  (first
    (filter
      #(apply < %)
      (mapcat #(partition % 1 s) (range (count s) 1 -1))
    )
  )
  []
)


(apply < [ 1 2 3 4 6])

; clj #54

(def partit
  (fn partit [s col]
    (if
      (< (count col) s)
      []
      (cons
        (take s col)
        (partit s (drop s col)
      )
      )
    )
  )
)

(partit 2 (range 12))

; clj #55
(
  (fn [col]
    (into {}
      (map
        (fn [[k v]] [k (count v)])
        (group-by identity col)
      )
    )
  )
  [1 1 1 3 5 1 3 4 5]
)

(map #(identity [(first %) (count (second %))]))

; 4clj #56

(def my-unique
  (fn my-unique
    ([col]
      (my-unique col [])
    )
    ([[c & rst :as col] acc]
      (if (empty? col)
        acc
        (my-unique
          rst
          (if
            (some #(= c %) acc)
            acc
            (conj acc c)
          )
        )
      )
    )
  )
)

(def my-unique
  (fn [col]
    (reduce )
  )
)

(my-unique [1 2 3 1 2 3])

(= (my-unique [1 2 1 3 1 2 4]) [1 2 3 4])

(reduce + (range 5))

(def nums [1 2 2 3 3 4 1])

; 4clj #56 shorter
(def my-unique
  (fn [vctr]
    (reduce
      (fn [col x] (if (some #(= x %) col) col (conj col x)))
      []
      vctr
    )
  )
)

; 4clj #57

((fn foo [x] (when (> x 0) (conj (foo (dec x)) x))) 5)


;4clj #58
(fn [f & rst])

(def solution (fn [& rst] (reduce (fn [f1 f2] #(f1 (apply f2 %&))) rst)))

(= true ((solution zero? #(mod % 8) +) 3 5 7 9))

