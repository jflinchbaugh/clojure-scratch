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
    "data/BeyondPodFeeds.opml"
    io/file
    zip-opml
  )
)

(pprint root)

(defn get-element [xml-root path]
  (zip-xml/xml->
    xml-root
    (comment path)
    zip/node
  )
)

(get-element root [:body :outline :outline])

(def feeds
  (zip-xml/xml->
    root
    :body
    :outline
    :outline
    zip/node
  )
)

(pprint *feeds*)

(def podcast-db
  (into []
    (map (fn [arg]
      { :podcast-name (-> arg :attrs :text)
        :podcast-url (-> arg :attrs :xmlUrl)
      }
    ) *feeds*)
  )
)

(map :podcast-name podcast-db)

(map #(println "podcast name: " (:podcast-name %)
           "\n"
           "podcast url: " (:podcast-url %)
           "\n"
) podcast-db)

