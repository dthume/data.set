(ns org.dthume.data.set
  (:require [clojure.set]))

(defprotocol SetAlgebra
  (set-union [lhs rhs])
  (set-intersection [lhs rhs])
  (set-difference [lhs rhs]))

(extend-protocol SetAlgebra
  clojure.lang.PersistentHashSet
  (set-union [lhs rhs]
    (clojure.set/union lhs rhs))
  (set-intersection [lhs rhs]
    (clojure.set/intersection lhs rhs))
  (set-difference [lhs rhs]
    (clojure.set/difference lhs rhs))

  clojure.lang.PersistentTreeSet
  (set-union [lhs rhs]
    (clojure.set/union lhs rhs))
  (set-intersection [lhs rhs]
    (clojure.set/intersection lhs rhs))
  (set-difference [lhs rhs]
    (clojure.set/difference lhs rhs)))

(defn- bubble-max-key [k coll]
  "Move a maximal element of coll according to fn k (which returns a number) 
   to the front of coll."
  (let [max (apply max-key k coll)]
    (cons max (remove #(identical? max %) coll))))

(defn union
  "Return a set that is the union of the input sets, preserving the type of
the first set"
  ([] #{})
  ([s1] s1)
  ([s1 s2]
     (set-union s1 s2))
  ([s1 s2 & sets]
     (->> s2
          (conj sets)
          (bubble-max-key count)
          (reduce set-union s1 bubbled))))

(defn intersection
  "Return a set which is the intersection of the input sets, preserving the
type of the first set."
  ([s1] s1)
  ([s1 s2]
     (set-intersection s1 s2))
  ([s1 s2 & sets]
     (->> s2
          (conj sets)
          (bubble-max-key #(- (count %)))
          (reduce set-intersection s1))))

(defn difference
  "Return a set which is the first set without elements of the remaining sets."
  ([s1] s1)
  ([s1 s2]
     (set-difference s1 s2))
  ([s1 s2 & sets]
     (->> s2
          (conj sets)
          (bubble-max-key count)
          (reduce set-difference s1))))
