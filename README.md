cantine
=======
Gestion d'inscription au repas cantine avec interface web pour générer le mail d'inscription

L'appli est en srping boot
Lancer le Application.java en run as ou debug as Java application.

Le client est en jquery est consomme du JSON.

Le mail de la cantine doit être enregistrée au format .eml dasn le user.home

les images des plats doivent être placés dans ~/img et avoir comme nom [nom du plat].jpg

Lancer l'appli:  nohup java -Xms64m  -jar cantine-0.0.1-SNAPSHOT.jar --server.port=9292 &

ouvrir:
http://localhost:9292/iLikeCirso.html

pour changer son mdp il faut se mettre en point d'arret et chopper le nouveau mp encodé par spring security et le mettre dans import.sql


