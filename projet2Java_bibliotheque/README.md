
Projet 2 - Application Java de Gestion d'une Bibliothèque

Introduction

    Ce projet est une application Java qui permet de gérer une bibliothèque, notamment les livres, les membres et les emprunts. Elle utilise Java, PostgreSQL comme base de données, et Maven pour la gestion des dépendances.

Fonctionnalités
- Ajouter, afficher, rechercher, et supprimer des livres.
- Inscrire, afficher, rechercher, et supprimer des membres.
- Enregistrer des emprunts et gérer les retours.
- Calculer les pénalités en cas de retard de retour.

Technologies Utilisées
- Java JDK 17 (ou version ultérieure).
- PostgreSQL comme base de données.
- Maven pour la gestion des dépendances.
- IntelliJ IDEA, Eclipse, ou tout autre IDE Java.

Configuration et Instructions d’Exécution

1. Configuration de l'environnement

Installez les outils nécessaires :
- Java JDK 17 ou version supérieure.
- PostgreSQL comme serveur de base de données.
- Maven pour gérer les dépendances.
- IntelliJ IDEA ou un autre IDE Java.

Configurez Maven dans IntelliJ IDEA :
- Allez dans File > New Project > Maven.
- Définissez un GroupId (ex. com.example) et un ArtifactId (ex. project2_JavaCourse).
- Cliquez sur Finish pour créer le projet Maven.

Assurez-vous que :
- PostgreSQL est en cours d'exécution.
- Les variables d'environnement JAVA_HOME et MAVEN_HOME sont configurées.

2. Structure du Projet
Le projet est structuré comme suit :

src/
├── main/
│   ├── java/
│   │   ├── daos/
│   │   │   ├── EmpruntDAO      #DAO pour gérer les emprunts dans la bibliothèque
│   │   │   ├── LivreDAO        #DAO pour gérer les livres dans la bibliothèque
│   │   │   ├── MembreDAO       #DAO pour gérer les membres dans la bibliothèque
│   │   ├── models/             #Classe représentant un emprunt dans 
│   │   │   ├── Bibliotheque.java  #Classe pour centraliser toutes les fonctionnalités
│   │   │   ├── DatabaseConnection.java   #Gestion de la connexion PostgreSQL
│   │   │   ├── Emprunt         #Classe représentant un emprunt dans la bibliothèque
│   │   │   ├── Livre           #Classe représentant un livre dans la bibliothèque
│   │   │   ├── Membre          #Classe représentant un membre dans la bibliothèque
│   │   ├── App.java            #Classe principale avec le menu interactif
├── resources/ 
│   ├── bibliothequeDB_javaProject2.sql #Base de Données de la bibliothèque
│   ├── classes_UML.pdf         #Diagramme UML de classes
│   ├── test_execution.txt      #Exemple d’exécution avec tests simples
│   ├── lien_github.txt         #Lien du repository GitHub
├── README.md                   #Instructions pour exécuter le projet
├── pom.xml                     # Fichier Maven

3. Création de la Base de Données
- Importer la base de données :
    Vérifiez que le fichier bibliothequeDB_javaProject2.sql est présent dans le dossier resources.
- Exécutez la commande suivante pour importer la base de données dans PostgreSQL :
    psql -U postgres -d bibliotheque -f resources/bibliothequeDB_javaProject2.sql

4. Télécharger et configurer le projet
- Cloner le projet
    git clone <URL-DU-RÉPÔT>
    cd <nom-du-dossier-cloné>
- Ouvrir dans un IDE
    Ouvrez le projet dans IntelliJ IDEA, Eclipse, ou votre IDE Java préféré.
- Configuration de Maven
    Ajoutez les dépendances nécessaires dans le fichier pom.xml :

<dependencies>
    <!-- PostgreSQL JDBC Driver -->
    <dependency>
        <groupId>org.postgresql</groupId>
        <artifactId>postgresql</artifactId>
        <version>42.6.0</version>
    </dependency>

    <!-- JUnit pour les tests -->
    <dependency>
        <groupId>junit</groupId>
        <artifactId>junit</artifactId>
        <version>4.13.2</version>
        <scope>test</scope>
    </dependency>
</dependencies>

- Recharger le projet Maven pour télécharger les dépendances.
- Configurer la Connexion à la Base de Données

    Dans le fichier models.DatabaseConnection.java, modifiez les informations de connexion si nécessaire :

    private static final String URL = "jdbc:postgresql://localhost:5432/bibliotheque";
    private static final String USER = "postgres"; // Votre utilisateur PostgreSQL
    private static final String PASSWORD = "password"; // Votre mot de passe PostgreSQL

5. Exécuter le projet

Avec l'IDE
- Compiler le Projet :
    Dans IntelliJ IDEA, cliquez sur Build > Build Project.

- Exécuter le Projet :
    Localisez la classe principale App.java.
    Faites un clic droit et sélectionnez Run App.main().

Avec Maven
- Compiler le Projet :
    mvn clean compile

- Exécuter l'Application :
    mvn exec:java -Dexec.mainClass="App"

6. Utilisation de l’application

Une fois l'application démarrée, un menu interactif s'affiche comme suit :

==================== Bibliothèque ====================
1. Ajouter un livre
2. Afficher tous les livres
3. Rechercher un livre par titre
4. Modifier les informations d'un livre
5. Supprimer un livre
6. Inscrire un membre
7. Afficher tous les membres
8. Rechercher un membre par nom
9. Modifier les informations d'un membre
10. Supprimer un membre
11. Enregistrer un emprunt
12. Afficher tous les emprunts
13. Modifier les informations d'un emprunt
14. Retourner un livre
15. Calculer les pénalités d'un emprunt
16. Quitter

Choisissez une option :

7. Exemple d’utilisation : Ajouter un livre

- Sélectionnez l'option 1.

- Entrez les informations demandées :
    Titre : Harry Potter
    Auteur : J.K. Rowling
    Catégorie : Fantasy
    Nombre d'exemplaires : 10

- Sortie :
    models.Livre ajouté avec succès !

8. Résolution des problèmes

- Problème : No suitable driver found for jdbc:postgresql://...
    Assurez-vous que le pilote PostgreSQL est inclus dans le fichier pom.xml.
    Recharger le projet Maven.

- Problème : FATAL: database "bibliotheque" does not exist
    Assurez-vous que la base de données bibliotheque a été créée et que les tables ont été initialisées.

- Problème : Access Denied ou Connexion Impossible
    Vérifiez les informations de connexion dans models.DatabaseConnection.java.

NB: Avec ces instructions, vous pouvez exécuter le projet localement et commencer à l'utiliser.

