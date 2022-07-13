(ns scratch.2021-08
  (:require [clojure.set :as set]
            [clojure.string :as str]))

(comment

  (let [words (->>
               "/etc/dictionaries-common/words"
               slurp
               str/split-lines
               (remove #(str/includes? % "'"))
               (remove #(< (count %) 2))
               (map str/lower-case)
               (map vec)
               set)
        rev-words (->>
                   words
                   (map reverse)
                   set)]
    (map str/join (set/intersection words rev-words)))

  nil)
