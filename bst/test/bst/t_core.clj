(ns bst.t-core
  (:use midje.sweet)
  (:use [bst.core])
  (:import [bst.core BST] ))

(facts "about this lab"
  

  (facts "about make - tree"
    (fact "makes an empty tree"
      (:root (make-tree)) => nil)
    (fact "size is zero"
      (:size (make-tree)) => 0))
  

  (facts "about make-node"
    ( fact "right and left are nil when given key and value"
      (and (:right (make-node 10 20)) (:left (make-node 10 20))) => true))

  (def tree (add (add (add (add (add (add (make-tree) 10 30) 20 40) 5 15) 30 60) 1 13) 25 13))

  (facts "about size" 
    (fact "returns the correct size of the binary tree"
      (:size (make-tree)) => 0
      (:size tree) => 6))

  (facts "about add"
    (fact "increments size when adding a new key to the tree"
      (:size (add tree 3 7)) => 7)
    (fact "does not increment when adding with existing key"
      (:size (add tree 20 15)) => 6)
    (fact "puts value in the correct place"
      (= tree (add (delete tree 1) 1 13)) => true))
  
  (facts "about find"
    (fact "returns nil if key does not exist"
      (find tree 45) => nil)
    (fact "returns correct value"
      (find tree 25) => 13))

  (facts "about find-key"
    (fact "returns nil when value does not exist"
      (find-key tree 45) => nil)
    (fact "returns correct key"
      (find-key tree 15) => 5)
    (fact "returns right most key when finding multiple"
      (find-key tree 13) => 25))

  (facts "about delete"
    (fact "returns bst tree when key does not exist"
      (= tree (delete tree 100)) => true)
    (fact "deletes key and decrements size"
      (:size (delete tree 5)) => 5)
    (fact "replaces correct predecessor when deleting child with two children"
      (:key (:root (delete tree 10))) => 5)
    (fact "replaces only child when deleting tree with only one child"
      (:key (:root   (delete (add (add (make-tree) 10 20) 5 15) 10))) => 5
      (:key (:root   (delete (add (add (make-tree) 10 20) 15 15) 10))) => 15)
    (fact "removes predecessor after deleting"
      (:key (:left (:root (delete tree 10)))) => 1))

  (facts "about delete-value"
    (fact "returns bst tree when value does not exist"
      (= tree (delete-value tree 100)) => true)
    (fact "deletes key and decrements size"
      (:size (delete-value tree 15)) => 5)
    (fact "replaces correct predecessor when deleting child with two children"
      (:key (:root (delete-value tree 30))) => 5)
    (fact "replaces only child when deleting tree with only one child"
      (:key (:root   (delete-value (add (add (make-tree) 10 20) 5 15) 20))) => 5) 
    (fact "removes predecessor after deleting"
      (:key (:left (:root (delete-value tree 30)))) => 1)
    (fact "deletes right most key when multiple values exist"
      (:key (:left (:left (:root (delete-value tree 13))))) => 1))
  
  (facts "about map-tree"
    (fact "applies function to all values in tree"
      (:value (:root (map-tree tree inc))) => 31
      (= (map-tree tree inc) (add (add (add (add (add (add (make-tree) 10 31) 20 41) 5 16) 30 61) 1 14) 25 14)) => true)))
  
