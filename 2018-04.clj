; sieve

(def integers (iterate inc 2))

(take 10 integers)

(defn multiple-of? [divisor num]
  (zero? (mod num divisor))
)

(defn sieve [remaining]
  (if
    (empty? remaining) ()
    (lazy-seq
      (let
        [
          [f & rst] remaining
        ]
        (cons f (sieve (remove #(multiple-of? f %) rst)))
      )
    )
  )
)

(sieve (take 1000 integers))

; factorial

(defn factorial [n]
  (if
    (zero? n) 1
    (* n (factorial (dec n)))
  )
)

(factorial 20)

(for
  [
    message-char [\a \b \c \d]
    key-char [\x \y \z]
    :while (some? message-char)
  ]
  [message-char key-char]
)

