(ns scratch.2021-02
  (:require [hawk.core :as hawk]))

(comment
  (def watcher (hawk/watch! [{:paths ["/home/john/workspace"]
                              :handler (fn [ctx event]
                                         (println ctx event)
                                         ctx)}]))

  (hawk/stop! watcher)

  .)


