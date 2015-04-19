(ns heap-lab.core)

;; # Array Based Heaps
;;
;; Just in time for thanksgiving, a simple lab about Heaps!
;;
;; We will use vectors to handle this, with a top-level record
;; to keep track of the vector and the size.

(defrecord Heap [size data])

;; We will initialize this using the `make-heap` function.

(defn make-heap
  "Creates an empty heap.  Specify the size for the data vector.
The vector will be populated with `nil`."
  [size]
  (Heap. size (apply vector (repeat size nil))))

;; To access the elements of the heap, we will use these functions
;; `get`, `left`, `right`, and `parent`.

(defn heap-get
  "Return the value of the heap vector at the given index.
Throws an exception if the index is out of the range.
this is part of the implementation, not for public consumption."
  [heap loc]
  (cond (>= loc (count (:data heap)))
        (throw (Exception. (str "Get called with " loc " but last vector slot is " (dec (count (:data heap))))))

        :otherwise
        (get-in heap [:data loc])))

(defn heap-set
  "Set the value of the heap vector at the given index.
Throws an exception if the index is out of the range.
this is part of the implementation, not for public consumption."
  [heap loc value]
  (cond (>= loc (count (:data heap)))
        (throw (Exception. (str "Get called with " loc " but last vector slot is " (dec (count (:data heap))))))

        :otherwise
        (assoc-in heap [:data loc] value)))

(defn heap-left
  "Return the left index."
  [loc]
  (inc (* loc 2)))

(defn heap-get-left [heap loc]
  "gets the left "
  (heap-get heap (heap-left loc)))

(defn heap-right
  "Return the right index."
  [loc]
  (+ 2 (* loc 2)))

(defn heap-get-right [heap loc]
  "gets the right"
  (heap-get heap (heap-right loc)))

(defn heap-parent
  "Return the parent index."
  [loc]
  (int (/ (dec loc) 2)))

(defn heap-get-parent [heap loc]
  "gets the parent"
  (heap-get heap (heap-parent loc)))

;; Now it's time for your code!  You need these three, but you are welcome to
;; write helper functions if you want (e.g., `percolate-down`.)  Do **not** write
;; `midje` tests for them, because they are not part of the spec.



(defn top
  "Return the top element of a heap.
If the heap has no elements, return `nil`."
  [heap]
  (heap-get heap 0)
  )

(defn percolate [heap loc]
 (if (< (dec (:size heap)) (or (heap-left loc) (heap-right loc))) heap  

 (let [x (compare (heap-get-left heap loc) (heap-get-right heap loc))]
    (cond (= x -1) 
          (if (nil? (heap-get-left heap loc))
            heap 
            (let [y (compare (heap-get heap loc) (heap-get-left heap loc))]
              (cond (= y -1) heap
                    (= y 0) heap
                    :else (let [temp (heap-get heap loc)]
                            (percolate   (heap-set (heap-set heap loc (heap-get-left heap loc)) (heap-left loc) temp) (heap-left loc))))))
          (= x 0)
          (if (nil? (heap-get-left heap loc))
            heap
             (let [y (compare (heap-get heap loc) (heap-get-left heap loc))]
              (cond (= y -1) heap
                    (= y 0) heap
                    :else (let [temp (heap-get heap loc)]
                            (percolate   (heap-set (heap-set heap loc (heap-get-left heap loc)) (heap-left loc) temp) (heap-left loc))))))
          :else (if (nil? (heap-get-right heap loc))
                  heap
                   (let [y (compare (heap-get heap loc) (heap-get-right heap loc))]
              (cond (= y -1) heap
                    (= y 0) heap
                    :else (let [temp (heap-get heap loc)]
                            (percolate   (heap-set (heap-set heap loc (heap-get-right heap loc)) (heap-right loc) temp) (heap-right loc))))))))))

(defn heap-full [heap]
  (if (nil? (heap-get heap (dec (:size heap)))) false true))



(defn heap-last [heap loc]
  "returns the next empty location"
  (if (heap-full heap) nil 
  (if (nil? (heap-get heap loc)) loc (heap-last heap (inc loc)))))

(defn heap-last-elt [heap loc]
  "returns the last element"
  (if (heap-full heap) (:size heap)
      (if (nil? (heap-get heap loc))  loc (heap-last-elt heap (inc loc)))))

(defn delete
  "Deletes the first element of the heap.
Returns the new heap."
  [heap]
  (if (nil? (heap-get heap 0)) 
    heap
    (let [last (dec (heap-last-elt heap 0))]
      (percolate (heap-set (heap-set heap 0 (heap-get heap last)) last nil) 0))))



(defn heap-double [heap doubled-heap loc]
  (if (nil? doubled-heap)
    (let [doubled-heap (make-heap (* 2 (:size heap)))]
      (heap-double heap doubled-heap loc)) 
    (if (== loc (:size heap)) doubled-heap (heap-double heap (heap-set doubled-heap loc (heap-get heap loc)) (inc loc)))))

(defn add-helper [heap loc] 
 (if (== (compare (heap-get heap loc) (heap-get-parent heap loc)) -1) 
   (let [temp (heap-get heap loc)]
     (add-helper (heap-set (heap-set heap loc (heap-get-parent heap loc)) (heap-parent loc) temp) (heap-parent loc)))
   heap))

(defn add
  "Adds a new element to the heap
If the data vector is too small, we resize it."
  [heap data]
  (if (heap-full heap) (add (heap-double heap nil 0) data)
      (add-helper (heap-set heap (heap-last heap 0) data) (heap-last heap 0)))
  )

