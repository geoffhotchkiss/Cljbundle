(ns cljbundle.core)

(use 'clojure.string)
(use 'pl.danieljanus.tagsoup)

(def bundle-list [])

(def parsed-page [])

(defn add-to-bundle-list [item]
  (def bundle-list (conj bundle-list item)))

(defn print-bundle-list []
  (println bundle-list))

(defn write-bundle-list [file]
  (spit file bundle-list))

(defn read-bundle-list [file]
  (def bundle-list (read-string (slurp file))))

(defn parse-humble-bundle [page]
  (def body (page 3))
  (def wrapper (body 19))
  (def main (wrapper 2))
  (def papers-content (main 5))
  (def whitebox (papers-content 6))
  (def whitecontent-nopadding (whitebox 5))
  (def shrinkwrapper-expanded (whitecontent-nopadding 2))
  (def shrinksizer (shrinkwrapper-expanded 2))
  (def regular-download-list (shrinksizer 4))
  (def aquaria-list (regular-download-list 8))
  (def gish-list (regular-download-list 10))
  (def lugaru-list (regular-download-list 12))
  (def penumbraoverture-list (regular-download-list 14))
  (def samorost2-list (regular-download-list 16))
  (def worldofgoo-list (regular-download-list 18))
  )

(defn parse-page [url]
  (def parsed-page (parse url))
  (parse-humble-bundle parsed-page)
  (println lugaru-list))

(defn write-parsed-page [page]
  (spit "parsedpage.txt" page))

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
5) Parse page
6) Write parsed website

Action: ")

(defn parse-input [input]
  (case (trim input)
    (or "" "quit") 0
    "1" (add-to-bundle-list (prompt "What would you like to add: "))
    "2" (print-bundle-list)
    "3" (write-bundle-list (prompt "What file to write to: "))
    "4" (read-bundle-list (prompt "What file to read from: "))
    "5" (parse-page (prompt "What url do you want to parse: "))
    "6" (write-parsed-page parsed-page)
    (println "I couldn't understand you")))

(defn -main [& args]
  (loop [input (prompt user-options)]
    (let [action (parse-input input)]
      (when-not (= action 0)
        (recur (prompt user-options)))))
  (println "Bye bye"))