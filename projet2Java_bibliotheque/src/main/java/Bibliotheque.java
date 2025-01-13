import java.sql.*;
import java.util.List;

/**
 * Classe pour centraliser toutes les fonctionnalités de gestion de la bibliothèque.
 * Constructeur de la classe Bibliotheque.
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
        System.out.println("Livre ajouté avec succès !");
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
        System.out.println("Livre supprimé avec succès !");
    }

    // --------------------------- Gestion des Membres ---------------------------

    /**
     * Inscrire un nouveau membre à la bibliothèque.
     */
    public void inscrireMembre(Membre membre) throws SQLException {
        membreDAO.ajouterMembre(membre);
        System.out.println("Membre inscrit avec succès !");
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
        System.out.println("Membre supprimé avec succès !");
    }

    // --------------------------- Gestion des Emprunts ---------------------------

    /**
     * Enregistrer un nouvel emprunt.
     */
    public void enregistrerEmprunt(Emprunt emprunt) throws SQLException {
        empruntDAO.enregistrerEmprunt(emprunt);
        System.out.println("Emprunt enregistré avec succès !");
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
        System.out.println("Livre retourné avec succès !");
    }

    /**
     * Calculer la pénalité pour un emprunt.
     */
    public long calculerPenalite(int idEmprunt) throws SQLException {
        return empruntDAO.calculerPenalite(idEmprunt);
    }

}

