import java.time.LocalDate;

/**
 * Classe représentant un Emprunt dans la bibliothèque.
 */
public class Emprunt {
    private int idEmprunt; // Identifiant unique de l'emprunt
    private int membreId; // Identifiant du membre ayant emprunté le livre
    private int livreId; // Identifiant du livre emprunté
    private LocalDate dateEmprunt; // Date de l'emprunt
    private LocalDate dateRetourPrevue; // Date prévue pour le retour du livre
    private LocalDate dateRetourEffective; // Date réelle de retour du livre

    /**
     * Constructeur de la classe Emprunt.
     *
     * @param idEmprunt Identifiant unique
     * @param membreId Identifiant du membre
     * @param livreId Identifiant du livre
     * @param dateEmprunt Date de l'emprunt
     * @param dateRetourPrevue Date de retour prévue
     */
    public Emprunt(int idEmprunt, int membreId, int livreId, LocalDate dateEmprunt, LocalDate dateRetourPrevue) {
        this.idEmprunt = idEmprunt;
        this.membreId = membreId;
        this.livreId = livreId;
        this.dateEmprunt = dateEmprunt;
        this.dateRetourPrevue = dateRetourPrevue;
    }

    // Getters et Setters
    public int getIdEmprunt() { return idEmprunt; }
    public void setIdEmprunt(int idEmprunt) { this.idEmprunt = idEmprunt; }
    public int getMembreId() { return membreId; }
    public void setMembreId(int membreId) { this.membreId = membreId; }
    public int getLivreId() { return livreId; }
    public void setLivreId(int livreId) { this.livreId = livreId; }
    public LocalDate getDateEmprunt() { return dateEmprunt; }
    public void setDateEmprunt(LocalDate dateEmprunt) { this.dateEmprunt = dateEmprunt; }
    public LocalDate getDateRetourPrevue() { return dateRetourPrevue; }
    public void setDateRetourPrevue(LocalDate dateRetourPrevue) { this.dateRetourPrevue = dateRetourPrevue; }
    public LocalDate getDateRetourEffective() { return dateRetourEffective; }
    public void setDateRetourEffective(LocalDate dateRetourEffective) { this.dateRetourEffective = dateRetourEffective; }

    @Override
    public String toString() {
        return "Emprunt{" +
                "idEmprunt=" + idEmprunt +
                ", membreId=" + membreId +
                ", livreId=" + livreId +
                ", dateEmprunt=" + dateEmprunt +
                ", dateRetourPrevue=" + dateRetourPrevue +
                ", dateRetourEffective=" + dateRetourEffective +
                '}';
    }
}
