(source prn)

(comment
  things
)

; do does stuff
(do
  (prn "hello")
  (prn "hello")
  "hello"
)

; parse, split, shift dates

(def data [
  { :name "breakfast" :start "1" }
  { :name "lunch" :start "2" }
  { :name "dinner" :start "3" }
])

(defn rotate
  "rotate the list the left"
  (
    [lst]
    (rotate 1 lst)
  )
  (
    [n lst]
    (let
      [
        c (if (< n 0) (+ (count lst) n) n)
        [front back] (split-at c lst)
      ]

      (concat back front)
    )
  )
)

(defn with-end
  "rotate the :start to make an :end for each entry"
  [data]
  (let
    [
      starts (map :start data)
      ends (rotate -1 starts)
    ]
    (map #(merge %1 %2) data (map #(hash-map :end %) ends))
  )
)

(doc rotate)

(doc with-end)

(with-end data)

(count (map :start data))

(apropos "drop")

(rotate [1 2 3 4])


; spec

(require '[clojure.spec.alpha :as s])

(s/conform odd? 1000)

(s/valid? even? 10)

(s/valid? #{12 13 14} 12)

(s/def ::date inst?)

(doc ::date)

(s/def ::maybe-string (s/nilable string?))

(s/valid? ::maybe-string "x")
(s/valid? ::maybe-string nil)
