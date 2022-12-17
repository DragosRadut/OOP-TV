# POO TV

## Descriere
Proiectul presupune implementarea unui backend simplu al unei platforme specifice vizualizarii de filme sub forma unei ierarhii de fisiere.

## Structura
Structura proiectului este una similara cu un command design pattern.
### Pachete specifice
Clasele sunt impartite in doua pachete:
* input.data -> contine clasele specifice datelor primite ca input
* page.data -> contine clasele prin care se suprascriu metodele specifice fiecarei pagini
### Pagini
Fiecare pagina implementeaza (folosind Singleton design patter) interfata Page care contine 2 metode:
* changePange -> primeste ca parametru pagina pe care user-ul doreste sa inainteze si intoarce un String ("err" = eroare, "page name" = succes);
* action -> handler-ul pentru actiunile de tip "on page"; va intoarce un obiect PageResponse care contine datele ce vor fi modificate;
### Interpreter
Un obiect de tip Interpreter are rolul de intermediar intre input, actiunea propriu-zisa si output-ul generat.
Acest obiect va determina tipul paginii care trebuie folosite conform paginii curente pe care se afla utilizatorul.
Odata determinat tipul paginii, va apela una dintre cele doua metode specifice: changePage/action.
#### changePage
