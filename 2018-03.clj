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

(reduce * (range 1 (inc 3)))

(nth 2 [1 2 3])

; 4clj #49
(#(list (take %1 %2) (drop %1 %2)) 3 [1 2 3 4 5 6])
#(list (take %1 %2) (drop %1 %2))


; 4clj #50
(= (set (
#(vals (group-by type %1))
[1 :a 2 :b 3 :c])) #{[1 2 3] [:a :b :c]})

(type 1)

; 4clj 51

(=
  [1 2 [3 4 5] [1 2 3 4 5]]
  (let [
    [a b & c :as d]
      [1 2 3 4 5]
    ]
    [a b c d]
  )
)

; 4clj 83

(
  (fn [& x]
    (and
        (true? (some true? x))
        (true? (some false? x))
    )
  )
  [ true false]
)

(true? (some false? [true]))

(fn [x y]
  (let
    [
      candidates (reverse (map inc (range (min x y))))
    ]
    (first (filter #(= 0 (mod x %) (mod y %)) candidates))
  )
)

(f 15001 15000000)

; 4clj #107

(
  (
    (fn [p] (fn [x] (reduce * (repeat p x))))
    4
  )
  2
)

(defn raise [p] (fn [x] (reduce * (repeat p x))))

(def square (raise 2))
(square 4)

(def cube (raise 3))
(cube 2)

(repeat 2 1)
