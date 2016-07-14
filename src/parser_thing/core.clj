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
 "translates a syntax tree into a clojure program
  First it walks down through the tree, displatching each
  type of element to its corresponding function"
  (defn trans-exp_Arithmetic [_tree]
   (list (extract-val (nth _tree 1))   ; second element, the operator
         (extract-val (first _tree))   ; first num
         (extract-val (last _tree)))) ; second num
  (case (first (first tree))
    :exp_Arithmetic (trans-exp_Arithmetic tree)
    :exp_Function (println "FUNCTION thiNG AAGAG")))

(defn exec [s]
 (eval (translate (grammar s))))

