(ns scratch.2021-01)

(defn ackermann [x y]
  (cond
    (= 0 y) 0
    (= 0 x) (* 2 y)
    (= 1 y) 2
    :else (ackermann (- x 1) (ackermann x (- y 1)))))

(comment

  (ackermann 1 10)

  (ackermann 2 4)

  (ackermann 3 3)

  (let [f (partial ackermann 0)
        g (partial ackermann 1)
        h (partial ackermann 2)]
    (map (juxt f g h) (range 5)))

  .)

(def first-denomination {1 1, 2 5, 3 10, 4 25, 5 50})

(def cc (memoize (fn [amount kinds-of-coins]
                   (cond
                     (= amount 0) 1
                     (or (< amount 0) (= kinds-of-coins 0)) 0
                     :else (+
                            (cc
                             amount
                             (dec kinds-of-coins))
                            (cc
                             (- amount (first-denomination kinds-of-coins))
                             kinds-of-coins))))))

(defn count-change [amount] (cc amount 5))

(comment

  (time (count-change 2000))

  .)
