%functie auziliara de afisare a listelor
printlist([]).
printlist([Head|List]):-
    printlist(List),
    write(Head),
    write(" ").
/*
  Problema 2
  a) Sa se sorteze o lista cu pastrarea dublurilor. De ex: [4 2 6 2 3 4] => [2
  2 3 4 4 6]
  Model flux my_sort(i,O).
 */

  mysort(L,Rez):-sort(0,@=<,L,Rez).

/*
  Problema 2
  b)Se da o lista eterogena, formata din numere intregi si liste de numere. Sa
  se sorteze fiecare sublista cu pastrarea dublurilor. De ex:
  [1, 2, [4, 1, 4], 3, 6, [7, 10, 1, 3, 9], 5, [1, 1, 1], 7] =>
  [1, 2, [1, 4, 4], 3, 6, [1, 3, 7, 9, 10], 5, [1, 1, 1], 7].
  L-lista care se da
  R-lista care rezulta
  Model matematic:
  sort_subliste(l1l2...ln,r1r2...rn)=
  R=[], daca L=[]
  l1+sort_subliste(l2l3...ln), daca l1 nu este lista,
  mySort(l1)+R, daca l1 este lista.

 */
  sort_subliste([],R):-printlist(R).
  sort_subliste([H|T],R):-
                    is_list(H),
                    mysort(H,Rez),
                    !,
                    sort_subliste(T,[Rez|R]).
  sort_subliste([H|T],R):- sort_subliste(T,[H|R]).

