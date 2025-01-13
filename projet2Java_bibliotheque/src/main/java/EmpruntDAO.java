import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO pour gérer les emprunts dans la bibliothèque.
 */
public class EmpruntDAO {

    /**
     * Enregistrer un emprunt dans la base de données.
     */
    public void enregistrerEmprunt(Emprunt emprunt) throws SQLException {
        String query = "INSERT INTO emprunts (membre_id, livre_id, date_emprunt, date_retour_prevue) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, emprunt.getMembreId());
            stmt.setInt(2, emprunt.getLivreId());
            stmt.setDate(3, Date.valueOf(emprunt.getDateEmprunt()));
            stmt.setDate(4, Date.valueOf(emprunt.getDateRetourPrevue()));
            stmt.executeUpdate();
        }
    }

    /**
     * Récupérer tous les emprunts depuis la base de données.
     *
     * @return Une liste d'emprunts
     * @throws SQLException Si une erreur survient lors de la requête SQL
     */
    public List<Emprunt> afficherTousLesEmprunts() throws SQLException {
        String query = """
            SELECT e.id_emprunt, e.membre_id, e.livre_id, e.date_emprunt,
                   e.date_retour_prevue, e.date_retour_effective,
                   m.nom AS membre_nom, l.titre AS livre_titre
            FROM emprunts e
            JOIN membres m ON e.membre_id = m.id
            JOIN livres l ON e.livre_id = l.id;
        """;
        List<Emprunt> emprunts = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Emprunt emprunt = new Emprunt(
                        rs.getInt("id_emprunt"),
                        rs.getInt("membre_id"),
                        rs.getInt("livre_id"),
                        rs.getDate("date_emprunt").toLocalDate(),
                        rs.getDate("date_retour_prevue").toLocalDate()
                );
                emprunt.setDateRetourEffective(
                        rs.getDate("date_retour_effective") != null
                                ? rs.getDate("date_retour_effective").toLocalDate()
                                : null
                );

                System.out.println("Emprunt{id=" + emprunt.getIdEmprunt() +
                        ", membre=" + rs.getString("membre_nom") +
                        ", livre=" + rs.getString("livre_titre") +
                        ", dateEmprunt=" + emprunt.getDateEmprunt() +
                        ", dateRetourPrevue=" + emprunt.getDateRetourPrevue() +
                        ", dateRetourEffective=" + emprunt.getDateRetourEffective() +
                        "}");
            }
        }
        return emprunts;
    }

    /**
     * Mettre à jour les informations d'un emprunt dans la base de données.
     *
     * @param idEmprunt L'identifiant de l'emprunt à modifier
     * @param dateRetourPrevue La nouvelle date de retour prévue (peut être null si pas modifiée)
     * @param dateRetourEffective La nouvelle date de retour effective (peut être null si pas modifiée)
     * @throws SQLException Si une erreur survient lors de la requête SQL
     */
    public void modifierEmprunt(int idEmprunt, String dateRetourPrevue, String dateRetourEffective) throws SQLException {
        StringBuilder query = new StringBuilder("UPDATE emprunts SET ");
        boolean hasChanges = false;

        if (dateRetourPrevue != null) {
            query.append("date_retour_prevue = ?, ");
            hasChanges = true;
        }
        if (dateRetourEffective != null) {
            query.append("date_retour_effective = ?, ");
            hasChanges = true;
        }

        if (!hasChanges) {
            System.out.println("Aucune modification apportée à l'emprunt.");
            return;
        }

        // Supprime la virgule finale et ajoute la condition WHERE
        query = new StringBuilder(query.substring(0, query.length() - 2));
        query.append(" WHERE id_emprunt = ?");

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query.toString())) {
            int parameterIndex = 1;
            if (dateRetourPrevue != null) {
                stmt.setDate(parameterIndex++, java.sql.Date.valueOf(dateRetourPrevue));
            }
            if (dateRetourEffective != null) {
                stmt.setDate(parameterIndex++, java.sql.Date.valueOf(dateRetourEffective));
            }
            stmt.setInt(parameterIndex, idEmprunt);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Emprunt modifié avec succès !");
            } else {
                System.out.println("Aucun emprunt trouvé avec cet ID.");
            }
        }
    }

    /**
     * Retourner un livre (mettre à jour la date de retour effective).
     */
    public void retournerLivre(int idEmprunt, Date dateRetourEffective) throws SQLException {
        String query = "UPDATE emprunts SET date_retour_effective = ? WHERE id_emprunt = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setDate(1, dateRetourEffective);
            stmt.setInt(2, idEmprunt);
            stmt.executeUpdate();
        }
    }

    /**
     * Calculer la pénalité pour un retard.
     */
    public long calculerPenalite(int idEmprunt) throws SQLException {
        String query = "SELECT date_retour_prevue, date_retour_effective FROM emprunts WHERE id_emprunt = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, idEmprunt);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    LocalDate dateRetourPrevue = rs.getDate("date_retour_prevue").toLocalDate();
                    LocalDate dateRetourEffective = rs.getDate("date_retour_effective") != null
                            ? rs.getDate("date_retour_effective").toLocalDate()
                            : LocalDate.now();

                    if (dateRetourEffective.isAfter(dateRetourPrevue)) {
                        return java.time.temporal.ChronoUnit.DAYS.between(dateRetourPrevue, dateRetourEffective) * 100;
                    }
                }
            }
        }
        return 0;
    }
}
