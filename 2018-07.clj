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

(partition 1 s)

(apply < [ 1 2 3 4 6])

