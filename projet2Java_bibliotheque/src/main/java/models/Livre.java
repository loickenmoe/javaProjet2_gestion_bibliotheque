package models;

/**
 * Classe représentant un models.Livre dans la bibliothèque.
 */
public class Livre {
    private int id; // Identifiant unique du livre
    private String titre; // Titre du livre
    private String auteur; // Auteur du livre
    private String categorie; // Catégorie ou genre du livre
    private int nombreExemplaires; // Nombre d'exemplaires disponibles

    /**
     * Constructeur de la classe models.Livre.
     *
     * @param id Identifiant unique
     * @param titre Titre du livre
     * @param auteur Auteur du livre
     * @param categorie Catégorie ou genre
     * @param nombreExemplaires Nombre d'exemplaires disponibles
     */
    public Livre(int id, String titre, String auteur, String categorie, int nombreExemplaires) {
        this.id = id;
        this.titre = titre;
        this.auteur = auteur;
        this.categorie = categorie;
        this.nombreExemplaires = nombreExemplaires;
    }

    // Getters et Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getTitre() { return titre; }
    public void setTitre(String titre) { this.titre = titre; }
    public String getAuteur() { return auteur; }
    public void setAuteur(String auteur) { this.auteur = auteur; }
    public String getCategorie() { return categorie; }
    public void setCategorie(String categorie) { this.categorie = categorie; }
    public int getNombreExemplaires() { return nombreExemplaires; }
    public void setNombreExemplaires(int nombreExemplaires) { this.nombreExemplaires = nombreExemplaires; }

    @Override
    public String toString() {
        return "models.Livre{" +
                "id=" + id +
                ", titre='" + titre + '\'' +
                ", auteur='" + auteur + '\'' +
                ", categorie='" + categorie + '\'' +
                ", nombreExemplaires=" + nombreExemplaires +
                '}';
    }
}
