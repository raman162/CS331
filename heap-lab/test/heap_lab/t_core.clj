(ns heap-lab.t-core
  (:use midje.sweet)
  (:use [heap-lab.core]))

(facts "about numbers"
       (fact "one plus one is two."
             (+ 1 1)  =>  2)
       (fact "two plus one is three."
             (+ 2 1)  =>  3))




(let [heap (add (add (add (add (add (make-heap 5) 4) 1) 2) 7) 3)]

 
(facts "about top"
  (fact "it returns nil when the heap has no elements"
    (top (make-heap 10)) => nil)
  (fact "it returns the top most element" 
    (top heap) => 1)
)


(facts "about delete"

  (fact "returns same heap  when trying to delete from empty heap"
    (delete (make-heap 10)) => (make-heap 10))
  (fact "deletes an element and replaces the correct element"
    (top (delete heap)) => 2)
  (fact "does not duplicate the last element"
    (heap-get (delete heap) 4) => nil)
  (fact "percolates down correctly"
    (heap-get (delete heap) 3) => 7))

(facts "about add"

  (fact "doubles the heap if the heap is full"
    (:size (add heap 13)) => 6)
  (fact "larger element added doesn't move"
    (heap-get (add heap 13) 5) => 13)
  (fact "smallest element goes to the top"
    (heap-get (add heap 0) 0) => 0)))
