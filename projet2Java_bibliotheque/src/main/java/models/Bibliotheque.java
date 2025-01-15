package models;

import daos.*;

import java.sql.*;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * Classe pour centraliser toutes les fonctionnalités de gestion de la bibliothèque.
 * Constructeur de la classe models.Bibliotheque.
 * Initialise les DAO pour les livres, les membres, et les emprunts.
 */
public class Bibliotheque {
    private final LivreDAO livreDAO = new LivreDAO();
    private final MembreDAO membreDAO = new MembreDAO();
    private final EmpruntDAO empruntDAO = new EmpruntDAO();

    // --------------------------- Gestion des Livres ---------------------------
    /**
     * Ajouter un livre à la bibliothèque.
     */
    public void ajouterLivre(Livre livre) throws SQLException {
        livreDAO.ajouterLivre(livre);
        System.out.println("models.Livre ajouté avec succès !");
    }

    /**
     * Afficher tous les livres disponibles dans la bibliothèque.
     */
    public void afficherTousLesLivres() throws SQLException {
        List<Livre> livres = livreDAO.afficherTousLesLivres();
        System.out.println("Liste des livres :");
        for (Livre livre : livres) {
            System.out.println(livre);
        }
    }

    /**
     * Afficher toutes les catégories de livres disponibles dans la bibliothèque.
     */
    public void afficherToutesCategories() {
        try {
            List<String> categories = livreDAO.getToutesCategories();

            if (categories.isEmpty()) {
                System.out.println("Aucune catégorie disponible dans la bibliothèque.");
            } else {
                System.out.println("Catégories disponibles :");
                // Afficher chaque catégorie avec un numéro
                for (int i = 0; i < categories.size(); i++) {
                    System.out.println((i + 1) + ". " + categories.get(i));
                }
                // Demander à l'utilisateur de choisir une catégorie
                System.out.print("Choisissez un numéro de catégorie pour afficher les livres : ");
                Scanner scanner = new Scanner(System.in);

                int choixCategorie = -1;
                boolean validInput = false;

                // Boucle jusqu'à ce que l'entrée soit valide
                while (!validInput) {
                    try {
                        choixCategorie = scanner.nextInt();
                        // Vérifier si le choix est dans la plage valide
                        if (choixCategorie > 0 && choixCategorie <= categories.size()) {
                            validInput = true; // Entrée valide
                        } else {
                            System.out.println("Choix invalide. Veuillez sélectionner un numéro entre 1 et " + categories.size());
                        }
                    } catch (InputMismatchException e) {
                        System.out.println("Entrée invalide. Veuillez entrer un numéro entier.");
                        scanner.nextLine(); // Consommer la ligne incorrecte pour éviter la boucle infinie
                    }
                }

                // Si l'entrée est valide, afficher les livres de la catégorie choisie
                String categorieChoisie = categories.get(choixCategorie - 1);
                afficherLivresParCategorie(categorieChoisie);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Afficher l'erreur si une exception SQL se produit
        }
    }




    /**
     * Afficher les livres d'une catégorie donnée dans la bibliothèque.
     * Si la catégorie n'existe pas, un message d'erreur est affiché.
     *
     * @param categorie La catégorie des livres à afficher.
     * @return
     */
    public boolean afficherLivresParCategorie(String categorie) {
        try {
            System.out.println("Catégorie recherchée : '" + categorie + "'");  // Log pour inspecter la valeur exacte de la catégorie
            // Vérifier si la catégorie existe dans la base de données
            if (livreDAO.categorieExiste(categorie)) {
                List<Livre> livresParCategorie = livreDAO.afficherLivresParCategorie(categorie);

                // Vérifier si des livres ont été trouvés pour cette catégorie
                if (livresParCategorie.isEmpty()) {
                    System.out.println("Aucun livre trouvé pour cette catégorie.");
                } else {
                    System.out.println("Livres dans la catégorie " + categorie + ":");
                    // Afficher tous les livres trouvés dans la catégorie
                    for (Livre livre : livresParCategorie) {
                        System.out.println(livre); // Assurez-vous que la méthode toString() est définie dans Livre
                    }
                }
            } else {
                System.out.println("La catégorie '" + categorie + "' n'existe pas dans la base de données.");
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Afficher l'erreur si une exception SQL se produit
        }
        return false;
    }


    /**
     * Rechercher un livre par titre.
     */
    public Livre rechercherLivreParTitre(String titre) throws SQLException {
        return livreDAO.rechercherLivreParTitre(titre);
    }

    /**
     * Modifier les informations d'un livre.
     *
     * @param idLivre L'identifiant du livre à modifier
     * @param titre Le nouveau titre du livre (peut être null)
     * @param auteur Le nouvel auteur du livre (peut être null)
     * @param categorie La nouvelle catégorie du livre (peut être null)
     * @param nombreExemplaires Le nouveau nombre d'exemplaires (0 si pas modifié)
     * @throws SQLException Si une erreur survient lors de la requête SQL
     */
    public void modifierLivre(int idLivre, String titre, String auteur, String categorie, int nombreExemplaires) throws SQLException {
        livreDAO.modifierLivre(idLivre, titre, auteur, categorie, nombreExemplaires);
    }

    /**
     * Supprimer un livre par son identifiant.
     */
    public void supprimerLivre(int id) throws SQLException {
        livreDAO.supprimerLivre(id);
        System.out.println("models.Livre supprimé avec succès !");
    }

    // --------------------------- Gestion des Membres ---------------------------

    /**
     * Inscrire un nouveau membre à la bibliothèque.
     */
    public void inscrireMembre(Membre membre) throws SQLException {
        membreDAO.ajouterMembre(membre);
        System.out.println("models.Membre inscrit avec succès !");
    }

    /**
     * Afficher tous les membres inscrits.
     */
    public void afficherTousLesMembres() throws SQLException {
        List<Membre> membres = membreDAO.afficherTousLesMembres();
        System.out.println("Liste des membres :");
        for (Membre membre : membres) {
            System.out.println(membre);
        }
    }

    /**
     * Rechercher un membre par nom.
     */
    public Membre rechercherMembreParNom(String nom) throws SQLException {
        return membreDAO.rechercherMembreParNom(nom);
    }

    /**
     * Modifier les informations d'un membre.
     *
     * @param idMembre L'identifiant du membre à modifier
     * @param nom Le nouveau nom du membre (peut être null)
     * @param prenom Le nouveau prénom du membre (peut être null)
     * @param email Le nouvel email du membre (peut être null)
     * @param dateAdhesion La nouvelle date d'adhésion (peut être null)
     * @throws SQLException Si une erreur survient lors de la requête SQL
     */
    public void modifierMembre(int idMembre, String nom, String prenom, String email, String dateAdhesion) throws SQLException {
        membreDAO.modifierMembre(idMembre, nom, prenom, email, dateAdhesion);
    }

    /**
     * Supprimer un membre par son identifiant.
     */
    public void supprimerMembre(int id) throws SQLException {
        membreDAO.supprimerMembre(id);
        System.out.println("models.Membre supprimé avec succès !");
    }

    // --------------------------- Gestion des Emprunts ---------------------------

    /**
     * Enregistrer un nouvel emprunt.
     */
    public void enregistrerEmprunt(Emprunt emprunt) throws SQLException {
        empruntDAO.enregistrerEmprunt(emprunt);
        System.out.println("models.Emprunt enregistré avec succès !");
    }

    /**
     * Afficher tous les emprunts présents dans la base de données.
     *
     * @throws SQLException Si une erreur survient lors de la requête SQL
     */
    public void afficherTousLesEmprunts() throws SQLException {
        List<Emprunt> emprunts = empruntDAO.afficherTousLesEmprunts();
        System.out.println("Liste des emprunts :");
        for (Emprunt emprunt : emprunts) {
            System.out.println(emprunt);
        }
    }

    /**
     * Modifier les informations d'un emprunt.
     *
     * @param idEmprunt L'identifiant de l'emprunt à modifier
     * @param dateRetourPrevue La nouvelle date de retour prévue (peut être null)
     * @param dateRetourEffective La nouvelle date de retour effective (peut être null)
     * @throws SQLException Si une erreur survient lors de la requête SQL
     */
    public void modifierEmprunt(int idEmprunt, String dateRetourPrevue, String dateRetourEffective) throws SQLException {
        empruntDAO.modifierEmprunt(idEmprunt, dateRetourPrevue, dateRetourEffective);
    }

    /**
     * Retourner un livre emprunté.
     */
    public void retournerLivre(int idEmprunt, String dateRetourEffective) throws SQLException {
        empruntDAO.retournerLivre(idEmprunt, Date.valueOf(dateRetourEffective));
        System.out.println("models.Livre retourné avec succès !");
    }

    /**
     * Calculer la pénalité pour un emprunt.
     */
    public long calculerPenalite(int idEmprunt) throws SQLException {
        return empruntDAO.calculerPenalite(idEmprunt);
    }

}

