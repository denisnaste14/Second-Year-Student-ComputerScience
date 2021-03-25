;Problema 9
;a)  Sa se scrie o functie care intoarce diferenta a doua multimi.
;am nevoie de o functie care sa imi zica daca o valoare exista sau nu intr-o lista 
;model matematic functie valoareExistenta
;L-lista in care caut, E - elementul pe care il caut
;valoareExistenta(L1L2..Ln,E)= -9999,  daca lista este goala
;                              l1, daca l1=E
;                              valoareExistenta(L2...Ln,E), altfel 

(defun valoareExistenta(l e)
    (cond 
            ((null l) -99999)
            ((=(car l) e) e)
            (t(valoareExistenta (cdr l) e))
    )
) 

;diferentaMultimi(l1l2..ln,r1r2...rn) = (), daca l=[] si r=[]
;                                     = l1+ diferentaMultimi(l2...ln,r), daca r=[]
;                                     = r1+ diferentaMultimi(l,r2...rn), daca l=[]
;                                     = l1+ diferentaMultimi(l2..ln,r), daca valoareExistenta(r,l1)=-99999
;                                     = r1+ diferentaMultimi(l,r2..rn), daca valoareExistenta(l,r1)=-99999
;                                     = diferentaMultimi(l2...ln,r2...rn), altfel

(defun diferentaMultimi(l r)
    (cond
            ((and (null l) (null r)) '())
            ((null l) (append (list (car r)) (diferentaMultimi l (cdr r))))
            ((null r) (append (list (car l)) (diferentaMultimi (cdr l) r)))
            ((= (valoareExistenta r (car l)) -99999) (append (list (car l)) (diferentaMultimi (cdr l) r)))
            ((= (valoareExistenta l (car r)) -99999) (append (list (car r)) (diferentaMultimi l (cdr r))))
            (T(diferentaMultimi (cdr l) (cdr r)))     
    )
)
;b)Definiti o functie care inverseaza o lista impreuna cu toate sublistele sale de pe orice nivel.
;L- o lista data 
;inversa(L1L2...Ln) = [], daca n=0
;                   = inversa(L2...Ln)+L1 , altfel
(defun inversa (l)
    (cond
           ((null l) '())
           (T (append (inversa (cdr l)) (list (car l))))
    )
)

;inversaGeneral(L1L2...Ln) = [], daca n=0
;                          = inversaGeneral(L2...Ln)+inversa(L1), daca L1=lista
;                          = inversaGeneral(L2...Ln)+L1, altfel

(defun inversaGeneral (l)
    (cond
           ((null l) '())
           ((listp (car l)) (append (inversaGeneral (cdr l)) (list (inversaGeneral (car l)))))
           (T (append (inversaGeneral (cdr l)) (list (car l))))
    )
)

;c)Dandu-se o lista, sa se construiasca lista primelor elemente ale tuturor elementelor lista ce au un numar impar de elemente la nivel superficial.
;Exemplu: (1 2 (3 (4 5) (6 7)) 8 (9 10 11)) => (1 3 9).
;l-lista data
;nrElemLista(l1l2...ln)= 0 daca l=[]
;                        1+nrElemLista, altfel
(defun nrElemLista (l)
    (cond
            ((null l) 0)
            (T (+ 1 (nrElemLista (cdr l))))
    )
)

;listaPrimelorElem(l1l2...ln) = () , daca l =[]
;                               listaPrimelorElem(l1), daca l1=lista
;                               L1+listaPrimelorElem(l2..ln), nrElemLista(l)=nrImpar si !l1=lista
;                               listaPrimelorElem(l2..ln), altfel     
(defun listaPrimelorElem (l)
    (cond
            ((null l) '())
            ((and (not(listp (car l))) (oddp (nrElemLista l))) (append (list (car l)) (listaPrimelorElem (cdr l))))
            ((listp (car l)) (listaPrimelorElem (car l)))
            (T(listaPrimelorElem (cdr l)))
    )
)

; Sa se construiasca o functie care intoarce suma atomilor numerici dintr-o lista, de la nivelul superficial. 
;suma(l1l2...ln) = 0, daca l=[]
;                  l1+suma(l2...ln), daca l1=numar
;                  suma(l2...ln), daca l1 nu e numar
(defun suma (l)
    (cond
            ((null l) 0)
            ((numberp (car l))(+ (car l) (suma(cdr l))))
            (T (suma (cdr l)))
    )
)