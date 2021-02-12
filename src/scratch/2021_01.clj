(ns scratch.2021-01
  (:require [clj-yaml.core :as y]
            [clj-http.client :as h]))

(comment


  (->
    "https://sonatype.github.io/helm3-charts/index.yaml"
    h/get
    :body
    y/parse-string
    :entries
    (->>
      (map (fn [[k v]] v))
      flatten
      (map :urls)
      flatten
      (map (fn [u] [u ((juxt :status :length) (h/get u))]))))
;; => (["https://github.com/sonatype/helm3-charts/releases/download/nexus-iq-server-103.0.0/nexus-iq-server-103.0.0.tgz"
;;      [200 7422]]
;;     ["https://sonatype.github.io/helm3-charts/charts/nexus-iq-server-102.0.1.tgz"
;;      [200 7429]]
;;     ["https://sonatype.github.io/helm3-charts/charts/nexus-iq-server-102.0.0.tgz"
;;      [200 7428]]
;;     ["https://sonatype.github.io/helm3-charts/charts/nexus-iq-server-101.0.0.tgz"
;;      [200 7428]]
;;     ["https://sonatype.github.io/helm3-charts/charts/nexus-iq-server-100.0.0.tgz"
;;      [200 7425]]
;;     ["https://sonatype.github.io/helm3-charts/charts/nexus-iq-server-99.0.0.tgz"
;;      [200 7428]]
;;     ["https://sonatype.github.io/helm3-charts/charts/nexus-iq-server-98.0.0.tgz"
;;      [200 7426]]
;;     ["https://sonatype.github.io/helm3-charts/charts/nexus-iq-server-97.0.0.tgz"
;;      [200 7376]]
;;     ["https://sonatype.github.io/helm3-charts/charts/nexus-iq-server-96.0.1.tgz"
;;      [200 7391]]
;;     ["https://sonatype.github.io/helm3-charts/charts/nexus-iq-server-96.0.0.tgz"
;;      [200 7350]]
;;     ["https://sonatype.github.io/helm3-charts/charts/nexus-iq-server-95.0.0.tgz"
;;      [200 7888]]
;;     ["https://sonatype.github.io/helm3-charts/charts/nexus-iq-server-94.0.0.tgz"
;;      [200 7888]]
;;     ["https://sonatype.github.io/helm3-charts/charts/nexus-iq-server-93.0.0.tgz"
;;      [200 7889]]
;;     ["https://sonatype.github.io/helm3-charts/charts/nexus-iq-server-92.0.0.tgz"
;;      [200 7888]]
;;     ["https://sonatype.github.io/helm3-charts/charts/nexus-iq-server-91.0.0.tgz"
;;      [200 7887]]
;;     ["https://sonatype.github.io/helm3-charts/charts/nexus-iq-server-90.0.0.tgz"
;;      [200 7859]]
;;     ["https://github.com/sonatype/helm3-charts/releases/download/nexus-repository-manager-29.1.0/nexus-repository-manager-29.1.0.tgz"
;;      [200 10391]]
;;     ["https://github.com/sonatype/helm3-charts/releases/download/nexus-repository-manager-29.0.2/nexus-repository-manager-29.0.2.tgz"
;;      [200 10389]]
;;     ["https://github.com/sonatype/helm3-charts/releases/download/nexus-repository-manager-29.0.1/nexus-repository-manager-29.0.1.tgz"
;;      [200 10340]]
;;     ["https://github.com/sonatype/helm3-charts/releases/download/nexus-repository-manager-29.0.0/nexus-repository-manager-29.0.0.tgz"
;;      [200 9307]]
;;     ["https://sonatype.github.io/helm3-charts/charts/nexus-repository-manager-28.1.6.tgz"
;;      [200 9307]]
;;     ["https://sonatype.github.io/helm3-charts/charts/nexus-repository-manager-28.1.5.tgz"
;;      [200 9329]]
;;     ["https://sonatype.github.io/helm3-charts/charts/nexus-repository-manager-28.1.4.tgz"
;;      [200 9327]]
;;     ["https://sonatype.github.io/helm3-charts/charts/nexus-repository-manager-28.1.3.tgz"
;;      [200 9145]]
;;     ["https://sonatype.github.io/helm3-charts/charts/nexus-repository-manager-28.1.2.tgz"
;;      [200 9176]]
;;     ["https://sonatype.github.io/helm3-charts/charts/nexus-repository-manager-28.1.1.tgz"
;;      [200 9341]]
;;     ["https://sonatype.github.io/helm3-charts/charts/nexus-repository-manager-28.1.0.tgz"
;;      [200 9344]]
;;     ["https://sonatype.github.io/helm3-charts/charts/nexus-repository-manager-28.0.4.tgz"
;;      [200 9343]]
;;     ["https://sonatype.github.io/helm3-charts/charts/nexus-repository-manager-28.0.3.tgz"
;;      [200 9343]]
;;     ["https://sonatype.github.io/helm3-charts/charts/nexus-repository-manager-28.0.2.tgz"
;;      [200 9345]]
;;     ["https://sonatype.github.io/helm3-charts/charts/nexus-repository-manager-28.0.1.tgz"
;;      [200 9258]]
;;     ["https://sonatype.github.io/helm3-charts/charts/nexus-repository-manager-28.0.0.tgz"
;;      [200 9265]]
;;     ["https://sonatype.github.io/helm3-charts/charts/nexus-repository-manager-27.0.3.tgz"
;;      [200 9262]]
;;     ["https://sonatype.github.io/helm3-charts/charts/nexus-repository-manager-27.0.2.tgz"
;;      [200 9224]]
;;     ["https://sonatype.github.io/helm3-charts/charts/nexus-repository-manager-27.0.1.tgz"
;;      [200 9315]]
;;     ["https://sonatype.github.io/helm3-charts/charts/nexus-repository-manager-27.0.0.tgz"
;;      [200 9516]]
;;     ["https://sonatype.github.io/helm3-charts/charts/nexus-repository-manager-26.1.3.tgz"
;;      [200 9478]]
;;     ["https://sonatype.github.io/helm3-charts/charts/nexus-repository-manager-26.1.2.tgz"
;;      [200 9471]]
;;     ["https://sonatype.github.io/helm3-charts/charts/nexus-repository-manager-26.1.1.tgz"
;;      [200 9494]]
;;     ["https://sonatype.github.io/helm3-charts/charts/nexus-repository-manager-26.1.0.tgz"
;;      [200 9406]]
;;     ["https://sonatype.github.io/helm3-charts/charts/nexus-repository-manager-25.0.1.tgz"
;;      [200 9466]]
;;     ["https://sonatype.github.io/helm3-charts/charts/nexus-repository-manager-25.0.0.tgz"
;;      [200 9387]]
;;     ["https://sonatype.github.io/helm3-charts/charts/nexus-repository-manager-24.0.0.tgz"
;;      [200 9387]]
;;     ["https://sonatype.github.io/helm3-charts/charts/nexus-repository-manager-23.0.0.tgz"
;;      [200 9388]]
;;     ["https://sonatype.github.io/helm3-charts/charts/nexus-repository-manager-22.1.0.tgz"
;;      [200 9388]]
;;     ["https://sonatype.github.io/helm3-charts/charts/nexus-repository-manager-22.0.0.tgz"
;;      [200 9387]]
;;     ["https://sonatype.github.io/helm3-charts/charts/nexus-repository-manager-21.1.0.tgz"
;;      [200 9387]])


  (mapcat inc [1 2 3 4])

  (map + [1 2 3 4] [5 6 7 8])


  (->>
    ["language" "clojure" "username" "john"]
    (partition 2)
    (reduce (fn [m [k v]] (assoc m k v)) {}))
  ;; => {"language" "clojure", "username" "john"}


  .)

