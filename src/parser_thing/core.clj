(ns parser-thing.core
 (:require [instaparse.core :as insta]))

(def grammar
 (insta/parser (slurp "./spec.ebnf")))

(defn extract-val [container]
  "Extracts the value from a syntax container"
  (Integer. (last container)))

(defn extract-opr [container]
  (case (last container)
    "+" '+
    "-" '-
    "*" '*))

(defn translate [tree]
 "translates a syntax tree into a clojure program"
  (list (extract-opr (nth tree 1)) (extract-val (first tree)) (extract-val (last tree))))

(defn parse [s]
 (grammar s))

