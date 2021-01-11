(ns scratch.2021-01
  (:require [clj-yaml.core :as y]
            [clj-http.client :as h]))

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


(comment


  (->
    "https://sonatype.github.io/helm3-charts/index.yaml"
    h/get
    :body
    y/parse-string
    :entries
    (->>
      (map (fn [[k v]] v))
      flatten
      (map :urls)
      flatten
      (map (fn [u] [u ((juxt :status :length) (h/get u))]))))
;; => (["https://github.com/sonatype/helm3-charts/releases/download/nexus-iq-server-103.0.0/nexus-iq-server-103.0.0.tgz"
;;      [200 7422]]
;;     ["https://sonatype.github.io/helm3-charts/charts/nexus-iq-server-102.0.1.tgz"
;;      [200 7429]]
;;     ["https://sonatype.github.io/helm3-charts/charts/nexus-iq-server-102.0.0.tgz"
;;      [200 7428]]
;;     ["https://sonatype.github.io/helm3-charts/charts/nexus-iq-server-101.0.0.tgz"
;;      [200 7428]]
;;     ["https://sonatype.github.io/helm3-charts/charts/nexus-iq-server-100.0.0.tgz"
;;      [200 7425]]
;;     ["https://sonatype.github.io/helm3-charts/charts/nexus-iq-server-99.0.0.tgz"
;;      [200 7428]]
;;     ["https://sonatype.github.io/helm3-charts/charts/nexus-iq-server-98.0.0.tgz"
;;      [200 7426]]
;;     ["https://sonatype.github.io/helm3-charts/charts/nexus-iq-server-97.0.0.tgz"
;;      [200 7376]]
;;     ["https://sonatype.github.io/helm3-charts/charts/nexus-iq-server-96.0.1.tgz"
;;      [200 7391]]
;;     ["https://sonatype.github.io/helm3-charts/charts/nexus-iq-server-96.0.0.tgz"
;;      [200 7350]]
;;     ["https://sonatype.github.io/helm3-charts/charts/nexus-iq-server-95.0.0.tgz"
;;      [200 7888]]
;;     ["https://sonatype.github.io/helm3-charts/charts/nexus-iq-server-94.0.0.tgz"
;;      [200 7888]]
;;     ["https://sonatype.github.io/helm3-charts/charts/nexus-iq-server-93.0.0.tgz"
;;      [200 7889]]
;;     ["https://sonatype.github.io/helm3-charts/charts/nexus-iq-server-92.0.0.tgz"
;;      [200 7888]]
;;     ["https://sonatype.github.io/helm3-charts/charts/nexus-iq-server-91.0.0.tgz"
;;      [200 7887]]
;;     ["https://sonatype.github.io/helm3-charts/charts/nexus-iq-server-90.0.0.tgz"
;;      [200 7859]]
;;     ["https://github.com/sonatype/helm3-charts/releases/download/nexus-repository-manager-29.1.0/nexus-repository-manager-29.1.0.tgz"
;;      [200 10391]]
;;     ["https://github.com/sonatype/helm3-charts/releases/download/nexus-repository-manager-29.0.2/nexus-repository-manager-29.0.2.tgz"
;;      [200 10389]]
;;     ["https://github.com/sonatype/helm3-charts/releases/download/nexus-repository-manager-29.0.1/nexus-repository-manager-29.0.1.tgz"
;;      [200 10340]]
;;     ["https://github.com/sonatype/helm3-charts/releases/download/nexus-repository-manager-29.0.0/nexus-repository-manager-29.0.0.tgz"
;;      [200 9307]]
;;     ["https://sonatype.github.io/helm3-charts/charts/nexus-repository-manager-28.1.6.tgz"
;;      [200 9307]]
;;     ["https://sonatype.github.io/helm3-charts/charts/nexus-repository-manager-28.1.5.tgz"
;;      [200 9329]]
;;     ["https://sonatype.github.io/helm3-charts/charts/nexus-repository-manager-28.1.4.tgz"
;;      [200 9327]]
;;     ["https://sonatype.github.io/helm3-charts/charts/nexus-repository-manager-28.1.3.tgz"
;;      [200 9145]]
;;     ["https://sonatype.github.io/helm3-charts/charts/nexus-repository-manager-28.1.2.tgz"
;;      [200 9176]]
;;     ["https://sonatype.github.io/helm3-charts/charts/nexus-repository-manager-28.1.1.tgz"
;;      [200 9341]]
;;     ["https://sonatype.github.io/helm3-charts/charts/nexus-repository-manager-28.1.0.tgz"
;;      [200 9344]]
;;     ["https://sonatype.github.io/helm3-charts/charts/nexus-repository-manager-28.0.4.tgz"
;;      [200 9343]]
;;     ["https://sonatype.github.io/helm3-charts/charts/nexus-repository-manager-28.0.3.tgz"
;;      [200 9343]]
;;     ["https://sonatype.github.io/helm3-charts/charts/nexus-repository-manager-28.0.2.tgz"
;;      [200 9345]]
;;     ["https://sonatype.github.io/helm3-charts/charts/nexus-repository-manager-28.0.1.tgz"
;;      [200 9258]]
;;     ["https://sonatype.github.io/helm3-charts/charts/nexus-repository-manager-28.0.0.tgz"
;;      [200 9265]]
;;     ["https://sonatype.github.io/helm3-charts/charts/nexus-repository-manager-27.0.3.tgz"
;;      [200 9262]]
;;     ["https://sonatype.github.io/helm3-charts/charts/nexus-repository-manager-27.0.2.tgz"
;;      [200 9224]]
;;     ["https://sonatype.github.io/helm3-charts/charts/nexus-repository-manager-27.0.1.tgz"
;;      [200 9315]]
;;     ["https://sonatype.github.io/helm3-charts/charts/nexus-repository-manager-27.0.0.tgz"
;;      [200 9516]]
;;     ["https://sonatype.github.io/helm3-charts/charts/nexus-repository-manager-26.1.3.tgz"
;;      [200 9478]]
;;     ["https://sonatype.github.io/helm3-charts/charts/nexus-repository-manager-26.1.2.tgz"
;;      [200 9471]]
;;     ["https://sonatype.github.io/helm3-charts/charts/nexus-repository-manager-26.1.1.tgz"
;;      [200 9494]]
;;     ["https://sonatype.github.io/helm3-charts/charts/nexus-repository-manager-26.1.0.tgz"
;;      [200 9406]]
;;     ["https://sonatype.github.io/helm3-charts/charts/nexus-repository-manager-25.0.1.tgz"
;;      [200 9466]]
;;     ["https://sonatype.github.io/helm3-charts/charts/nexus-repository-manager-25.0.0.tgz"
;;      [200 9387]]
;;     ["https://sonatype.github.io/helm3-charts/charts/nexus-repository-manager-24.0.0.tgz"
;;      [200 9387]]
;;     ["https://sonatype.github.io/helm3-charts/charts/nexus-repository-manager-23.0.0.tgz"
;;      [200 9388]]
;;     ["https://sonatype.github.io/helm3-charts/charts/nexus-repository-manager-22.1.0.tgz"
;;      [200 9388]]
;;     ["https://sonatype.github.io/helm3-charts/charts/nexus-repository-manager-22.0.0.tgz"
;;      [200 9387]]
;;     ["https://sonatype.github.io/helm3-charts/charts/nexus-repository-manager-21.1.0.tgz"
;;      [200 9387]])


  .)

