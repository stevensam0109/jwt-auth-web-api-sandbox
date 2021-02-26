# Secure Web REST API (Products Management)    

![Build Status](https://img.shields.io/badge/build-passing-brightgreen.svg?branch=develop)
![Gitlab code coverage](https://img.shields.io/gitlab/coverage/oviok-group/jwt-auth-web-api-sandbox/jwt-auth-web-api-back-end) 

# A Propos
**`My Products`** est une application Web sécurisée de gestion de produits avec leur catégorie (**`service Web RESTFul Sécurisé`**) écrit en **Java** et embarque **Spring** 
avec d'autres technologies pour :
- non seulement faciliter l'intégration des différents composants applicatifs (traduire en lignes de codes l'expression des besoins : `gérer les produits`, `gérer les catégories de produits`, ...), 
- mais également assurer la gesion de la `sécurité applicative` (sécuriser le SI donc les ressources et les échanges).

Il comporte principalement deux modules :
- un **Back-End Java** , basée sur une architecture **`REST`** et embarquant :
	- _les exigences fonctionnelles ou métier_, 
	- _les exigences non fonctionnelles_. 
- un **Front-End Angular** (`Client Web`) fournissant les interfaces utilisateur pour consommer les fonctionnalités exposées par le Back-End Java (le serveur).

**NB** :
- _Les données/informations sont stockées dans une base de données relationnelles_.
- Voir la section **`Exigences`** pour plus de détails sur les fonctionnalités embarquées.
- Voir la section **`Stack Technique`** pour plus de détails sur l'ensemble des technos utilisées pour le développment de l'application.
- **SI** : **S**ystème d'**I**nformations.	

	
# Spécifications 
Dans cette section, quelques éléments sont fournis pour faciliter la compréhension du besoin et les réalisations techniques à venir.
Les élements des processus de gestion : **`autorisation, authentication et sécurité applicative`**, seront mis en place à partir des spécifications
**JWT** et **Spring Security**. 
Les échanges se feront principalement entre le client (`front-end`) et le serveur (`back-end Java`). Les éléments ci-dessous sont fournis dans le cadre cette spécification :
- Une brève présentation de **`JWT`** 
- Le diagramme d'architecture applicative et technique
- Les diagrammes de séquences du fonctionnement global de quelques cas d'utilisation :
	- _Ajouter un nouvel utilisateur avec ses rôles dans le système d'information_,
	- _Gérer la production/fourniture de jetons d'accès lorsque les utilisateurs se connectent à l'application_,	
	- _Accès aux resources (protégées) de l'application : fournir le jeton d'accès dans l'en-tête de la requête lors de la demande d'informations_.
- Les schéma et modèle de données pour la gestion des informations **métier** de l'application.


## Brève Présentation JWT  
**`JWT`** (**J**SON **W**eb **T**oken), est une spécification pour la représentation des revendications (_claims_) à transférer entre deux parties. Les revendications sont codées en tant qu'objet `JSON` utilisé comme charge
 utile d'une structure chiffrée, permettant aux revendications d'être signées ou chiffrées numériquement. La structure peut être :
- **J**SON **W**eb **S**ignature (`JWS`) ou 
- **J**SON **W**eb **E**ncryption (`JWE`).

Les **`JWT`** contiennent les informations nécéssaires pour aider au stockage de la **`session utilisateur`**, etc. **`JWT`** peut être aussi choisi comme format pour les jetons d'accès et d'actualisation
utilisés dans le protocole **`OAuth2`**.

## Les Exigences 

### Les exigences fonctionnelles 
Le tableau ci-dessous fournit une liste non exhaustive des exigences fonctionnelles qui seront embarquées par le SI.
|Processus|Fonctionnalités|
|---|---|
|**Gestion des utilisateurs**|_<ul><li>Se Connecter/Déconnecter de l'application</li><li>Ajouter de nouveaux utilisateurs dans le SI avec leurs rôles</li><li>Mettre à jour les informations d'un utilisateur existant</li><li>Supprimer les informations de l'utilisateur du SI</li><li>Rechercher les informations d'un utilisateur dans le SI selon son identifiant</li><li>Obtenir la liste des utilisateurs du système</li></ul>_| 
|**Gestion des produits**|_<ul><li>Ajouter les informations de nouveaux produits dans le SI</li><li>Mettre à jour les informations d'un produit existant dans le SI</li><li>Supprimer les informations d'un produit du SI</li><li>Rechercher les informations d'un produit dans le SI selon son identifiant</li><li>Obtenir la liste des produits du système</li><li>Obtenir la liste filtrée de produits dont le nom `match` avec le `pattern` fourni</li></ul>_|
|**Gestion des catégories de produits**|_<ul><li>Ajouter de nouvelles catégories de produits dans le SI</li><li>Mettre à jour les informations d'une catégorie de produits dans le SI</li><li>Supprimer les informations d'une catégorie de produits du SI</li><li>Rechercher les informations d'une catégorie de produits dans le SI</li><li>Obtenir la liste des catégories de produits du SI</li><li>Obtenir la liste filtrée de catégories de produits dont le nom de produit `match` avec le `pattern` fourni</li></ul>_|
|**Gestion de la Sécurité dans l'application**|_<ul><li>`Authentification` : permet de confirmer ou valider l'identité du client/l’utilisateur qui tente d’accéder au système d'informations</li><li>`Autorisation` (protection des ressources) : permet d’octroyer au client/l’utilisateur l’accès au système d’informations, donc aux ressources de l'application</li></ul>_|

### Les exigences non fonctionnelles 
Les exigences non fonctionnelles sont plutôt d'ordre technique, mais elles participent à l'implémentation des besoins exprimés et au bon focntionnement de l'application. 
Le tableau ci-dessous dresse une liste non exhaustive des exigences non fonctionnelles de l'application.
|Type Exigence|Fonctionnalités|
|---|---|
|**Exigences non fonctionnelles**|_<ul><li>Gérer les logs</li><li>Gérer les erreurs/exceptions</li><li>Gérer les accès à la base de données</li><li>Gérer la migration des scripts de base de données (création de schémas, insertion, mise à jour de tables ou de données ...) avec **`Flyway`**</li></ul>_|

### Le Client (Front-end)
L'interface utilisateur permet selon ses rôles définis dans l'application (les droits de l'utilisateurs) :
- **`Gestion des utilisateurs`** :
	- _Se Connecter/Déconnecter de l'application_,
	- _Ajouter/Inscrire un nouvel utilisateur dans le SI_,
	- _Visualiser les informations des utilisateurs_, 
	- _Modifier les informations d'un utilisateur_,
	- Supprimer les informations d'un utilisateur.
- **`Gestion des catégories de produits`** :
	- _Ajouter les informations d'une nouvelle catégorie de produits_,
	- _Ajouter les informations d'un produit à une catégorie de produits_,
	- _Mettre à jour les informations d'une catégorie de produits_,
	- _Réchercher les informations d'une catégorie de produits (soit par son identifiant, par son nom, ...)_,
	- _Supprimmer du SI les informations d'une catégorie de produits_.	
- **`Gestion des produits`** :
	- _Ajouter les informations d'un nouveau produit dans le SI_,
	- _Mettre à jour les informations d'un produit existant dans le SI_,
	- _Réchercher les informations d'un produit (soit par son identifiant, par son nom, ...)_,
	- _Supprimer du SI les inforamtions d'un produit_.

## Architecture Technique et Applicative Globale 
Le diagramme ci-dessous fournit une vision globale des flux d'échanges entre l'application et les acteurs du système et(ou) briques/composants applicatifs.
L'architecture technique et applicative comporte les éléments suivants :
- le `noeud` **`Back-End`** environnement d'exécution qui embarque les différents composants permettant d'implémenter toute la logique métier de l'application
- le `package` **`Front-End`** : interface utlisateur avec les différents composants permettant de consommer les services exposés par le `back-end Java`.
- le `noeud` **`Base de Données`** : environnement d'exécution qui spécifie les configurations de SGBDR fournies (acceptables) pour la persistance, le stockage des informations métiers de l'application.

Il est fourni par le diagramme ci-dessous (`PlantUML` au format markdown) :

```plantuml
@startuml
' Défintion des acteurs
actor User #red

' Défintion du serveur Front-End dans l'architecture (le noeud)
package "Client (Front-End Angular)" as Client  {
[Routeur]  .> [Components/Templates] : uses
[Components/Templates] <.> [Services] : exchange
[Services] <.> [HTTP Client] : exchange
}

' Défintion des couleurs des composants du serveur
[Routeur] #Pink
[Components/Templates] #fdad5c
[Services] #skyblue
[HTTP Client] #lightgreen

' Défintion du serveur Back-End dans l'architecture (le noeud)
node "Server (Back-End)" as Server  <<Execution Environnement>> {
[REST API Controller] ..> [Web Security Config] : uses
[Web Security Config] ..> [REST API Controller] : secure
[Web Security Config] ..> [Services Provider] : secure
[REST API Controller] ..> [Services Provider] : uses
[REST API Controller] ..> [Model/DTO] : uses
[Services Provider] ..> [Model/DTO] : uses
[DAO (Spring Data Jpa)] ..> [Model/DTO] : uses
[DAO (Spring Data Jpa)] ..> [ServerUtil] : uses
[Services Provider] ..> [ServerUtil] : uses
[REST API Controller] ..> [ServerUtil] : uses
[Services Provider] .> [DAO (Spring Data Jpa)] : uses
}

' Défintion des couleurs des composants du serveur
[REST API Controller] #Yellow
[Web Security Config] #fdad5c
[Services Provider] #Pink
[DAO (Spring Data Jpa)] #skyblue
[Model/DTO] #lightgreen
[ServerUtil] #teal

' Défintion de la base de données dans l'architecture
node "Base de Données" as BDD <<Execution Environnement>> {
database "H2" #Yellow
database "MariaDB" #Green
database "PostgreSQL" #Gray
}

' Ajout de commentaires
note left of Client
- Récupérer les informations du formulaire
- Valider les informations/les saisies
- Soumettre à nouveau le formulaire pour correction de saisies effectuées
- Construire la requête avec les informations du jeton JWT
end note

' Ajout de commentaires
note right of Server 
- Traiter la requête selon la demande 
- Générer le jeton JWT/ Construire la réponse à la demande
- Retourner le jeton JWT et informations d'authentification
end note

' Définition des interactions entres les composants de l'architecture
User --> Client : Effectuer une demande (S'inscrire,Se Connecter,..)
Client--> User : Afficher les détails (données, erreurs)
Client  --> [REST API Controller] :  Call API
[REST API Controller] --> Client   :  Retour Call API
[DAO (Spring Data Jpa)] <--> BDD : Rechercher - Sauvegarder - Mettre à jour données en base
@enduml
```
Du schéma d'architecture ci-dessus, on remarque que le `Client` (le Fornt-End) à la différence des autres composantes (qui sont des `noeuds`) de l'architecture est un `package`. 
Ce choix a été opéré dans le cadre de cette réalisation par soucis de simplification pour les raisons suivantes :
- pouvoir intégrer les éléments du `Client Angular` dans le `Back-End Java` pour une exécution dans un seul environnement fullstack (Client + Serveur). Ceci
consite donc à intégrer les ressources distribuées du `Client Angular` lors du packaging du `Back-End Java`.
- éviter une exécution séparée des deux composantes (éviter d'avoir à adresser des ports d'écoutes différents pour l'exécution)

**NB** : La configuration modulaire du projet permet néanmoins une exécution séparée. Il suffira tout juste d'inhiber la configuration actuelle permettant d'intégrer les ressources distribuées 
du `Client Angular` lors du packaging du `Back-End Java`.

## Fonctionnement global - Les USe Case
Le fonctionnement global de l'application est fourni aux travers de `diagrammes de séquences` des cas d'utilisation (`use case`) présentés dans le tableau ci-dessous :

|Use Case|Description succincte |
|---|---|
|**`Ajouter un nouvel utilisateur`**|_permet de persister/sauvegarder les informations d'un nouvel utilisateur dans le système d'informations_|
|**`S'Authentifier`** (_Se Connecter à l'application_)|_permet au client (utilisateur) de fournir les informations pour_ `authentification par le système d'informations`|
|**`Accéder aux ressources`** : _Cas affichier la liste de produits existant dans le SI_|_Il est composé principalement de deux phases : <ul><li>La demande et obtention des jetons d'accès (phase d'authentification : voir use case du dessus)</li><li>L'accès proprement dit aux ressources de l'application (produits, outils, services,... ) avec le jeton d'accès obtenu de la phase d'authentification</li></ul>_|

### Ajouter un nouvel utilisateur dans le SI
L'ajout ou la persistance des informations d'un nouvel utilisateur dans le système d'informations est présenté par le diagramme de séquences ci-dessous (`PlantUML` au format markdown) :

```plantuml
@startuml
' Déclaration des participants (acteurs)
actor User #Pink
participant Client #Yellow
participant Serveur #SkyBlue
database BDD #Gray

' Déclaration des enchainements des séquences des traitements
autonumber
User -[#black]> Client : Requête Ajout (demande enregistrement nouvel utilisateur)
activate User
Client -[#black]> Client : Charger la page de saisie  (saisir les données utilisateur)
Client -[#black]> User : Page de saisie (informations user)
deactivate Client
User -[#black]> Client : Saisie des informations  (username, paswword, email, roles)
activate Client
Client -[#black]> Client : Valider les informations du formulaire (check saisie)
autonumber stop
?<[#red]-- Client  : ""Erreur saisie (saisie non valide)""

autonumber 6
Client -[#black]> Serveur : Call API : POST /api/auth/register : (username, email, password, roles)
activate Serveur
Serveur -[#black]> Serveur : Récupérer informations requêtes (informations utilisateur)
Serveur -[#black]> BDD : Vérifier existance utilisateur (username,  email)
activate BDD 
BDD -[#black]> BDD : Recherche dans la table (T_USERS)
autonumber stop

BDD -[#red]> Serveur : SQL/LoginAlreadyUsedException/EmailAlreadyUsedException
deactivate BDD
Serveur -[#red]> Client : Construire/Remonter le message d'erreurs associé avec le code statut HTTP
deactivate Serveur
Client -[#red]> User : Remonter le message d'erreurs associé avec le code statut HTTP
deactivate Client
deactivate User

' Traitement alternatif lorsque l'utilisateur existe déjà en base de données
alt [Si utilisateur existe déjà]
autonumber 10
BDD -[#black]> Serveur : Retourner informations utilisateur (utilisateur existe déjà )
activate BDD
Serveur -[#black]> Client : Générer message existance (user informations)
activate Serveur
activate Client
Client -[#black]> User : Modifier la saisie (informations user)
activate User
deactivate Client
deactivate User
autonumber stop
end

' Traitements pour la persistance des informations de l'utilisateur dans la base de données et gestion des exceptions
autonumber 10
Serveur -[#black]> BDD : Persistance données (nouvel utilisateur)
BDD -[#black]> BDD : Enregistrer dans la table (T_USERS)
autonumber stop
BDD -[#red]> Serveur : SQL/DAOException
Serveur -[#red]> Client : Construire/Remonter le message d'erreurs associé avec le code statut HTTP
deactivate Serveur
activate Client
Client -[#red]> User : Construire/Remonter le message d'erreurs associé avec le code statut HTTP
activate User
deactivate Client
deactivate User

autonumber
BDD -[#black]> Serveur : Enregistrement OK (pas d'erreurs)
activate Serveur
Serveur -[#black]> Client : Enregistrement avec succès (message)
note left of Serveur : d'autres informations peuvent être ajoutées lors de la réalisation

activate Client 
Client -[#black]> User : Enregistrement avec succès (message)
activate User
deactivate Serveur
deactivate Client
deactivate User
deactivate BDD
@enduml
``` 

### S'Authentifier
Le fonctionnement global de la phase d'authentification du client (valider/confirmer les informations d'identification) par le système d'informations est présenté par le diagramme de séquences ci-dessous (`PlantUML` au format markdown) :

```plantuml
@startuml
' Déclaration des participants (acteurs)
actor User #Pink
participant Client #Yellow
participant Serveur #SkyBlue
database BDD #Gray

' Déclaration des enchainements des séquences des traitements
autonumber
User -[#black]> User : Démarrer l'application
activate User
User -[#black]> Client : Se Connecter  (demander la page connexion)
Client -[#black]> Client : Charger page de connexion(formulaire)
activate Client
Client -[#black]> User : Page connexion (saisie des informations d'identification)
deactivate Client
User -[#black]> Client : Sasir les informations d'authentification (username,password)
activate Client

' Validation des informations saisies et appel service
Client -[#black]> Client : Valider les informations du formulaire (check saisie)
Client -[#black]> Serveur : Call API : POST /api/auth/authenticate : (username, password)
activate Serveur
Serveur -[#black]> Serveur : Authentifier l'utilisateur qui tente de se connecter  (username, password)
Serveur -[#black]> BDD : Call BDD  findUserBy (username, email, roles, ..)
activate BDD

' Ajout de commentaires
note right of Serveur 
Le critère de recherche peut être l'un des éléments ci-dessous :  
username (login)
mail
username & roles
end note 

BDD -[#black]> BDD : Rechercher credentails dans la table (T_USERS)
autonumber stop
BDD -[#red]> Serveur : SQL/LoginAlreadyUsedException/EmailAlreadyUsedException
deactivate BDD
Serveur -[#red]> Client : Construire/Remonter le message d'erreurs associé avec le code statut HTTP
deactivate Serveur
Client -[#red]> User : Remonter le message d'erreurs associé avec le code statut HTTP
deactivate Client
deactivate User

' Traitement de la réponse en cas d'opération effectuée avec succès en base de données
autonumber 10
BDD -[#black]> Serveur : Réponse recherche OK (Pas d'Exception)
activate BDD
deactivate BDD
Serveur -[#black]> Serveur : Générer et Stocker le token JWT (créer avec paire de clés de type RSA)
activate Serveur

' Ajout de commentaires
note left of Serveur 
Construire l'authentification
Renseigner le contexte de sécurité de l'authentification
end note
Serveur -[#black]> Client : Retourner la réponse Authentification ( accesToken, tokenType, user info, authorities)

' Ajout de commentaires
note left of Client
{
"access_token":"e6631caa-bcf9-433c-8e54-3511fa55816d",
"token_type":"bearer",
"username":"user1",
"email": "user1@user.com",
"password": "user1123456789",
"roles":["user"]
}
end note

activate Client
deactivate Serveur
Client -[#black]> Client : Stocker le jeton JWT (interne)
Client -[#black]> User : Retourner la réponse Authentification ( accesToken, tokenType, user info, authorities)
activate User
deactivate Client
deactivate User
@enduml
```

### Accéder aux ressources de l'application - Cas : Afficher la liste des produits existant dans le SI
Ce cas d'utlisation comporte principalement les deux phases suivantes :
- La demande et obtention des jetons d'accès (phase d'authentification : voir use case du dessus)
- L'accès proprement dit aux ressources de l'application (produits, outils, services,... ) avec le jeton d'accès obtenu de la phase d'authentification.

Son fonctionnement global est fourni par le diagramme de séquences ci-dessous (`PlantUML` au format markdown) :

```plantuml
@startuml
' Déclaration des participants (acteurs)
actor User #Pink
participant Client #Yellow
participant Serveur #SkyBlue
database BDD #Gray

' Déclaration des enchainements des séquences des traitements du cas : Afficher la liste de produits esistant dans le SI
autonumber
User -[#black]> Client : Demander liste de produits 
activate User
activate Client
Client -[#black]> Serveur : Call API : GET /api/product/all : (sans en-têtes d'autorisation)
activate Serveur 
autonumber stop
Serveur -[#red]> Client : 401 Unauthorized (erreur authentification)
deactivate Serveur
Client -[#red]> User : Remonter le message d'erreurs associé avec le code statut HTTP
autonumber 3
Client -[#black]> Client : Rediriger ves la page d'authentification (connexion)
Client -[#black]> User : Page d'authentification (saisie)
deactivate Client

' Saisie des informations d'authentification et traitements
User -[#black]> Client : Sasir les informations d'authentification (username,password)
activate Client
deactivate User
Client -[#black]> Client : Valider la saisie (informations authentification)
Client -[#black]> Serveur : Call API : POST /api/auth/authenticate : (username, password)
activate Serveur
Serveur -[#black]> BDD : Valider les informations d'identification (utilisateur)
activate BDD
BDD -[#black]> BDD : Rechercher credentails dans la table (T_USERS)
autonumber stop
BDD -[#red]> Serveur : SQL/DAOException
deactivate BDD
Serveur -[#red]> Client : Construire/Remonter le message d'erreurs avec le code statut HTTP
deactivate Serveur
Client -[#red]> User : Remonter le message d'erreurs associé avec le code statut HTTP
activate User
deactivate Client
deactivate User

' Traitement de la réponse en cas d'opération effectuée avec succès en base de données
autonumber 10
BDD -[#black]> Serveur : Retourner les informations d'identification ( No Exception)
activate BDD
deactivate BDD
activate Serveur
Serveur -[#black]> Serveur : Générer et Stocker le token JWT (paire de clés RSA)
Serveur -[#black]> Client : Retourner la réponse Authentification ( accesToken, tokenType, ..)

' Ajout de commentaires
note left of Client
{
"access_token":"e6631caa-bcf9-433c-8e54-3511fa55816d",
"token_type":"bearer",
"username":"user1",
"email": "user1@user.com",
"password": "user1123456789",
"roles":["user"]
}
end note

activate Client
Client -[#black]> Client : Stocker les informations du jeton (JWT)
Client -[#black]> Serveur : Call API : GET /api/product/all : (Headers {Authorization : "bearer xx-yy-zz-rr-tt"})
Serveur -[#black]> Serveur : Vérifier/Valider la signature JWT (jeton)
deactivate Client
Serveur -[#black]> BDD : Obtenir les informations d'identification (utilisateur)
deactivate Serveur
activate BDD 
BDD -[#black]> BDD : Rechercher credentails dans la table (T_USERS)
autonumber stop
BDD -[#red]> Serveur : SQL/LoginException/EmailException
deactivate BDD
activate Serveur
Serveur -[#red]> Client : Construire/Remonter le message d'erreurs avec le code statut HTTP
deactivate Serveur
activate Client 
Client -[#red]> User : Remonter le message d'erreurs associé avec le code statut HTTP
activate User
deactivate User
deactivate Client
autonumber 18
BDD -[#black]> Serveur : 19 - Retourner les informations (utilisateur OK)
activate BDD
deactivate BDD
activate Serveur
deactivate User
Serveur -[#black]> Serveur : Préparer la requête recherche liste des produits 
Serveur -[#black]> BDD : Call BDD  GetAlll(données des porduits existant en base de données)
activate BDD
BDD -[#black]> BDD : Rechercher credentails dans la table (T_PRODUCTS)
autonumber stop
BDD -[#red]> Serveur : SQL/DAOException
deactivate BDD
Serveur -[#red]> Client : Construire/Remonter le message d'erreurs avec le code statut HTTP
deactivate Serveur
activate Client
Client -[#red]> User : Remonter le message d'erreurs associé avec le code statut HTTP
activate User
deactivate User
deactivate Client

' Traitement du retour OK de la base de données
autonumber 22
BDD -[#black]> Serveur : Réponse recherche OK (Pas d'Exception)
activate BDD
deactivate BDD
activate Serveur
Serveur -[#black]> Serveur : Préparer la réponse à retourner (basée sur les autorités)

' Ajout de commentaires
note right of Serveur 
Authentifier
Autoriser en utilisant les autorités de l'utilisateur
end note

Serveur -[#black]> Client : Retourner les informations des produits (liste des produits:[])
deactivate Serveur 
activate Client
Client -[#black]> User : Afficher la liste des produits (code statut HTTP)
activate User
deactivate Client
deactivate User
@enduml
```

## Modèles et Schémas de données
Les modèles fournis sont relatifs au **_métier_**. Le diagramme de classes ci-dessous présente les relations entre les entités de gestion de la partie métier de l'application.
Il est founi avec `PlantUML` au format .md.

```plantuml
@startuml
' Déclaration des types 
interface Persistable<ID> #Aqua
'interface GrantedAuthority extends Serializable
interface GrantedAuthority #Aqua 
abstract class AbstractPersistable<PK extends Serializable> #Gold

' Déclaration de l'Enum : RoleEnum 
enum RoleEnum  #Pink
{
ROLE_USER
ROLE_ADMIN
ROLE_MODERATOR
ROLE_ANONYMOUS
+ getAuthority() : String
}

' Déclaration de l'Enum : CategoryTypeEnum
enum CategoryTypeEnum  #Pink
{
TELEPHONIE
TV
SON
INFORMATIQUE
PHOTO
JEUX_VIDEO
JOUETS
ELCETROMENAGER
MEUBLES_DECO
LITERIE
}

' Déclaration de la classe : User 
 class User #lightgreen
{
- id : Long
- username : String
- password : String
- email : String 
- accountExpired : Boolean
- accountLocked : Boolean
- credentialsExpired : Boolean
- enabled : Boolean
- roles : Set<RoleEnum>
- createdTime : LocalDateTime
- updatedTime : LocalDateTime
- version : Integer
}

' Déclaration de la classe : Category 
'class Category extends AbstractPersistable 
class Category #fdad5c
{
- id : Long
- name : String
- description : String 
- enabled : Boolean
- products : Set<Product>
- categoryType : CategoryTypeEnum
- version : Integer
}

' Déclaration de la classe : Product 
' class Product extends AbstractPersistable 
class Product #skyblue
{
- id : Long
- name : String
- description : String
- quantity : Long
- unitPrice : BigDecimal
- price : BigDecimal
- isActive : Boolean
- imageUrl : String
- version : Integer
}

' Défintion des relations entre les objets

' Realtions entre GrantedAuthority et RoleEnum 
GrantedAuthority <|-.. RoleEnum

' Realtions d'héritages
Serializable <|-- GrantedAuthority
AbstractPersistable <|-- User
AbstractPersistable <|-- Category
AbstractPersistable <|-- Product

' Realtions d'implémentation
Persistable  <|-.. AbstractPersistable
Serializable<|-.. User
Serializable<|-.. Category
Serializable<|-.. Product 

' Realtions entre les objets metier
RoleEnum "- roles" <--* "1" User
Category -->  "- categoryType" CategoryTypeEnum
Category "- category" o-- "- products" Product
@enduml
```

# Stack Technique
Une liste non exhaustive des technos utilsées pour le développement de cette application :

![](https://img.shields.io/badge/Java_11-✓-blue.svg)
![](https://img.shields.io/badge/Maven_3-✓-blue.svg)
![](https://img.shields.io/badge/Spring_Boot_2-✓-blue.svg)
![](https://img.shields.io/badge/Spring_Security_5-✓-blue.svg)
![](https://img.shields.io/badge/Spring_AOP-✓-blue.svg)
![](https://img.shields.io/badge/JWT-✓-blue.svg)
![](https://img.shields.io/badge/Jpa_2-✓-blue.svg)
![](https://img.shields.io/badge/Hibernate_5-✓-blue.svg)
![](https://img.shields.io/badge/PostgreSQL_10+-✓-blue.svg)
![](https://img.shields.io/badge/Junit_5-✓-blue.svg)
![](https://img.shields.io/badge/Model_Mapper-✓-blue.svg)
![](https://img.shields.io/badge/Flyway-✓-blue.svg)
![](https://img.shields.io/badge/Angular_11-✓-blue.svg)
![](https://img.shields.io/badge/Docker-✓-blue.svg)
![](https://img.shields.io/badge/Swagger_3.0_/OpenAPI-✓-blue.svg)
![](https://img.shields.io/badge/EhCache-✓-blue.svg)
![](https://img.shields.io/badge/Lombok-✓-blue.svg)
![](https://img.shields.io/badge/PlantUML-✓-blue.svg)

C'est un projet `Maven` avec `Spring Boot` donc basé sur le langage `Java` : 
- **`PlantUML`** avec intégration de `StarUML` pour la production au format markdown des éléments de modélisation et conception des spécifications techniques fournies.
- **`Java 11`** est utilisé pour la compilation et cible pour l'environnement d'exécution.
- **`Spring Security`**, **`JWT`**, pour sécuriser les échanges (production de jetons, authentification et autorisation).
- **`AOP`** pour la séparation des préoccupations transversales dans l'application. Ici, il s'agit de la journalisation dans le couches applicatives : `le logging`
- **`JPA / Hibernate`** pour les concepts ORM et DAO.
- **`H2`**, **`MariaDB`**, **`PostgreSQL`** configurations pour les accès aux données en base et pour la réalisation de TI (`_Tests d'Intégration_` : système)
- **`Flyway`** pour la migration de bases de données.
- **`EhCache`** pour optimiser les accès aux données.
- **`Angular`** pour le développment de l'interface utilisateur (le Clent Web).
- **`Docker`** pour la conteneurisation des services/ de l'application
- **`Lombok`** pour générer du code couramment utilisé et faciliter le maintien du code source propre, simplifier l'écriture des classes.
- **`Keytool/OpenSSL`** pour la génération du magasin des `clés privées/publiques RSA`, export de la clé publique et du certificat dans des fichiers pour exploitation autraversde l'API Java dédiée. 
- **`Swagger 3.0.0 /OpenAPI`** pour la documentation et tests de l'API.
- **`JUnit 5`** pour l'écriture des codes sources des classes unitaires et d'intégration.
- **`SonarLint`** intégré dans l'IDE (_STS_) pour `analyser la qualité du code` livré, poussé dans le `repository` (_bonnes pratiques de développement_).
- **`MoreUnit`** intégré dans l'IDE (_STS_) pour `taguer` les classes du code source couvertes par des TU (_Tests Unitaires_).
- **`JaCoCo`** librairie java d'analyse de couverture de codes. Produire/fournir les rapports de couverture du code source (lignes, branches,..) par les tests réalisés. Offrir une visualisation graphique de la couverture
de codes et fournit des rapports détaillés de l'analyse de la couverture.


# Configurations
Les configurations de l'application permettent de faciliter aussi bien le démarrage, l'exécution que l'exploitation de celle-ci.

## Configurations de la Sécurité dans l'application 
Afin de rehausser le niveau de sécurité dans l'application, celle-ci sera abordée selon les points ci-dessous :
- **`la sécurité applicative`** : sécurisé les accès aux ressources de l'application 
- **`la sécurité au niveau transport`** : sécurisé les échanges avec l'application

### Sécurité applicative
Elle consiste à _sécuriser les ressources_ de l'application (donc les accès à celles-ci). Elle est mise en place dans l'application par les spécifications `JWT` et `Spring Security` consistant à produire/fournir les `jetons d'accès JWT`.
Le choix opéré pour cette application (`rehausser le niveau de sécurité`) est de **signer/valider** les `jetons JWT` avec des **clés privées/publiques RSA**,au lieu d'utiliser le **secret HMAC partagé**.
Ceci, offre l'avantage que le jeton JWT soit généré et signé par une autorité centrale (généralement un serveur d'autorisation).Ainsi, l'application (les services) 
peut (peuvent) valider les `jetons JWT` à l'aide de la **_clé publique exposée à partir du serveur d'autorisation_**.
Les éléments permettant de fournir les `clés privées/publiques RSA` pour signer/valider les jetons JWT d'accès aux ressources, peuvent être mise en place de deux façons différentes :
- en **`ligne de commande`** : en utilisant les outils `Keytool et OpenSSL` pour générer clés et fichiers, puis utiliser l'API Java dédiée pour recupérer les éléméents attendus. 
- ou avec **`KeyStore Explorer`** : utiliser les fonctionnalités offertes par l'outil graphique pour explorer le magasin des clés (Keystore par exemple), produire les clés et fichiers, puis utiliser l'API Java dédiée pour recupérer les éléméents attendus.  

### Sécurité au niveau transport : Activer le support `TLS`
La sécurité au niveau du transport `applique des contrôles de sécurité lors de l’établissement d’une connexion` entre les consommateurs de services (les clients), et le serveur. 
Il assure donc la confidentialité des données échangées over `HTTP` -> donc utilisation de `HTTPS` pour le transport des données. Le protocole `HTTPS`utilise `TLS` pour sécuriser la communication. Ainsi donc :
- Le `niveau de transport entrant` **sécurise** la _communication entre les clients et le serveur_.
- Le `niveau de transport sortant` sécurise de façon implicite les trois techniques d'envoi de demandes sortantes à savoir : `actions de routage, actions de publication et actions d'appel`. 


Les détails sur la mise en place et exploitation des éléments des configurations de sécurité sont fournis dans le fichier :
[README](/jwt-auth-web-api-back-end/README.md).

## Base de données 
TODO

## Configuration applicatives 
TODO

# Points de terminaison REST
L'application fournit des points de terminaison HTTP et des outils pour exposer les fonctionnalités proposées.
TODO

# Compilation, Packaging, Exécution et Documentation
## Compilation et Packaging
TODO

## Exécution
TODO

## Documentation Swagger des REST API
TODO


# Les Tests
Les outils de tests classiques de **Java** et **Spring** sont utilisés pour effectuer des tests.

## Les Types de Tests
- **Tests unitaires** : pas seulement pour un effet de test immédiat du code, mais également permettre d'effectuer des tests de non-régression lors de modifications qui interviendront inévitablement durant la vie de l'application.
- **Tests d'intégration** : assurer que le comportement de l'application est toujours aussi conforme, au fur et à mesure de l'assemblage des unités de code. Nous couvirons les deux types à savoir :
	- **_Tests d'intégration composants_** : vérifier que les unités de code fonctionnent corectement ensemble, dans un environnement de test assez proche du test unitaire, c'est-à-dire de manière isolée,
sans lien avec des composants extérieurs et ne permettant pas le démarrage d'une vraie application. Ce type de test répond à la question : `Est-ce que les classes testées unitairement fonctionnent vraiment bien ensemble ?`
	- **_Tests d'intégration système_** : vérifier le bon fonctionnement de plusieurs unités de code au sein d'une configuration d'application, avec éventuellement des liens avec des composants extérieurs
comme une base de données, des fichiers, ou des API en réseau. Ce type de test répond à la question : `Comment pouvons-nous rapidement tester que notre application en fonctionnement collaborerait avec le monde extérieur ?`
- **Tests fonctionnels** :  partent de l'interface utilisateur pour obtenir un résultat selon un scénario prédéfini. Ils imitent l'utilisateur final de l'application. Un démarrage complet de l'application est donc nécessaire.
Ce type de test répond à la question : `Comment pouvons-nous vérifier qu'un utilisateur final utilisera une application conforme et cohérente de bout en bout ?`

## Les Outils de Tests
La partie `test` de l’écosystème `Spring` (Framework de base de l’application) plus précisément sa composante : `spring-boot-starter-test`, avec ses apports : `spring-test, spring-boot-test, spring-boot-test-autoconfigure`, 
fournit des outils permettant la réalisation des types de tests cités ci-dessus.
Le tableau ci-dessous dresse une liste des outils disponibles pour la réalisation des Tests :

|Outil|Description|
|---|---|
|**`Mockito/BDDMockito`**|_pour les mocks /Style d'écriture des tests de développement (Behavior Driven Development)  piloté par le comportement, il utilise : //given //when //then_|
|**`JUnit 5`**|_pour l'écriture des classes des Tests Unitaires et d'intégration_|
|**`Assert-J`**|_pour les assertions_|
|**`Postman`**|_pour tester les fonctionnalités exposées par les API_|
|**`JaCoCo`**|_Plugin maven (avec les plugin surefire et failsafe) pour produire/fournir les rapports de couverture de codes_|
|**`Swagger`**|_Pour générer la documentation et Tester les REST API_|


## Rapport de couverture de codes
La couverture des tests est mesurée et fournie par `JaCoCo`. L'image ci-dessous fournit la couverture du code de l'application à l'exception des objets de couche de modèle (objets métiers).

La copie d'écran ci-dessous fournit l'image de la couverture des tests lors des 1ères phases de développement du back-end.
![JaCoCo Tests Coverage Report](./docs/images/tests_coverage_report.png "JaCoCo Tests Coverage Report")

# Packaging et Livrables
TODO