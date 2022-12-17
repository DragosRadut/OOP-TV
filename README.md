# POO TV

## Descriere
Proiectul presupune implementarea unui backend simplu al unei platforme specifice vizualizarii de filme sub forma unei ierarhii de fisiere.

## Structura
Structura proiectului este una similara cu un command design pattern.
Un obiect de tip Interpreter are rolul de intermediar intre input, actiunea propriu-zisa si output-ul generat.
Acest obiect va determina tipul paginii care trebuie folosite conform paginii curente pe care se afla utilizatorul.
Fiecare pagina implementeaza interfata Page care contine 2 metode:
* changePange:
** primeste ca parametru pagina pe care user-ul doreste sa inainteze; intoarce
