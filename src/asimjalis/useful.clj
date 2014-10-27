(ns asimjalis.useful
  (:gen-class)
  (:use 
    [clojure.pprint]
    [clojure.test      :only [deftest is run-tests]]
    [clojure.template  :only [do-template]]
    [clojure.reflect]
    ))

;[Print Error]

(defn println-err [& args]
  (binding [*out* *err*]
    (apply println args)))

(defn exit-with-error [& messages]
  (apply println-err messages)
  (System/exit 1))

(defn fail [& messages]
  (throw (new Exception (apply str messages))))

(defmacro try-catch [& rest]
  `(try
    ~@rest
    (catch Exception e#
      (println-err "Error:" (.getMessage e#))
      nil)))

;[Dbg]

(defn- date-today []
  (.toString (new java.util.Date (System/currentTimeMillis))))

(defn- pretty-string [& args] 
  (with-out-str 
    (apply pprint args)))

(defn- dbg- [& args]
  (let [line (apply str args)]
    (locking System/out (print line) (flush))))

(defmacro dbg [x]
  `(dbg- 
     "[" (date-today) "] " 
     '~x " = "
     (pretty-string ~x)))


;[Macros]

(defmacro test-block [func-name & expected-and-args]
  `(do-template [expected args] (is (= expected (apply ~(resolve (symbol func-name)) args)))
    ~@expected-and-args))

(defmacro defn-test [func-name & expected-and-args]
  `(deftest ~(symbol (str "test-fun-" func-name )) 
    (test-block ~func-name ~@expected-and-args))) 

(defmacro let-block [& bindings]
  (let [bindings-vec (vec bindings)
        last-var (get bindings-vec (- (count bindings-vec) 2))]
  `(let [~@bindings]
    ~last-var)))

(defmacro defn-let [func-name args & bindings]
  (if (not (vector? args)) (throw "Expected args in vector for " (func-name)))
  `(defn ~func-name ~args
    (let-block ~@bindings)))


;[Show]

(defn show-raw [x]
  (->> 
    (reflect x)
    :members
    (sort-by :name)
    (filter #(-> % :flags (contains? :public)))
    ))

(defn show [x]
  (->> x show-raw pprint))

(defn show-names [x]
  (->> 
    (show-raw x)
    (filter #(-> % :flags (contains? :public)))
    pprint)) 

(defn show-table [x]
  (->> 
    (show-raw x)
    (filter #(-> % :flags (contains? :public)))
    (print-table)) )
