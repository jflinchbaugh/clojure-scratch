#{#_#_1 2 3}

(defn factorial
  "factorial as reduction over sequence"
  [n] (reduce *' (range 1 (inc n))))

(factorial 100)
