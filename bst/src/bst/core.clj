(ns bst.core)

;; # Introduction
;;
;; In this lab you get to write a BST like the one we did in class, only
;; this time it is a dictionary structure and not a set.
;; As such, the "data" element from before will have a key and value instead.

(defrecord BST [root size])
(defrecord BNode [left key value right])

(defn make-node
  ([key value]  (make-node nil key value nil))
  ([left key value right] (BNode. left key value right))
  )

(defn make-tree []
  (BST. nil 0))

;; # Size
;;
;; A warmup function.

(defn size "Return the size of the tree."
  [t]
  (:size t))

;; # Add
;;
;; The nodes will be entered into the tree on the basis of their key.
;; If someone tries to add a key that is already there, we replace the value
;; with the new entry.
(declare find-helper)
(declare find)

(defn add-helper [node nu-key nu-val]
  (cond (nil? node) (make-node nu-key nu-val)
        (= nu-key (:key node)) (make-node (:left node) (:key node) nu-val (:right node))
        (pos? (compare nu-key (:key node))) (make-node (:left node) (:key node) (:value node) (add-helper (:right node) nu-key nu-val))
        :else (make-node (add-helper (:left node) nu-key nu-val) (:key node) (:value node) (:right node))))


(defn add "Add a key and value to the BST."
  [bst nu-key nu-val]
  (if (nil? (:root bst)) (BST. (make-node nu-key nu-val) 1) (BST. (add-helper (:root bst) nu-key nu-val) (if (nil? (find bst nu-key)) (+ 1 (:size bst)) (:size bst)))))

;; # Find
;;
;; We need two versions of find.  The first one takes a key and returns the
;; value.  The second takes a value and returns the key.  Note that the second
;; version of the function must search the entire tree!  If the search item is not
;; there, return nil.

(defn find-helper [node look-key]
  (cond (nil? node) nil
        (= look-key (:key node)) (:value node)
        (pos? (compare look-key (:key node))) (find-helper (:right node) look-key)
  :else (find-helper (:left node) look-key)))

(defn find "Look for a key and return the corresponding value."
  [bst look-key] (find-helper (:root bst) look-key))


(defn find-key-helper [node look-value]
  (cond (nil? node) nil
        (= look-value (:value node)) (:key node)
        :else (or (find-key-helper (:right node) look-value) (find-key-helper (:left node) look-value))))


(defn find-key "Look for a value and return the corresponding key."
  [bst look-value] (find-key-helper (:root bst) look-value))

;; # Delete
;;
;; Similiarly, we have two versions of delete.  Please use the predecessor node if
;; you need to delete a child with two elements.



(defn get-pred [node]
  (if (nil? (:right node)) node (get-pred (:right node))))



(defn delete-helper [node victim]
  (cond (nil? node) (make-tree)
        (= victim (:key node)) (cond (and (nil? (:left node)) (nil? (:right node))) nil
                                     (or (nil? (:left node)) (nil? (:right node))) (or (:left node) (:left node))
                                     :else (let [pred (get-pred (:left node))]
                                             (make-node (delete-helper (:left node) (:key pred))(:key pred) (:value pred) (:right node)))) 
        (pos? (compare victim (:key node))) (make-node (:left node) (:key node) (:value node) (delete-helper (:right node) victim))
        :else (make-node (delete-helper (:left node) victim) (:key node) (:value node) (:right node))))



(defn delete [bst victim]
  (if (nil? (find bst victim)) bst (BST. (delete-helper (:root bst) victim) (-> bst :size dec))))

(defn delete-value [bst victim]
  (let [fkey (find-key bst victim)] 
    (if (nil? fkey) bst (delete bst fkey))))



;; # Map Tree
;;
;; This function takes a tree t and maps a function f over it.
;; If your tree is ((x 3 x) 5 ((x 7 x) 6 x)), then (map-tree t inc)
;; will return ((x 4 x) 6 ((x 8 x) 7 x))

(defn map-tree-helper [node  f] 
  (if (nil? node) nil (make-node (map-tree-helper (:left node) f) (:key node) (f (:value node)) (map-tree-helper (:right node) f))))

(defn map-tree
  [t f] (BST. (map-tree-helper (:root t) f) (:size t)))



