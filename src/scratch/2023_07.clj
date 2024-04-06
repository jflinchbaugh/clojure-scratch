(ns scratch.2023-07)

;; https://vimeo.com/832754188
;; quick sort, then optimize with mutable implementation

(defn quicksort-fn [elements]
  (cond
    (>= 1 (count elements))
    elements

    :else
    (let [pivot (first elements)
          left (filter #(< % pivot) (rest elements))
          right (filter #(not (< % pivot)) (rest elements))]
      (concat
       (quicksort-fn left)
       [pivot]
       (quicksort-fn right)))))

(comment

  (->>
   1000
   range
   shuffle
   quicksort-fn)


;
  )
