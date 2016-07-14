(ns parser-thing.core
 (:require [instaparse.core :as insta]))

(def grammar
   (insta/parser (slurp "./spec.ebnf")))

(defn extract-opr [container]
  (case (last container)
    "+" '+
    "-" '-
    "*" '*))

(defn extract-val [container]
  "Extracts the value from a syntax container"
  (case (first container)
    :sym_Number (Integer. (last container))
    :sym_Operator (extract-opr container)))

(defn translate [tree]
 "translates a syntax tree into a clojure program"
  (list (extract-val (nth tree 1))  ; second element, the operator
        (extract-val (first tree))  ; first num
        (extract-val (last tree)))) ; second num

(defn exec [s]
 (eval (translate (grammar s))))

