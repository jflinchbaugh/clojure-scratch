(ns scratch.covid
  (:require [clojure.data.csv :as csv]
            [java-time :as t]
            [clojure.java.io :as io]
            [clojure.string :as str]
            [next.jdbc :as jdbc]))

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
    (re-matches #"\d+/\d+/\d{4}" s) (t/local-date "M/d/yyyy" s)
    (re-matches #"\d+/\d+/\d{2}" s) (t/local-date "M/d/yy" s)
    (re-matches #"\d+/\d+/\d{2} \d+:\d+" s) (t/local-date "M/d/yy H:m" s)
    (re-matches #"\d+/\d+/\d{4} \d+:\d+" s) (t/local-date "M/d/yyyy H:m" s)
    (re-matches #"\d+-\d+-\d+T\d+:\d+:\d+" s) (t/local-date "y-M-d'T'H:m:s" s)
    (re-matches #"\d+-\d+-\d+ \d+:\d+:\d+" s) (t/local-date "y-M-d H:m:s" s)
    :else (throw (IllegalArgumentException. (str "Bad date: " s)))))

(defn fix-date [m] (update-in m [:date] parse-date))

(defn parse-int [i] (if (str/blank? i) nil (Integer/parseInt i)))

(defn fix-numbers [m]
  (-> m
      (update-in [:cases] parse-int)
      (update-in [:deaths] parse-int)
      (update-in [:recoveries] parse-int)))

(defn view
  ([f col]
   (let [tp (f col)]
     (doall (map println tp)) col))
  ([col]
   (view (partial take 50) col)))

(def ds (jdbc/get-datasource {:dbtype "h2:mem" :dbname "covid"}))

(def insert-values
  (juxt :date :country :state :county
        :cases :cases-change
        :deaths :deaths-change
        :recoveries :recoveries-change))

(defn drop-table! [ds]
  (jdbc/execute! ds ["drop table covid_day if exists"]))

(defn create-table! [ds]
  (drop-table! ds)
  (jdbc/execute! ds ["
create table covid_day (
  date timestamp,
  country varchar,
  state varchar,
  county varchar,
  case_total int,
  case_change int,
  death_total int,
  death_change int,
  recovery_total int,
  recovery_change int,
  primary key (date, country, state, county))"]))

(defn insert-day! [ds r]
  (jdbc/execute! ds
                 (cons "
insert into covid_day (
  date,
  country,
  state,
  county,
  case_total,
  case_change,
  death_total,
  death_change,
  recovery_total,
  recovery_change
) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"
                       (insert-values r))))

(def location-grouping (juxt :country :state :county))

(def table-keys (juxt :country :state :county :date))

(defn calc-changes [lst new]
  (let [prev (last lst)]
    (conj
     lst
     (merge
      new
      {:cases-change
       (-
        (or (:cases new) 0)
        (or (:cases prev) 0))
       :deaths-change
       (-
        (or (:deaths new) 0)
        (or (:deaths prev) 0))
       :recoveries-change
       (-
        (or (:recoveries new) 0)
        (or (:recoveries prev) 0))}))))

(defn ammend-changes [col]
  (->> col
       (sort-by table-keys)
       (group-by location-grouping)
       (reduce-kv
        (fn [m k v]
          (assoc m k (reduce calc-changes [] v))) {})
       vals
       flatten))

(defn latest-daily [col]
  (->> col
       (sort-by table-keys)
       (group-by table-keys)
       (reduce-kv
        (fn [m k v]
          (assoc m k (last v))) {})
       vals
       flatten))

(defn has-changes? [r]
  (not= [0 0 0] ((juxt :cases-change :deaths-change :recoveries-change) r)))

(defn days-ago [days date]
  (-> date (t/adjust t/minus (t/days days))))

(defn stage-data! [ds input-dir]
  (create-table! ds)
  (->>
   "/home/john/workspace/COVID-19/csse_covid_19_data/csse_covid_19_daily_reports"
   read-csv
   (pmap cols->maps)
   (pmap fix-date)
   (pmap fix-numbers)
   latest-daily
   ammend-changes
   (filter has-changes?)
   (pmap (partial insert-day! ds))
   doall
   count))

(defn cases-by-window [ds country state date days]
  (jdbc/execute!
   ds
   ["
select county, sum(case_change)
from covid_day
where date >= ?
and date <= ?
and country = ?
and state = ?
group by county
"
    (days-ago 14 date)
    date
    country
    state]))

(defn series-by-county [ds country state county]
  (jdbc/execute!
   ds
   ["
select
  date,
  case_total, case_change,
  death_total, death_change,
  recovery_total, recovery_change
from covid_day
where country = ?
and state = ?
and county = ?
order by date, country, state, county
"
    country
    state
    county]))

(comment
  (stage-data! ds "/home/john/workspace/COVID-19/csse_covid_19_data/csse_covid_19_daily_reports")

  (->>
   (cases-by-window ds "US" "Pennsylvania" (t/local-date) 14)
   (map (comp prn vals)))

  (->>
   (series-by-county ds "US" "Pennsylvania" "Lancaster")
   (map (comp prn vals)))

  nil)
