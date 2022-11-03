(comment parse my opml file to print a summary)

(require
  '[clojure.java.io :as io]
  '[clojure.xml :as xml]
  '[clojure.zip :as zip]
  '[clojure.data.zip.xml :as zip-xml]
)

(defn zip-opml [file]
  (->
    file
    xml/parse
    zip/xml-zip
  )
)

(def root
  (->
    "data/podcast-addict.opml"
    io/file
    zip-opml
  )
)

(pprint root)

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

(def podcast-db
  (into []
    (map (fn [arg]
      {
        :podcast-name (-> arg :attrs :text)
        :podcast-url (-> arg :attrs :xmlUrl)
      }
    )
    feeds
   )
  )
)

(defn paid? [{url :podcast-url}] (re-matches #".*auth=.*" url))

(map
  (fn [p]
    (println
      (str "* " (:podcast-name p) " "
        (if (paid? p) "(Private Link)" (:podcast-url p)))))
  podcast-db
)

(comment make a more generic accessor)
(defn get-element [xml-root path]
  (apply zip-xml/xml->
    xml-root
    (comment path)
    zip/node
  )
)

(get-element root [:body :outline :outline])

