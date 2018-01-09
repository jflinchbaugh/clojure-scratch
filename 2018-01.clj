(comment parse my opml file to print a summary)

(keys (ns-aliases *ns*))
(ns-unalias *ns* 'zip)
(ns-unalias *ns* 'xml)
(ns-unalias *ns* 'io)

(require
  '[clojure.java.io :as io]
  '[clojure.xml :as xml]
  '[clojure.java.io :as io]
  '[clojure.zip :as zip]
  '[clojure.data.zip.xml :as zip-xml]
)

(def root (->
  "data/BeyondPodFeeds.opml"
  io/file
  xml/parse
  zip/xml-zip
))

(def feeds
  (zip-xml/xml->
    root
    :body
    :outline
    :outline
    zip/node
  )
)

(pprint feeds)

(def report
  (zipmap
    (map #(-> % :attrs :text) feeds)
    (map #(-> % :attrs :xmlUrl) feeds)
  )
)

(pprint report)

