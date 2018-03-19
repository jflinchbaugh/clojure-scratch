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
      back (drop shift input)
    ]
    (concat back front)
  )
)

; cheshire macros
(defn hi-queen [phrase]
  (str phrase ", so please your Majesty."))

(defn alice-hi-queen [] (hi-queen "My name is Alice"))

(alice-hi-queen)

(defmacro def-hi-queen [name phrase]
  (list 'defn
    (symbol name)
    []
    (list 'hi-queen phrase)))

(macroexpand-1 '(def-hi-queen alice-hi-queen "My name is Alice"))

(def-hi-queen alice-hi-queen "My name is Alice")

(alice-hi-queen)

(def-hi-queen hatter-hi-queen "My name is Mad Hatter")

(hatter-hi-queen)

`(first [1 2 3])

(let [x 5] `(first [~x 2 3]))

(defmacro def-hi-queen [name phrase]
  `(defn ~(symbol name) []
    (hi-queen ~phrase)))

(def-hi-queen hatter-hi-queen "My name is Mad Hatter")

(hatter-hi-queen)

(-> [:a :b :c] (reverse) (list))
(->> [:a :b :c] (reverse) (list))
