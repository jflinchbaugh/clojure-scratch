(source prn)



(comment
  things
)


(do
  (prn "hello")
  (prn "hello")
  "hello"
)

; parse, split, shift dates

(def data [
  { :name "breakfast" :start "1" }
  { :name "lunch" :start "2" }
  { :name "dinner" :start "3" }
])

(defn rotate [lst]
  (concat
    (rest lst)
    (-> lst first str (cons []))))

(defn with-end
  "rotate the :start to make an :end for each entry"
  [data]
  (let [
    starts (map :start data)
    ends (rotate starts)]
    (map #(merge %1 %2) data (map #(hash-map :end %) ends))
  )
)

(doc with-end)

(with-end data)

(apropos "drop")
