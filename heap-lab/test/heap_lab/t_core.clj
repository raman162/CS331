(ns heap-lab.t-core
  (:use midje.sweet)
  (:use [heap-lab.core]))

(facts "about numbers"
       (fact "one plus one is two."
             (+ 1 1)  =>  2)
       (fact "two plus one is three."
             (+ 2 1)  =>  3))




(let [heap (add (add (add (add (add (make-heap 5) 4) 1) 2) 7) 3) empty-heap (make-heap 10) h1 (add (make-heap 1) 3) h2 (add (add (make-heap 2) 3) 4)  h3 (add (add (add (make-heap 3) 4) 5) 7) big-heap (add (add (add (add (add (add (add (add (add (add (add (make-heap 11) 1)3)2)4)9)2)6)13)5)11) 0) ]
(facts "about make-heap"
  (fact "the size is empty when creating new heap"
    (:size (make-heap 10)) => 0)
  (fact "the actual length vector size of the data is the size input from user"
    (count (:data (make-heap 10))) => 10))
 
(facts "about top"
  (fact "it returns nil when the heap has no elements"
    (top (make-heap 10)) => nil)
  (fact "it returns the top most element" 
    (top heap) => 1
    (top big-heap) => 0
    (top h1) => 3
    (top h2) => 3
    (top h3) => 4)
  (fact "returns nil if the size is empty even if there is garbage data"
    (top (delete (add (make-heap 2) 1))) => nil))


(facts "about delete"

  (fact "returns same heap  when trying to delete from empty heap"
    (delete (make-heap 10)) => (make-heap 10))
  (fact "deletes an element and replaces the correct element"
    (top (delete heap)) => 2)
  (fact "deletes an element and the heap matches"
    (heap-equal (delete heap) 4 [2 3 4 7 nil]) => true
    (heap-equal (delete big-heap) 10 [1 3 2 4 9 2 6 13 5 11 nil]) => true)
  (fact "does not duplicate the last element"
    (heap-get (delete heap) 4) => nil)
  (fact "percolates down correctly"
    (heap-get (delete heap) 3) => 7)
  (fact "it can delte from a heap with only one element"
    (heap-equal (delete (add (make-heap 1) 1)) 0 [nil]) => true)
  (fact "it can delete from a heap with only two elements"
   (heap-equal (delete (add (add (make-heap 2) 4) 5) ) 1 [5 nil]) => true
   (heap-equal (delete h2) 1 [4 nil]) => true)
  (fact "it can delete from a heap with only three elements"
    (heap-equal (delete (add (add (add (make-heap 3) 4) 5) 2)) 2 [4 5 nil]) => true
    (heap-equal (delete h3) 2 [5 7 nil]) => true )
  (fact "deletes an element and decrements size"
    (:size (delete heap)) => 4
    (:size (delete h1)) => 0
    (:size (delete h2)) => 1
    (:size (delete h3)) => 2
    (:size (delete big-heap)) => 10)
  (fact "does not decrement size when there is an empty heap"
    (:size (delete (make-heap 5))) => 0
    (:size (delete empty-heap)) => 0)
  (fact "deletes with from a heap with only one element"
    (heap-equal (delete h1) 0 [nil]) => true)
)




(facts "about add"
  (fact "increases size when element is added to heap"
    (:size (add heap 13)) => 6
    (:size (add h1 2)) => 2
    (:size (add h2 2)) => 3
    (:size (add h3 2)) => 4
    (:size (add big-heap 2)) => 12
    (:size (add empty-heap 23)) => 1 )
  (fact "increases size if heap is full"
    (:size (add heap 13)) => 6)
  (fact "larger element added doesn't move"
    (heap-get (add heap 13) 5) => 13)
  (fact "smallest element goes to the top"
    (heap-get (add heap 0) 0) => 0)
  (fact "percolating up works when adding elements to heap"
    (heap-equal (add heap 6) 6 [1 3 2 7 4 6 nil nil nil nil]) => true
    (heap-equal (add big-heap 1) 12 [0 1 1 4 3 2 6 13 5 11 9 2 nil nil nil nil nil nil nil nil nil nil]) => true)))

