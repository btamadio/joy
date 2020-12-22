(ns joy.ch3)

; Use seq as termination condition along with first/rest:
(defn print-seq [s]
  (when (seq s)  ; evaluates to nil when s is empty
    (prn (first s))
    (recur (rest s))))

; Positional destructuring
(def whole-name ["Guy" "Lewis" "Steele"])
(let [[first-name middle-name last-name] whole-name]
  (str last-name ", " first-name ", " middle-name))
; => Steele, Guy, Lewis

; Using & to bind remaining elements to a var as a new seq
(let [[a b c & more] (range 10)]
  (println "a b c are:" a b c)
  (println "more is: " more))
; a b c are: 0 1 2
; more is: (3 4 5 6 7 8 9)

; Using :as to bind original value to a new var 
(let [range-vec (vec (range 10))
      [a b c & more :as all] range-vec]
  (println "a b c are: " a b c)
  (println "more is: " more)
  (println "all is: " all))
; a b c are:  0 1 2
; more is:  (3 4 5 6 7 8 9)
; all is:  [0 1 2 3 4 5 6 7 8 9]

; Map destructuring
(def guys-name-map
  {:f-name "Guy" :m-name "Lewis" :l-name "Steele"})

(let [{f-name :f-name m-name :m-name l-name :l-name} guys-name-map]
  (str l-name ", " f-name ", " m-name))
; => Steele, Guy, Lewis

(let [{:keys [f-name m-name l-name]} guys-name-map]
    (str l-name ", " f-name ", " m-name))
; => Steele, Guy, Lewis

(let [{f-name :f-name :as whole-name} guys-name-map]
  (println "First name is" f-name)
  (println "Whole name is" whole-name))
; First name is Guy
; Whole name is {:f-name Guy, :m-name Lewis, :l-name Steele}

; Defaults
(let [{:keys [title f-name m-name l-name]
       :or {title "Mr."}} guys-name-map]
  (println title f-name m-name l-name))
; Mr. Guy Lewis Steele

; Using map destructuring on a list of alternating keys and values
; causes the list to first be poured into a map
(defn whole-name [& args]
  (let [{:keys [f-name m-name l-name]} args]
    (str l-name ", " f-name ", " m-name)))
(whole-name :f-name "Guy" :m-name "Lewis" :l-name "Steele")
; => Steele, Guy, Lewis

; Picking indices from a vector
(let [{first-thing 0 last-thing 3} [1 2 3 4]]
  [first-thing last-thing])
; => [1 4]

; Destructuring in function arguments
(defn print-last-name [{:keys [l-name]}]
  (println l-name))
(print-last-name guys-name-map)
; Steele

(defn xors [max-x max-y]
  (for [x (range max-x) y (range max-y)] [x y (bit-xor x y)]))

(defn xors [max-x max-y]
  (for [x (range max-x) y (range max-y)] [x y (rem (bit-xor x y) 256)]))

(defn make-frame [width height]
  (doto (java.awt.Frame.)
    (.setVisible true)
    (.setSize (java.awt.Dimension. width height))))

(defn f-values [f xs ys]
  (for [x (range xs) y (range ys)]
    [x y (rem (f x y) 256)]))

(defn draw-values [f]
  (fn [frame]
    (let [gfx (.getGraphics frame)
          width (int (.getWidth (.getSize frame)))
          height (int (.getHeight (.getSize frame)))]
      (.clearRect gfx 0 0 width height)
      (doseq [[x y v] (f-values f width height)]
        (.setColor gfx (java.awt.Color. v v v))
        (.fillRect gfx x y 1 1)))))




