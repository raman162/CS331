(ns deque.t-core
  (:use midje.sweet)
  (:use [deque.core])
  (:import [deque.core Deque] ))

(facts "about this lab"
  (fact "the student never started it."
        (+ 1 2)  => 3)



  (facts "about make-deque"
    (fact "makes an empty deque."
      (:size (make-deque)) => 0)
    (fact "front is empty."
      (empty? (:front (make-deque))) => true)
    (fact "back is empty."
      (empty? (:back (make-deque))) )
    )

  (facts "about deque-size"
    (fact "returns correct size"
     (:size (push-front (make-deque) 12)) => 1 ))

  (facts "about push-front"
    (fact "adds element to the front."
      (first (:front (push-front (make-deque) 14))) => 14)
    (fact "increments size by 1 when pushing element"
     (:size (push-front (make-deque) 12)) => 1  )
    )

  (facts "about push-back"
    (fact "adds element to the back"
      (first (:back (push-back (make-deque) 14))) => 14 )
    (fact "increments size by 1 pushing element"
      (:size (push-back (make-deque) 12)) => 1 ))

  (facts "about flip-front"
    (fact "the back is empty"
     (empty? (:back (flip-front (push-back (make-deque) 12))))  => true)
    (fact "the back is at the end of the front"
     (rest (:front (flip-front (push-back (push-front (make-deque) 11) 12)))) => '(12))
    (fact "the front element does not change."
     (first (:front (flip-front (push-back (push-front (make-deque) 11) 12)))) => 11))
  

  (facts "about flip-back"
    (fact "the front is empty"
     (empty? (:front (flip-back (push-front (make-deque) 12))))  => true)
    (fact "the front is at the end of the back"
      (rest (:back (flip-back (push-back (push-front (make-deque) 11) 12)))) => '(11) )
    (fact "the back element does not change."
      (first (:back (flip-back (push-back (push-front (make-deque) 11) 12)))) => 12 ))

  (facts "about front"
    (fact "provides the front element"
      (front (push-front (push-front (make-deque) 11) 12)) => 12)
    (fact "flips the list if needed"
      (front (push-back (push-back (make-deque) 11) 12)) => 11 )
    )

  (facts "about back"
    (fact "provides the back element"
       (back (push-back (push-back (make-deque) 11) 12)) => 12)
    (fact "flips the list if needed"
       (back (push-front (push-front (make-deque) 11) 12)) => 11)
    )

  (facts "about pop-front"
    (fact "size remains 0 if trying to pop from empty deque"
      (:size (pop-front (make-deque))) => 0)
    (fact "the most front element is removed"
      (first (:front (pop-front  (push-front (push-front (make-deque) 11) 12) ))) => 11 )
    (fact "flips the back if the front is empty"
      (first (:front (pop-front  (push-back (push-back (make-deque) 11) 12))) ) => 12)
    (fact "decrements the correct size"
      (:size (pop-front  (push-back (push-back (make-deque) 11) 12))) => 1 )
    )

  (facts "about pop-back"
    (fact "size remains 0 if trying to pop from empty deque"
      (:size (pop-back (make-deque))) => 0)
    (fact "the most back element is removed"
      (first (:back (pop-back  (push-back (push-back (make-deque) 11) 12))) ) => 11 )
    (fact "flips the front if the back is empty"
      (first (:back (pop-back  (push-front (push-front (make-deque) 11) 12) ))) => 12)
    (fact "decrements the correct size"
      (:size (pop-back  (push-back (push-back (make-deque) 11) 12))) => 1 ))


 

  


)
