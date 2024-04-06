(ns scratch.2023-04)

(comment

  (into [] (map inc) (range 10))

  (transduce (filter odd?) conj (range 10))

  (eduction (comp (map #(* 2 %)) (filter odd?)) conj (range 10))

  )
