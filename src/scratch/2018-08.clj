; 4clj #59 implement juxt

; one i like from caterpillar
(fn my-juxt [& funcs]
  (fn [& args]
   	(for [f funcs]
      (apply f args))))

; common one from others
(fn [& fs]
  (fn [& as] (map #(apply % as) fs)))
