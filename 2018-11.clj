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
