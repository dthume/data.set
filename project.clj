(defproject org.dthume/data.set "0.1.2-SNAPSHOT"
  :description "Protocols for set operations"
  :url "http://github.com/dthume/data.set"
  :license "Eclipse Public License 1.0"
  :plugins [[codox "0.8.9"]
            [lein-marginalia "0.7.1"]
            [lein-midje "3.0.0"]
            [perforate "0.3.3"]]

  :codox {:defaults {:doc/format :markdown}
          :output-dir "doc/codox"}

  :dependencies [[org.clojure/clojure "1.6.0"]]

  :javac-options ["-target" "1.6" "-source" "1.6"]

  :perforate
  {:benchmark-paths ["src/benchmark/clj"]}

  :profiles
  {:dev
   {:source-paths ["src/dev/clj"]
    :dependencies [[midje "1.6.3"]
                   [perforate "0.3.3"]]}

   :benchmark
   {:jvm-opts ^:replace
    ["-XX:+DoEscapeAnalysis"
     "-XX:+UseBiasedLocking"]}

   :site {}}

  :aliases
  {"ci-build"
   ^{:doc "Perform CI build"}
   ["do" ["clean"] ["check"]]
   
   "dev-bench"
   ^{:doc "Run development benchmarks"}
   ["with-profile" "benchmark" "perforate"]

   "dev-repl"
   ^{:doc "Start a clean development NREPL session"}
   ["do" ["clean"] ["repl"]]

   "dev-test"
   ^{:doc "Run development unit tests"}
   ["do" ["clean"] ["midje"]]})
