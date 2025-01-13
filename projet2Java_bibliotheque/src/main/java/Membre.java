/**
 * Classe représentant un Membre de la bibliothèque.
 */
public class Membre {
    private int id; // Identifiant unique du membre
    private String nom; // Nom du membre
    private String prenom; // Prénom du membre
    private String email; // Email du membre
    private String adhesionDate; // Date d'adhésion au format YYYY-MM-DD

    /**
     * Constructeur de la classe Membre.
     *
     * @param id Identifiant unique
     * @param nom Nom du membre
     * @param prenom Prénom du membre
     * @param email Email du membre
     * @param adhesionDate Date d'adhésion
     */
    public Membre(int id, String nom, String prenom, String email, String adhesionDate) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.adhesionDate = adhesionDate;
    }

    // Getters et Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getAdhesionDate() { return adhesionDate; }
    public void setAdhesionDate(String adhesionDate) { this.adhesionDate = adhesionDate; }

    @Override
    public String toString() {
        return "Membre{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", email='" + email + '\'' +
                ", adhesionDate='" + adhesionDate + '\'' +
                '}';
    }
}
