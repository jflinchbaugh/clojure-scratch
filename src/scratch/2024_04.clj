(ns scratch.2024-04)

(defn ! [n]
  (cond
    ((complement integer?) n) (throw (IllegalArgumentException. "must be integer"))
    (> 1 n) (throw (IllegalArgumentException. "must be positive"))
    (= 1 (abs n)) n
    :else (* n (! ((if (neg? n) inc dec) n)))))


(comment

(! -4.0)

  )
