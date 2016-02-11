# Installation de l’application


## 1. Prérequis

La machine hébergeant le web service doit avoir : 

* Java 8 ou supérieur
* Un serveur Java applicatif : Glassfish 4.1.1 ou supérieur de préférence, ou TomCat
* Un accès au serveur Apache Jena Fuseki hébergeant la base de données sémantique dans le dataset `/ds`

Aucune version compilée n’est fournie dans le repository, il vous faudra donc NetBeans pour compiler l’application. 

## 2. Installation 

Une fois que vous aurez compilé l’application, connectez-vous à l’interface d’administration de votre serveur Glassfish ou Tomcat 
et déployez le fichier *.war* généré lors de la compilation.

Vérifier que vous avez accès à l’application et récupérer l’adresse du wsdl qui devrait être :

`<adresse_de_votre_serveur>:<port_du_serveur>/AXIS_MoM_WS?wsdl`

Il vous faudra utiliser cette adresse lors de la configuration de l’application web. 

