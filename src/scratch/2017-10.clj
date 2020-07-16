(def events
  [
    {:date [1977 3 21] :description "Birth"}
    {:date [1981] :description "Start Preschool at Little People"}
    {:date [1982] :description "Start Kindergarten"}
    {:date [1995 6] :description "Graduate from Eastern York High School"}
    {:date [2000 5] :description "Graduate from Millersville University"}
    {:date [2002 8 10] :description "Married"}
    {:date [2002 12] :description "Moved to Armstrong Ln, Lancaster"}
    {:date [2003 5 10] :description "Paige Born"}
    {:date [2005 7 13] :description "Benedict Born"}
    {:date [2008 1] :description "Started Using Google Calendar"}
    {:date [2008 4 28] :description "Hosted First Open Space at MapQuest"}
    {:date [2008 11 28] :description "Attend Photo Class at HACC"}
    {:date [2009 4 26] :description "First Senior Portraits with Bridget"}
    {:date [2009 4] :description "Start Using a Standing Desk"}
    {:date [2010 12 16] :description "Purchase Ford Fiesta"}
    {:date [2012 5 22] :description "Start at Learning Sciences International"}
    {:date [2014 11 10] :description "Start at Hershey Medical Center"}
    {:date [2014 11 24] :description "Separated"}
    {:date [2015 12 31] :description "Divorce"}
  ]
)

(map :date events)
(map :description events)

(def date {:date [2012 10]})

(def omitted not)

(defn dayOfMonth [event]
  (let [
    [_ _ day] (:date event)]
    day))

(defn monthOfYear [event]
  (let [
    [_ month _] (:date event)]
    month))

(defn year [event]
  (let [
    [year _ _] (:date event)]
    year))

(dayOfMonth {:date [1 2 3]})

(comment all ways to get events with dayOfMonth)
(filter (fn [d] (-> d dayOfMonth)) events)
(filter (fn [d] (dayOfMonth d)) events)
(filter #(-> %1 dayOfMonth) events)
(filter #(dayOfMonth %1) events)
(filter dayOfMonth events)

(comment all ways to get events without dayOfMonth)
(filter (fn [d] (-> d dayOfMonth not)) events)
(filter #(-> %1 dayOfMonth not) events)
(filter #(-> %1 dayOfMonth omitted) events)
(filter (fn [d] (not (dayOfMonth d))) events)
(filter #(not (dayOfMonth %1)) events)
(filter (complement dayOfMonth) events)
(filter (comp not dayOfMonth) events)

(map year (filter (complement dayOfMonth) events))


(def gcd (memoize
  (fn [x y]
    (cond
      (> x y) (recur (- x y) y)
      (< x y) (recur x (- y x))
      :else x))))

(time (gcd 2134 4240))


(apply str (rest (interleave (repeatedly (partial str " ")) ["hi" "there"])))

(->>
  ["hello" "there" "world"]
  (interleave (repeat " "))
  (rest)
  (apply str)
)


(doc join)
