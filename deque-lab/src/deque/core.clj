(ns deque.core)

(defrecord Deque [front back size])

;; # Your Work

(defn make-deque
  "Create an empty deque."
  []
  (Deque. '() '() 0))

(defn deque-size
  "Return the size of a deque."
  [dq]
  (:size dq))

(defn push-front
  "Adds an element to the front of the deque."
  [dq elt]
  (let [{:keys [front back size]} dq]
    (Deque. (cons elt front) back (inc size))))

(defn push-back
  "Adds an element to the back fo the deque."
  [dq elt]
  (let [{:keys [front back size]} dq]
    (Deque. front (cons elt back) (inc size))))

(defn flip-front
  "Flip the back list to the front list, if necessary."
  [dq]  
(if (empty? (:front dq)) (Deque.  (reverse (:back dq)) '() (:size dq))
 (Deque. (concat (into [] (:front dq)) (into [] (reverse (:back dq)))) '() (:size dq))))



(defn flip-back
  "Flip the front list to the back list, if necessary."
  [dq]
(if (empty? (:back dq)) (Deque. '() (reverse (:front dq)) (:size dq))
 (Deque. '() (concat (into [] (:back dq)) (into [] (reverse (:front dq)))) (:size dq))))

(defn front
  "Return the front element of the deque.  May cause a flip."
  [dq]
 (if (empty? (:front dq)) (first (:front (flip-front dq))) (first (:front dq))))

(defn back
  "Return the back element of the deque.  May cause a flip."
  [dq]
 (if (empty? (:back dq)) (first (:back (flip-back dq))) (first (:back dq)))
  
)

(defn pop-front
  "Pops/dequeues an element from the front of the deque."
  [dq]
 (if (empty? (:front dq)) (if (empty? (:back dq)) dq (Deque. (rest (:front (flip-front dq))) '() (dec (:size dq)))) (Deque. (rest (:front dq)) (:back dq) (dec (:size dq)))))

(defn pop-back
  "Pops/dequeues an element from the back of the deque."
  [dq]
    (if (empty? (:back dq)) (if (empty? (:front dq)) dq (Deque.  '()  (rest (:back (flip-back dq)))  (dec (:size dq)))) (Deque. (:front dq)  (rest (:back dq))  (dec (:size dq)))))
