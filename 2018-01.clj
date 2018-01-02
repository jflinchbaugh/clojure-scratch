(comment parse my opml file to print a summary)

(ns-aliases *ns*)
(ns-unalias *ns* 'zip)

(require
  '[clojure.java.io :as io]
  '[clojure.data.xml :as xml]
  '[clojure.zip :as zip]
)

(def opml (->
  "data/BeyondPodFeeds.opml"
  io/file
  io/reader
  xml/parse
  zip/xml-zip
))

(->
  opml
  zip/down
  zip/right
  zip/children
  pprint
)
