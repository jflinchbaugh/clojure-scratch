(take 5 (cycle (range 2)))

(partition 2 (interleave [1 2 3 4] ))

;; clj#43
(fn [coll n]
  (map
    #(map second %)
    (vals
      (group-by
        first
        (map #(list %1 %2) (cycle (range n)) coll)
      )
    )
  )
)

(rev-inter (range 10) 5)

(partition 5 (range 10))

(map list [0 1 2 3 4] [5 6 7 8 9])

(#(apply map list (partition %2 %1)) (range 10) 5)

; 4clj#44

(fn [rotate input]
  (let
    [
      size (count input)
      shift (mod (+ size rotate) size)
      front (take shift input)
      count-left (- size shift)
      back (reverse (take count-left (reverse input)))
    ]
    (concat back front)
  )
)
