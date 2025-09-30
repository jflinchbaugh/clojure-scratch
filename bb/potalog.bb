#!/usr/bin/env bb

(ns pota-log
  (:require [clojure.string :as str]))

(let [qsos (->>
             "/home/john/.local/share/WSJT-X/wsjtx_log.adi"
             slurp
             str/split-lines
             (filter (partial re-matches #"<call.*"))
             (map (fn [s]
                    (->
                      s
                      (str/split #"[<]")
                      (->>
                        (map str/trim)
                        (remove empty?)
                        (map (fn [p]
                                (-> p
                                  (str/split #":\d+>"))))
                        (filter #(= 2 (count %)))
                        (map
                          (fn [[k v]]
                            {(keyword (str/replace k #"_" "-")) v}))
                        (into {})))))
             )]
  (->>
        qsos
        (map (juxt :call :band :mode))
        sort
        (map cons (drop 1 (range)))
        (map (partial str/join "\t"))
        (map println )
        doall))

