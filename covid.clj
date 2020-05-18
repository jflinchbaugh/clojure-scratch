(ns scratch.covid
  (:require [clojure.core.logic :as l]
            [clojure.data.csv :as csv]
            [clojure.instant :as instant]
            [java-time :as t]
            [clojure.java.io :as io]
            [clojure.string :as str]))

(defn read-6 [[n2 n3 n4 n5 n6 n7]]
  {:county ""
   :state n2
   :country n3
   :date n4
   :cases n5
   :deaths n6
   :recoveries n7})

(def read-8 read-6)

(defn read-12 [[_ n1 n2 n3 n4 _ _ n7 n8 n9]]
  {:county n1
   :state n2
   :country n3
   :date n4
   :cases n7
   :deaths n8
   :recoveries n9})

(defn read-csv [path]
  (->> path
       io/file
       .list
       (filter (partial re-matches #".*\.csv"))
       (pmap
         (comp
           rest
           csv/read-csv
           io/reader
           (partial io/file path)))
       (reduce concat)))

(defn cols->maps [line]
  ((case (count line)
           6 read-6
           8 read-8
           12 read-12) line))

(defn parse-date [s]
  (cond
    (re-matches #".*/.*/.*" s) s
    (re-matches #".*-.*-.*T.*" s) s
    (re-matches #".*-.*-.*" s) s
    :else (instant/read-instant-date s)))

(defn fix-date [m] (update-in m [:date] parse-date))

(comment

  (parse-date "x")

  (dtf/parse dtf/iso-date "2020-05-20")

  (if (re-matches #".*" "1") "yay")

  (->>
   "/home/john/workspace/COVID-19/csse_covid_19_data/csse_covid_19_daily_reports"
   read-csv
   (pmap cols->maps)
   (pmap fix-date)
   (filter #(= ["Lancaster" "Pennsylvania"] ((juxt :county :state) %)))
   (sort-by :date)
   (pmap :date))

  nil)
