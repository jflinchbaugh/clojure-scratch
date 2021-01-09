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


; higher order functions SICP


; linear recursive sum (pops stack on too big)
(defn sum [term a next b]
  (if (> a b)
    0
    (+
     (term a)
     (sum term (next a) next b))))

(defn cube [x] (* x x x))

(comment
  (sum cube 1 inc 10)

  .)

(defn pi-sum [a b]
  (let [pi-term (fn [x] (/ 1.0 (* x (+ x 2))))
        pi-next (fn [x] (+ x 4))]
    (sum pi-term a pi-next b)))

(comment
  (* 8 (pi-sum 1 10000))

  .)

(defn integral [f a b dx]
  (let [add-dx (fn [x] (+ x dx))]
    (*
      (sum f (+ a (/ dx 2.0)) add-dx b)
      dx)))


(comment
  (integral cube 0 1 0.001)

  .)

; iterative sum instead of linear recursive
(defn sum [term a next b]
  (loop [a a
         result 0]
    (if (> a b)
      result 
      (recur (next a) (+ result (term a))))))

(comment

  (sum identity 0 inc 10)

  ; no more overflows! 
  (* 8 (pi-sum 1 1000000000))

  (integral cube 0 1 0.0000001)

  .)
