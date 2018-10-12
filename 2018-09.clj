; 4clj #59 implement juxt

; one i like from caterpillar
(defn my-juxt [& funcs]
  (fn [& args]
   	(for [f funcs]
      (apply f args))))

; common one from others
(defn other-juxt [& fs]
  (fn [& as] (map #(apply % as) fs)))

(my-juxt )

(prn "hello")
