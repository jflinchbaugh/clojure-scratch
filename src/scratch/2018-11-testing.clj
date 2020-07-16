(require '[clojure.test :as t])

(t/deftest playing
  (t/is (= 4 (+ 2 2)))
)

(t/run-tests)

