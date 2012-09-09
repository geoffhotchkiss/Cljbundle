(ns cljbundle.core
  (use clojure.string)
  (use pl.danieljanus.tagsoup)
  (use clojure.pprint)
  (:require [net.cgrand.enlive-html :as html]))


(def parsed-page [])

(def *hb1-url* "")

(defn fetch-url [url]
  (html/html-resource (java.net.URL. url)))

(defn make-list [thing]
  (apply list thing))

(defn get-games [url]
  (html/select (fetch-url url) [:div.download :div.flexbtn :a.a]))

(defn get-games-and-titles [url]
  (html/select (fetch-url url) [:div.row]))

; Returns a list containing the titles of the games
(defn get-game-titles [url] 
  (let [game-titles (make-list (html/select (fetch-url url) [:div.title :a]))
        game-titles (keep (fn [itm] (:content itm)) game-titles)]
        (flatten game-titles)))

(defn hb-games [url]
  (apply list (get-game-titles url)))

(defn add-to-bundle-list [item]
  (def bundle-list (conj bundle-list item)))

(defn print-bundle-list []
  (println "This doesn't do shit!"))

(defn write-bundle-list [file]
  (spit file bundle-list)) 

(defn read-bundle-list [file]
  (def bundle-list (read-string (slurp file))))

(defn parse-page [url]
  (let [games (hb-games url)]
    (keep-indexed (fn [idx itm] (println idx ":" itm "\n")) games)))

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
