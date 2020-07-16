(require '(clojure [zip :as zip]))

; http://josf.info/blog/2014/03/21/getting-acquainted-with-clojure-zippers/

(def zzz
  (->
    [
      1
      [
        [
          100
          200
        ]
        :a
        :b
        :c
      ]
      2
      3
      [
        40
        50
        60
      ]
    ]
    zip/vector-zip
  )
)

(-> zzz
  zip/down
  zip/right
  zip/down
  zip/down
  zip/node
)

(-> zzz
  zip/next
  zip/next
  zip/next
)

(defn first-keyword [loc]
	(if (zip/end? loc)
	  nil
	  (if (keyword? (zip/node loc))
	    (zip/node loc)
	    (recur (zip/next loc)))))

(first-keyword zzz)

(source zip/branch?)



; reduce all the things

(def nums (range 1 20))

(filter even? nums)

(reduce
  (fn [col n]
    (if (seq? col)
      (if (even? n)
        (into col n)
        col)
    )
    (recur [] col)
  )
  nums
)

; hiccup

(use 'hiccup.core)
(use 'hiccup.page)
(require
  '[
    hiccup
    [core :as h]
    [page :as p]
  ]
  '[clojure.string :as str]
)

(p/xml-declaration "utf-8")
(p/html5
  [:body
    [:span#thing.only "thing"]
  ]
)
(prn (h/html [:span#thing.only "thing"]))

(-> 'clojure.core ns-map keys)

(p/xml-declaration "utf-8")

(str/join " " )

; testing

; loop

(doc loop)

(loop
  [
    x 1
    total 0
  ]
  (if (>= x 10)
    total
    (recur (+ x 1) (+ total x))
  )
)

; mike's processing toys

(<= 0 1 2 2 2 3 4)


(defn check-grow [state height]
  (p/pprint ["check-grow" state height])
  (if (<= 0 (:r state))
    (update-in state [:shrink] (constantly false))
    state))

(defn check-shrink [state height]
  (p/pprint ["check-shrink" state height])
  (if (and (not (:shrink state))(< (/ height 4) (:r state)))
    (update-in state [:shrink] (constantly true))
    state))

(defn grow-or-shrink [state height]
  (p/pprint ["grow-or-shrink" state height])
  (if (:shrink state)
    (update-in state [:r] dec)
    (update-in state [:r] inc)))


(defn update-state [state]
  (p/pprint ["update-state" state])
  (reduce (fn [new-state state-modification-fn]
            (state-modification-fn new-state (q/height)))
          state
          [check-grow check-shrink grow-or-shrink]))
