package daos;

import models.DatabaseConnection;
import models.Membre;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO pour gérer les membres de la bibliothèque.
 */
public class MembreDAO {

    /**
     * Ajouter un membre dans la base de données.
     *
     * @param membre Le membre à ajouter
     * @throws SQLException Si une erreur survient lors de l'exécution de la requête SQL
     */
    public void ajouterMembre(Membre membre) throws SQLException {
        String query = "INSERT INTO membres (nom, prenom, email, adhesion_date) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, membre.getNom());
            stmt.setString(2, membre.getPrenom());
            stmt.setString(3, membre.getEmail());
            stmt.setDate(4, Date.valueOf(membre.getAdhesionDate()));
            stmt.executeUpdate();
        }
    }

    /**
     * Récupérer tous les membres depuis la base de données.
     *
     * @return Une liste de membres
     * @throws SQLException Si une erreur survient lors de l'exécution de la requête SQL
     */
    public List<Membre> afficherTousLesMembres() throws SQLException {
        String query = "SELECT * FROM membres";
        List<Membre> membres = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Membre membre = new Membre(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("email"),
                        rs.getDate("adhesion_date").toString()
                );
                membres.add(membre);
            }
        }
        return membres;
    }

    /**
     * Rechercher un membre par son nom.
     *
     * @param nom Le nom du membre à rechercher
     * @return Le membre trouvé, ou null s'il n'existe pas
     * @throws SQLException Si une erreur survient lors de l'exécution de la requête SQL
     */
    public Membre rechercherMembreParNom(String nom) throws SQLException {
        String query = "SELECT * FROM membres WHERE nom ILIKE ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, "%" + nom + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Membre(
                            rs.getInt("id"),
                            rs.getString("nom"),
                            rs.getString("prenom"),
                            rs.getString("email"),
                            rs.getDate("adhesion_date").toString()
                    );
                }
            }
        }
        return null;
    }

    /**
     * Mettre à jour les informations d'un membre dans la base de données.
     *
     * @param idMembre L'identifiant du membre à modifier
     * @param nom Le nouveau nom du membre (peut être null si pas modifié)
     * @param prenom Le nouveau prénom du membre (peut être null si pas modifié)
     * @param email Le nouvel email du membre (peut être null si pas modifié)
     * @param dateAdhesion La nouvelle date d'adhésion (peut être null si pas modifiée)
     * @throws SQLException Si une erreur survient lors de la requête SQL
     */
    public void modifierMembre(int idMembre, String nom, String prenom, String email, String dateAdhesion) throws SQLException {
        StringBuilder query = new StringBuilder("UPDATE membres SET ");
        boolean hasChanges = false;

        if (nom != null) {
            query.append("nom = ?, ");
            hasChanges = true;
        }
        if (prenom != null) {
            query.append("prenom = ?, ");
            hasChanges = true;
        }
        if (email != null) {
            query.append("email = ?, ");
            hasChanges = true;
        }
        if (dateAdhesion != null) {
            query.append("adhesion_date = ?, ");
            hasChanges = true;
        }

        if (!hasChanges) {
            System.out.println("Aucune modification apportée au membre.");
            return;
        }

        // Supprime la virgule finale et ajoute la condition WHERE
        query = new StringBuilder(query.substring(0, query.length() - 2));
        query.append(" WHERE id = ?");

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query.toString())) {
            int parameterIndex = 1;
            if (nom != null) {
                stmt.setString(parameterIndex++, nom);
            }
            if (prenom != null) {
                stmt.setString(parameterIndex++, prenom);
            }
            if (email != null) {
                stmt.setString(parameterIndex++, email);
            }
            if (dateAdhesion != null) {
                stmt.setDate(parameterIndex++, java.sql.Date.valueOf(dateAdhesion));
            }
            stmt.setInt(parameterIndex, idMembre);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("models.Membre modifié avec succès !");
            } else {
                System.out.println("Aucun membre trouvé avec cet ID.");
            }
        }
    }

    /**
     * Supprimer un membre de la base de données.
     *
     * @param id L'identifiant du membre à supprimer
     * @throws SQLException Si une erreur survient lors de l'exécution de la requête SQL
     */
    public void supprimerMembre(int id) throws SQLException {
        String query = "DELETE FROM membres WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}
