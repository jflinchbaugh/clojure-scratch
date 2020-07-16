(def minutes (range 0 (* 60 12)))

(def minute-tolerance 1)

(def hour-tolerance 1)

(defn minute-angle [minute] (* 360 (/ (mod minute 60) 60)))

(defn hour-angle [minute] (* 360 (/ minute (* 60 12))))

(defn angle-minute [angle] (int (+ 0.5 (* 60 (/ angle 360)))))

(defn angle-hour [angle] (int (* 12 (/ angle 360))))

(defn angle-pair->time [ap]
  (str (angle-hour (first ap)) ":" (angle-minute (second ap))))

(def angle-pairs (map (juxt minute-angle hour-angle) minutes))

(defn abs [n] (Math/abs (double n)))

(defn dist [[xh xm] [yh ym]] [(abs (- xh yh)) (abs (- xm ym))])

(->>
  (for [a1 angle-pairs
        a2 (map reverse angle-pairs)
        :let [[dh dm] (dist a1 a2)]
        :when (and (not= a1 a2) (< dh hour-tolerance) (< dm minute-tolerance))]
    (hash-set (angle-pair->time a1) (angle-pair->time (reverse a2))))
  (filter #(= 2 (count %)))
  (apply hash-set)
  (vec)
  (map vec)
)
