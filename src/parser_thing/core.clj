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
  (let [elems (first tree)]
   (if (= :expression (first elems)) ;check we have an expression chain of some sort
     (case (nth elems 1)
        :exp_Arithmetic (trans-exp_Arithmetic tree)
        :exp_Function (println "FUNCTION thiNG AAGAG"))
     (println "first element of parse tree must be :expression!"))))

(defn exec [s]
 (eval (translate (grammar s))))

