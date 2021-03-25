/*
 Problema 8: Un jucator la PRONOSPORT vrea sa aleaga pronosticuri pentru 4 meciuri.
Pronosticurile pot fi 1,X,2. Sa se genereze toate variantele posibile stiind ca:
- ultimul pronostic nu poate fi 2
- sa nu fie mai mult de doua pronosticuri X.
 */
 /*
  Model matematic:
  l1l2l3-lista de 3 valori posibile (1,x,2)
  c1c2c3c4-container colector, care rebuie sa aiba 4 elemente
  solutie(L1L2L3,C):
         1. solutie(L1L3,candidat(L1L2L3)+C) , daca nr_de_xuri(C,elementul_x)=2
         2. solutie(L1L2L3,candidat(L1L2L3)+C), daca candidat(L1L2L3)!=2 && C=[]
         3. C daca am ajuns sa am 4 elemente
   model flux(i,o) - nedeterminist.
 */

 /*
  Model matematic nr_de_xuri:
      C-lista in care caut
      Y-valoarea pe care o caut
      Contor-de cate ori am gasit val. pe care o caut in lista

   nr_de_xuri(C1C2..Cn,Y,Contor):
       Contor, daca C=[]
       nr_de_xuri(C2..Cn,Y,Contor+1), daca C1=Y
       nr_de_xuri(C2..Cn,Y,Contor), daca C1 diferit de Y

      model flux(i,i,O)
 */
nr_de_xuri([],_,_C).
nr_de_xuri([H|T],Y,Contor):-
                      H=Y,
                      nr_de_xuri(T,Y,Cnou),
                      Cnou is Contor+1.


nr_de_xuri([H|T],Y,Contor):-
                      H\=Y,
                      nr_de_xuri(T,Y,Contor).

candidat(E,[E|_]).
candidat(E,[_|T]) :-
     candidat(E,T).

solutie(_,[_,_,_,_]).
solutie([L1,L2,L3],[]):-
                      candidat(E,[L1,L2,L3]),
                      E \= 2,
                      solutie([L1,L2,L3],[E|_]).
solutie([L1,L2,L3],C):-
                      nr_de_xuri(C,L2,Contor),
                      Contor=2,
                      candidat(E,[L1,L3]),
                      solutie([L1,L3],[E|C]).

toateSol([L1,L2,L3],C,TC):-
              findall(C,solutie([L1,L2,L3],C),TC).
