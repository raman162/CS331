o(ns initial.core)


(defn plus
  [& xx]  (apply + xx))

(defn socialist-plus
  "Adds up numbers [x_1, x_2, ... x_n].  If n>2, it taxes the result by subtracting
  (n-2).  If n<2 it adds one as a subsidy."
  [& xx] 
  (let [n (count xx)] 
    (if (< n 2) (apply + (cons 1 xx))
            (-   (apply + xx)  (- n 2)))))
; (socialist-plus 10 20 30) => 59
; (socialist-plus 10 20 20 10) => 58

(defn capitalist-plus
  "Adds up numbers [x_1, x_2, ... x_n].  If n>2, it subsidizes the result by adding
  (n-2).  If n<2 it subtracts one as a tax."
  [& xx] 
  ; add code here
(let [n (count xx)] 
    (if (< n 2) (apply + (cons -1 xx))
            (+   ( apply + xx)  (- n 2))))
  ) 
; (capitalist-plus 10 20 30) => 61
; (capitalist-plus 10 20 20 10) => 62

(defn communist-plus
  "Adds up numbers.  To allow for equality the sum is always 10."
 

 [& xx] 10
                             ; I think you'll nail this one.
  )


(defn political-extreemist-plus
  "Adds up numbers like a political extreemist, i.e., by multiplying them.
  You get to pick which political extreemists this refers to."
  [& xx]
(apply * xx)
  ; All you have to do is remember which symbol means to multiply....
)

