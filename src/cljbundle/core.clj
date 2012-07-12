(ns cljbundle.core)

(use 'clojure.string)

(def bundle-list [])

(defn add-to-bundle-list [item]
  (def bundle-list (conj bundle-list item)))

(defn print-bundle-list []
  (println bundle-list))

(defn write-bundle-list [file]
  (spit file bundle-list))

(defn read-bundle-list [file]
  (def bundle-list (read-string (slurp file))))

(defn prompt [text]
  (print text)
  (flush)
  (read-line))

(def user-options
"
1) Add to bundle list
2) Print bundle list
3) Write bundle list
4) Read bundle list

Action: ")

(defn parse-input [input]
  (case (trim input)
    (or "" "quit") 0
    "1" (add-to-bundle-list (prompt "What would you like to add: "))
    "2" (print-bundle-list)
    "3" (write-bundle-list (prompt "What file to write to: "))
    "4" (read-bundle-list (prompt "What file to read from: "))
    (println "I couldn't understand you")))

(defn -main [& args]
  (loop [input (prompt user-options)]
    (let [action (parse-input input)]
      (when-not (= action 0)
        (recur (prompt user-options)))))
  (println "Bye bye"))