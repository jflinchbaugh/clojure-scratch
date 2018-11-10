(use 'hiccup.core)
(use 'hiccup.page)
(require
;  '(hiccup
;    [core :as h]
;    [page :as p]
;  )
  '(clojure.string :as str)
)

(p/xml-declaration "utf-8")
(p/html5
  [:body
    [:span#thing.only "thing"]
  ]
)
(prn (h/html [:span#thing.only "thing"]))

(-> 'clojure.core ns-map keys)

(p/xml-declaration "utf-8")

(clojure.string/join " " )
