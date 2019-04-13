(def minutes (range 0 (* 60 12)))
(def seconds (range 0 (* 60 60 12)))

(defn minute-angle [minute] (* 360 (/ (mod minute 60) 60)))

(defn hour-angle [minute] (* 360 (/ minute (* 60 12))))

(map minute-angle minutes)

(map hour-angle minutes)

(minute-angle 120)

(def angle-pairs (map (juxt minute-angle hour-angle) minutes))

(defn dist [[xh xm] [yh ym]] [(Math/abs (double (- xh yh))) (Math/abs (double (- xm ym)))])

(->>
  (for [a1 angle-pairs
        a2 (map reverse angle-pairs)
        :let [[dh dm] (dist a1 a2)]
        :when (and (< dh 1) (< dm 1))]
    [a1 a2 dh dm])
)

(defn minute-angle [time]
  )

(Math/abs 10)


