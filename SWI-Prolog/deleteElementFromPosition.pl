/*problema 4 punctul b.
 CERINTA: Sa se elimine elementul de pe pozitia a n-a a unei liste
 liniare.
 modelul matematic:
 eliminare_pozitie(l1,l2...ln,pozitie):
	[] , daca lista=[]
	lista , daca pozitie>length(lista) sau pozitie<1
	l1+eliminare_pozitie(l2,l3...ln,pozitie-1) , daca pozitie!=1
	eliminare_pozitie(l1,l2,..ln,pozitie) , altfel
        (i,i,o)
        */
 printlist([]).
 printlist([Head|List]):-
    printlist(List),
    write(Head),
    write(" ").

 eliminare_pozitie([],_Poz,[]).
 eliminare_pozitie([H|T],Poz,[H|Rez]):-
                           P is Poz-1,
                           Poz\=1,
                           eliminare_pozitie(T,P,Rez).

 eliminare_pozitie([_H|T],1,Rez):-eliminare_pozitie(T,0,Rez).

/*problema 4 punctul a.
 Model matematic:
 l-lista in care caut
 y-lista cu care substitui
 element- elementul pe care il caut sa il substitui
 Rez-lista rezultata
substitutie_element_altaLista(l1,l2,...ln, element, y1,y2..ym):
	[] , daca lisya=[]
	(y1,y2...ym)+(substituie_element_altaLista(l2,l3..ln, element, y1,y2...ym), daca l1==element
	(substituie_element_altaLista(l1,l2..ln, element, y1,y2...ym), altfel
         model flux: (i,i,i,o).
 */

subst_elem_list([],_E,_Y,[]).

subst_elem_list([H|T],E,Y,[Y|Rez]):-
                               H=E,
                               subst_elem_list(T,E,Y,Rez).

subst_elem_list([H|T],E,Y,[H|Rez]):-
                               H\=E,
                               subst_elem_list(T,E,Y,Rez).

afisare([]).
