# -PA-project-Chess
(proiect realizat singur)

Este un joc de sah care se joaca local, intre doi oameni si foloseste o baza de date (oracle db + hibernate) pentru inregistrare / logare. 
La rulare, jucatorii sunt intampinati cu o interfata de logare: 

![interfata login, register](https://i.imgur.com/d4tgorZ.png)  

Dupa ce au fost validati doi jucatori, jocul poate incepe si interfata jocului este redata, care este formata din tabla de joc si un panou care afiseaza
scorul, culoarea care trebuie sa mute si piesele capturate de fiecare jucator:

![interfata principala joc](https://i.imgur.com/3tlHEtD.png)

Atunci cand un jucatorul apasa pe o piesa, mutarile valide ale acesteia sunt calculate si colorate cu albastru (daca un rege este in sah, patratul respectiv este colorat cu rosu):
(in cazul acesta, nebunul de culoare neagra este apasat si acesta poate muta doar pe o diagonala, intrucat daca ar muta pe cealalta regele sau ar ramane in sah 
din cauza nebunului de culoare alba). 

![exemplu afisare mutari posibile](https://i.imgur.com/OCtDc8t.png)

Atunci cand este sah-mat, patratul regelui este colorat cu negru, scorul este actualizat si jucatorilor le este oferita posibilitatea de a juca din nou prin apasarea unui buton, care reseteaza tabla si schimba culorile jucatorilor:

![exemplu sah-mat](https://i.imgur.com/EWoHKTt.png)

