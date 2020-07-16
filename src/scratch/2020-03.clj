(ns scratch.2020-03
  (:require [clojure.java.io :as io]
            [clojure.data.csv :as csv]
            [clojure.string :as str]))

;; ----------
;; toys

(def m [1 2 3 4 [5 6 [7 8]]])

(update-in m [4 2 0] (constantly :a))


;; ---------------------------
;; covid-19 data

(def daily-path
  "/home/john/workspace/COVID-19/csse_covid_19_data/csse_covid_19_daily_reports")

(def daily-file-names
  (->> daily-path
    io/file
    .list
    (filter (partial re-matches #".*\.csv"))
    (map #(str/join "/" [daily-path %]))))

(def daily-data (->>
             daily-file-names
             (map (comp rest csv/read-csv slurp))
             (apply concat)))

(-> (group-by (juxt second first) daily-data) keys sort)


