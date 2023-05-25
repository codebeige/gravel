;;; # 🚶 Graph Walking
(ns graph-walking
  {:nextjournal.clerk/visibility {:code :hide}}
  (:require [nextjournal.clerk :as clerk]))


^{::clerk/visibility {:result :hide}}
(def sample-graph
  {:x {:edges [:a :b :c]}
   :a {:edges [:d :e]}
   :b {:edges [:c :f]}
   :c {:edges [:g]}
   :d {:edges [:e :h]}
   :e {:edges [:f]}
   :f {:edges []}
   :g {:edges []}
   :h {:edges [:j]}
   :j {:edges [:f]}})

(clerk/vl {:$schema "https://vega.github.io/schema/vega/v5.json"
           :background "#f8f8f8"
           :width 600
           :height 400
           :padding "none"
           :data [{:name "node-data"
                   :values [{:id "x"} {:id "a"} {:id "b"} {:id "c"} {:id "d"}]}
                  {:name "edge-data"
                   :values [{:source "x" :target "a"}
                            {:source "x" :target "b"}
                            {:source "x" :target "c"}
                            {:source "c" :target "d"}
                            {:source "a" :target "b"}
                            {:source "b" :target "c"}]}]
           :marks [{:name "node-marks"
                    :type "symbol"
                    :from {:data "node-data"}
                    :shape "circle"
                    :encode {:enter {:fill {:value "lightblue"}
                                     :size {:value 1600}}}
                    :transform [{:type "force"
                                 :signal "force-transform"
                                 :forces [{:force "center"
                                           :x {:signal "width/2"}
                                           :y {:signal "height/2"}}
                                          {:force "nbody"
                                           :strength -10}
                                          {:force "link"
                                           :links "edge-data"
                                           :id "datum.id"
                                           :distance 90}]}]}
                   {:name "node-labels"
                    :type "text"
                    :interactive false
                    :from {:data "node-marks"}
                    :encode {:enter {:text {:field "id"}}
                             :update {:x {:field "x"}
                                      :y {:field "y"}}}
                    :transform [{:type "force"}]}]})
