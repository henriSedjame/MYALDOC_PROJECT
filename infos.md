## INFOS UTILES ##

_**SECURITE :**_
######
Le socle de la sécurité de l'application s'articule autour de trois modules: 
* _**Reactive Security Core**_ :  
Ce module contient les couches MODEL et DAO pour le stockages des identifiants de connexion. Ce module contient aussi la logique de génération du Json Web Token (JWT)

* _**Reactive-Security-Auth-Server-Configuration**_ :  
Ce module contient la configuration pour la sécurité du server d'authorization. Il offre une annotation : _@EnableReactiveAuthServerSecurity_ qui permet d'importer toute la configuration.

* _**Reactive-Security-Resource-Server-Configuration**_ :   
Ce module la configuration pour la sécurité pour les différents microServices. Il offre une annotation : _@EnableReactiveResourceServerSecurity_ qui permet d'importer toute la configuration.

##

_**PROXY**_




##



     