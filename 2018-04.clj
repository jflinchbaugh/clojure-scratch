; sieve

(def integers (iterate inc 2))

(take 10 integers)

(defn multiple-of? [divisor num]
  (zero? (mod num divisor))
)

(defn sieve [remaining]
  (if (empty? remaining) ())
  (lazy-seq
    (let
      [
        [f & rst] remaining
      ]
      (cons f (sieve (remove #(multiple-of? f %) rst)))
    )
  )
)

(sieve (take 10 integers))
