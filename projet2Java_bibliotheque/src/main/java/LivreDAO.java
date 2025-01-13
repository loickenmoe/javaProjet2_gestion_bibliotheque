import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO pour gérer les opérations sur les livres dans la base de données.
 */
public class LivreDAO {

    /**
     * Ajouter un livre dans la base de données.
     *
     * @param livre Le livre à ajouter
     * @throws SQLException Si une erreur survient lors de l'exécution de la requête SQL
     */
    public void ajouterLivre(Livre livre) throws SQLException {
        String query = "INSERT INTO livres (titre, auteur, categorie, nombre_exemplaires) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, livre.getTitre());
            stmt.setString(2, livre.getAuteur());
            stmt.setString(3, livre.getCategorie());
            stmt.setInt(4, livre.getNombreExemplaires());
            stmt.executeUpdate();
        }
    }

    /**
     * Récupérer tous les livres depuis la base de données.
     *
     * @return Une liste de livres
     * @throws SQLException Si une erreur survient lors de l'exécution de la requête SQL
     */
    public List<Livre> afficherTousLesLivres() throws SQLException {
        String query = "SELECT * FROM livres";
        List<Livre> livres = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Livre livre = new Livre(
                        rs.getInt("id"),
                        rs.getString("titre"),
                        rs.getString("auteur"),
                        rs.getString("categorie"),
                        rs.getInt("nombre_exemplaires")
                );
                livres.add(livre);
            }
        }
        return livres;
    }

    /**
     * Rechercher un livre par son titre dans la base de données.
     *
     * @param titre Le titre du livre à rechercher
     * @return Le livre trouvé, ou null s'il n'existe pas
     * @throws SQLException Si une erreur survient lors de l'exécution de la requête SQL
     */
    public Livre rechercherLivreParTitre(String titre) throws SQLException {
        String query = "SELECT * FROM livres WHERE titre ILIKE ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, "%" + titre + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Livre(
                            rs.getInt("id"),
                            rs.getString("titre"),
                            rs.getString("auteur"),
                            rs.getString("categorie"),
                            rs.getInt("nombre_exemplaires")
                    );
                }
            }
        }
        return null;
    }


    /**
     * Mettre à jour les informations d'un livre dans la base de données.
     *
     * @param idLivre L'identifiant du livre à modifier
     * @param titre Le nouveau titre du livre (peut être null si pas modifié)
     * @param auteur Le nouvel auteur du livre (peut être null si pas modifié)
     * @param categorie La nouvelle catégorie du livre (peut être null si pas modifiée)
     * @param nombreExemplaires Le nouveau nombre d'exemplaires (0 si pas modifié)
     * @throws SQLException Si une erreur survient lors de la requête SQL
     */
    public void modifierLivre(int idLivre, String titre, String auteur, String categorie, int nombreExemplaires) throws SQLException {
        StringBuilder query = new StringBuilder("UPDATE livres SET ");
        boolean hasChanges = false;

        if (titre != null) {
            query.append("titre = ?, ");
            hasChanges = true;
        }
        if (auteur != null) {
            query.append("auteur = ?, ");
            hasChanges = true;
        }
        if (categorie != null) {
            query.append("categorie = ?, ");
            hasChanges = true;
        }
        if (nombreExemplaires > 0) {
            query.append("nombre_exemplaires = ?, ");
            hasChanges = true;
        }

        if (!hasChanges) {
            System.out.println("Aucune modification apportée au livre.");
            return;
        }

        // Supprime la virgule finale et ajoute la condition WHERE
        query = new StringBuilder(query.substring(0, query.length() - 2));
        query.append(" WHERE id = ?");

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query.toString())) {
            int parameterIndex = 1;
            if (titre != null) {
                stmt.setString(parameterIndex++, titre);
            }
            if (auteur != null) {
                stmt.setString(parameterIndex++, auteur);
            }
            if (categorie != null) {
                stmt.setString(parameterIndex++, categorie);
            }
            if (nombreExemplaires > 0) {
                stmt.setInt(parameterIndex++, nombreExemplaires);
            }
            stmt.setInt(parameterIndex, idLivre);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Livre modifié avec succès !");
            } else {
                System.out.println("Aucun livre trouvé avec cet ID.");
            }
        }
    }

    /**
     * Supprimer un livre de la base de données.
     *
     * @param id L'identifiant du livre à supprimer
     * @throws SQLException Si une erreur survient lors de l'exécution de la requête SQL
     */
    public void supprimerLivre(int id) throws SQLException {
        String query = "DELETE FROM livres WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}
