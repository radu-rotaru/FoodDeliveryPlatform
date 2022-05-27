Proiect PAO - Rotaru Radu-Stefan, 243
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
ETAPA 1

Tema pe care am ales-o pentru proiect este platforma de food delivery.
Clasele create:
	- user - reprezinta utilizatorul default
	- PremiumUser - utlizator premium, care are un abonament si are anumite discount-uri
	- restaurant - reprezinta restaurantele de la care se poate comanda
	- Courier - reprezinta persoana care va livra comanda
	- Dishes - reprezinta preparatele care sunt prezente in meniurile restaurantelor
	- payment - metoda de plata default a unei comenzi, prin cash
	- CardPayment - metoda de plata a unei comenzi prin intermediul cardului
	- Subscription - reprezinta tipurile de abonamente disponibile
	- Order - reprezinta o comanda
	- services - clasa in care am implementat actiunile
	- Main - clasa in care am folosit actiunile

 Actiuni implementate:
	- addOrder() - adauga o comanda in lista de comenzi din servicii
	- addUser() - adauga un user in lista de comenzi din servicii
	- addCourier() - adauga un curier in lista de comenzi din servicii
	- addNewSubscription() - adauga un abonament in lista de comenzi din servicii
	- getLoyalUsers() - afiseaza toti utilizatorii care au dat mai mult de 5 comenzi
	- getBestMarketPlaces() - afiseaza orasul cu cei mai multi utilizatori, 
				  cel cu cele mai multe restaurante si
				  cel cu cele mai multe comenzi
	- fireLaziestCouriers() - elimina din lista cei 3 curieri cu cele mai putine comenzi realizate
	- getRestaurantsNearUser() - afiseaza restaurantele din orasul utilizatorului pe care il selectam
	- unsubscribeUser() - dezaboneaza un utilizator premium si il transforma in utilizator default
	- removeRestaurant() - elimina restaurantul pe care il selectam din lista

---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
ETAPA 2

Am structurat clasele in pachete.
Am creat o interfata pentru citire si patru clase care o implementeaza, pentru 4 clase de obiecte.
Am creat un serviciu pentru citire, care are o functie care primeste ca parametri un 'reader' pentru 
o anumita clasa si numele fisierului din care se citesc datele.

Am creat o interfata pentru scrierea in fisiere si un serviciu care implementeaza interfata. Functia de scriere
primeste ca parametri un obiect si numele unui fisier si scrie atributele obiectului intr-un fisier csv.

Am creat si serviciul de audit singleton, care are o functie ce primeste ca parametru un string ce reprezinta
actiunea si scrie in fisierul audit.csv numele actiunii si data la care a fost efectuata.