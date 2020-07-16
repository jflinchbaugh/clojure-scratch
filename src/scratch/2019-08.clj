;; 

(defn my-func [x] (Math/pow 2 x))

(def upper-range 10)

(defn my-other-func [] (map (comp int my-func) (range upper-range)))

(my-other-func)

(with-redefs [
              my-func (constantly 10)
              upper-range 2]
  (my-other-func))
