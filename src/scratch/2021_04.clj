(ns scratch.2021-04)

(comment 

  (->>
    ["language" "clojure" "username" "john"]
    (partition 2)
    (reduce (fn [m [k v]] (assoc m k v)) {}))

  (apply hash-map ["language" "clojure" "username" "john"])

  .)
