grows([_]).
grows([Xh, Yh|Yt]) :- Xh < Yh, grows([Yh|Yt]).

removed([], []).
removed([Xh | Xt], R) :- member(Xh, Xt), removed(Xt, R).
removed([Xh | Xt], [Xh|R]) :- not(member(Xh, Xt)), removed(Xt, R).

set_intersection(_,[],[]).
set_intersection([],_,[]).
set_intersection([Xh|Xt], Y, R) :- not(member(Xh, Y)), set_intersection(Xt, Y, R).
set_intersection([Xh|Xt], Y,[Xh|R]) :- member(Xh, Y), set_intersection(Xt, Y, R).

running([], []).
running([Xh|Xt], [Xh|R]) :- running(Xt, Xh, R).
running([], _, []).            
running([Xh|Xt], Sum, [CurSum|R]) :- CurSum is (Xh + Sum), running(Xt, CurSum, R).
    
list_merge(X, [], X).
list_merge([], Y, Y).
list_merge([Xh|Xt], [Yh|Yt], R) :- Xh =< Yh, list_merge(Xt, [Yh|Yt], Z), R = [Xh|Z].
list_merge([Xh|Xt], [Yh|Yt], R) :- list_merge(Yt, [Xh|Xt], Z), R = [Yh|Z].

dependsOn( lib0, []).
dependsOn( lib1, []).
dependsOn( lib2, []).
dependsOn( lib3, []).
dependsOn( lib4, [lib3] ).
dependsOn( lib5, [lib0, lib2] ).
dependsOn( lib6, [lib5, lib3] ).
dependsOn( lib7, [lib1, lib3, lib4 ] ).
dependsOn( lib8, [lib3, lib5] ).

imports([],[]).
imports(X, R) :- dependsOn(X, Y), imports(Y, Z), removed(Z, R).
imports([Xh|Xt], R) :- imports(Xh, Y), imports(Xt, Z), append(Y, [Xh|Z], R).
