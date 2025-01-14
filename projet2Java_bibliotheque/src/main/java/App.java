import models.Bibliotheque;
import models.Emprunt;
import models.Livre;
import models.Membre;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Scanner;

/**
 * Classe principale avec le menu interactif pour gérer la bibliothèque.
 */
public class App {
    public static void main(String[] args) {
        Bibliotheque bibliotheque = new Bibliotheque(); // Instanciation de la bibliothèque
        Scanner scanner = new Scanner(System.in);

        while (true) {
            // Affichage du menu principal
            System.out.println("\n==================== Bibliothèque ====================");
            System.out.println("1. Ajouter un livre");
            System.out.println("2. Afficher tous les livres");
            System.out.println("3. Rechercher un livre par titre");
            System.out.println("4. Modifier les informations d'un livre");
            System.out.println("5. Supprimer un livre");
            System.out.println("6. Inscrire un membre");
            System.out.println("7. Afficher tous les membres");
            System.out.println("8. Rechercher un membre par nom");
            System.out.println("9. Modifier les informations d'un membre");
            System.out.println("10. Supprimer un membre");
            System.out.println("11. Enregistrer un emprunt");
            System.out.println("12. Afficher tous les emprunts");
            System.out.println("13. Modifier les informations d'un emprunt");
            System.out.println("14. Retourner un livre");
            System.out.println("15. Calcul de la pénalité d'un emprunt");
            System.out.println("16. Quitter");
            System.out.print("Choisissez une option : ");

            String choix = scanner.nextLine();

            try {
                switch (choix) {
                    case "1":
                        System.out.print("Titre : ");
                        String titre = scanner.nextLine();
                        System.out.print("Auteur : ");
                        String auteur = scanner.nextLine();
                        System.out.print("Catégorie : ");
                        String categorie = scanner.nextLine();

                        // Demander le nombre d'exemplaires avec gestion d'exception et vérification >= 1
                        int nombreExemplaires = 0;  // Initialisation à une valeur invalide
                        boolean valid = false;
                        while (!valid) {
                            System.out.print("Nombre d'exemplaires (doit être supérieur ou égal à 1) : ");
                            try {
                                nombreExemplaires = scanner.nextInt(); // Tenter de lire un entier
                                if (nombreExemplaires >= 1) {
                                    valid = true; // Si l'entrée est valide, sortir de la boucle
                                } else {
                                    System.out.println("Le nombre d'exemplaires ne peut pas être inférieur à 1. Essayez à nouveau.");
                                }
                            } catch (java.util.InputMismatchException e) {
                                System.out.println("Erreur : veuillez entrer un nombre entier valide pour le nombre d'exemplaires.");
                                scanner.nextLine(); // Consommer la ligne pour éviter la boucle infinie
                            }
                        }

                        Livre livre = new Livre(0, titre, auteur, categorie, nombreExemplaires);
                        bibliotheque.ajouterLivre(livre);
                        break;

                    case "2":
                        bibliotheque.afficherTousLesLivres();
                        break;

                    case "3":
                        System.out.print("Titre : ");
                        String rechercheTitre = scanner.nextLine();
                        Livre livreTrouve = bibliotheque.rechercherLivreParTitre(rechercheTitre);
                        System.out.println(livreTrouve != null ? livreTrouve : "Aucun livre trouvé.");
                        break;

                    case "4":
                        System.out.print("ID du livre à modifier : ");
                        int idLivre = scanner.nextInt();
                        scanner.nextLine(); // Consommer la ligne restante

                        System.out.print("Nouveau titre (ou appuyez sur Entrée pour ne pas modifier) : ");
                        String nouveauTitre = scanner.nextLine();
                        if (nouveauTitre.isEmpty()) {
                            nouveauTitre = null;
                        }

                        System.out.print("Nouvel auteur (ou appuyez sur Entrée pour ne pas modifier) : ");
                        String nouvelAuteur = scanner.nextLine();
                        if (nouvelAuteur.isEmpty()) {
                            nouvelAuteur = null;
                        }

                        System.out.print("Nouvelle catégorie (ou appuyez sur Entrée pour ne pas modifier) : ");
                        String nouvelleCategorie = scanner.nextLine();
                        if (nouvelleCategorie.isEmpty()) {
                            nouvelleCategorie = null;
                        }

                        // Demander le nouveau nombre d'exemplaires avec gestion d'exception et vérification >= 0
                        int nouveauNombreExemplaires = -1;  // Initialisation à une valeur invalide
                        valid = false;
                        while (!valid) {
                            System.out.print("Nouveau nombre d'exemplaires (ou 0 pour ne pas modifier) : ");
                            try {
                                nouveauNombreExemplaires = scanner.nextInt(); // Tenter de lire un entier
                                if (nouveauNombreExemplaires >= 0 || nouveauNombreExemplaires == 0) {
                                    valid = true; // Si l'entrée est valide, sortir de la boucle
                                } else {
                                    System.out.println("Le nombre d'exemplaires ne peut pas être inférieur à 0. Essayez à nouveau.");
                                }
                            } catch (java.util.InputMismatchException e) {
                                System.out.println("Erreur : veuillez entrer un nombre entier valide pour le nombre d'exemplaires.");
                                scanner.nextLine(); // Consommer la ligne pour éviter la boucle infinie
                            }
                        }

                        try {
                            bibliotheque.modifierLivre(idLivre, nouveauTitre, nouvelAuteur, nouvelleCategorie, nouveauNombreExemplaires);
                        } catch (SQLException e) {
                            System.err.println("Erreur lors de la modification du livre : " + e.getMessage());
                        }
                        break;


                    case "5":
                        System.out.print("ID du livre : ");
                        idLivre = scanner.nextInt();
                        bibliotheque.supprimerLivre(idLivre);
                        break;

                    case "6":
                        System.out.print("Nom : ");
                        String nom = scanner.nextLine();
                        System.out.print("Prénom : ");
                        String prenom = scanner.nextLine();

                        // Demander l'email avec validation de format
                        String email = "";
                        boolean emailValide = false;
                        while (!emailValide) {
                            System.out.print("Email : ");
                            email = scanner.nextLine();

                            // Expression régulière pour valider un email de base
                            String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
                            if (email.matches(regex)) {
                                emailValide = true;
                            } else {
                                System.out.println("Erreur : L'email n'est pas valide. Veuillez entrer un email au format valide.");
                            }
                        }

                        System.out.print("Date d'adhésion (YYYY-MM-DD) : ");
                        String adhesionDate = scanner.nextLine();

                        Membre membre = new Membre(0, nom, prenom, email, adhesionDate);
                        bibliotheque.inscrireMembre(membre);
                        break;

                    case "7":
                        bibliotheque.afficherTousLesMembres();
                        break;

                    case "8":
                        System.out.print("Nom : ");
                        String nomRecherche = scanner.nextLine();
                        Membre membreTrouve = bibliotheque.rechercherMembreParNom(nomRecherche);
                        System.out.println(membreTrouve != null ? membreTrouve : "Aucun membre avec ce nom trouvé.");
                        break;

                    case "9":
                        System.out.print("ID du membre à modifier : ");
                        int idMembre = scanner.nextInt();
                        scanner.nextLine(); // Consommer la ligne restante

                        System.out.print("Nouveau nom (ou appuyez sur Entrée pour ne pas modifier) : ");
                        String nouveauNom = scanner.nextLine();
                        if (nouveauNom.isEmpty()) {
                            nouveauNom = null;
                        }

                        System.out.print("Nouveau prénom (ou appuyez sur Entrée pour ne pas modifier) : ");
                        String nouveauPrenom = scanner.nextLine();
                        if (nouveauPrenom.isEmpty()) {
                            nouveauPrenom = null;
                        }

                        // Demander le nouvel email avec validation de format
                        String nouvelEmail = null;
                        emailValide = false;
                        while (!emailValide) {
                            System.out.print("Nouvel email (ou appuyez sur Entrée pour ne pas modifier) : ");
                            String inputEmail = scanner.nextLine();

                            // Si l'email n'est pas vide, procéder à la validation
                            if (!inputEmail.isEmpty()) {
                                String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
                                if (inputEmail.matches(regex)) {
                                    nouvelEmail = inputEmail;  // Email valide, l'assigner
                                    emailValide = true;  // Terminer la boucle de validation
                                } else {
                                    System.out.println("Erreur : L'email n'est pas valide. Veuillez entrer un email au format valide.");
                                }
                            } else {
                                // Si l'email est vide, ne pas modifier l'email
                                nouvelEmail = null;
                                emailValide = true;
                            }
                        }

                        System.out.print("Nouvelle date d'adhésion (YYYY-MM-DD) ou appuyez sur Entrée pour ne pas modifier : ");
                        String nouvelleDateAdhesion = scanner.nextLine();
                        if (nouvelleDateAdhesion.isEmpty()) {
                            nouvelleDateAdhesion = null;
                        }

                        try {
                            bibliotheque.modifierMembre(idMembre, nouveauNom, nouveauPrenom, nouvelEmail, nouvelleDateAdhesion);
                        } catch (SQLException e) {
                            System.err.println("Erreur lors de la modification du membre : " + e.getMessage());
                        }
                        break;

                    case "10":
                        System.out.print("ID du membre : ");
                        idMembre = scanner.nextInt();
                        bibliotheque.supprimerMembre(idMembre);
                        break;

                    case "11":
                        int membreId = -1;
                        int livreId = -1;
                        String dateRetourPrevue = "";

                        // Demander l'ID du membre avec gestion d'exception
                        while (membreId < 1) {  // S'assurer que l'ID est supérieur ou égal à 1
                            System.out.print("ID du membre : ");
                            try {
                                membreId = scanner.nextInt(); // Tenter de lire un entier
                                if (membreId < 1) {
                                    System.out.println("Erreur : L'ID du membre doit être supérieur ou égal à 1.");
                                }
                            } catch (java.util.InputMismatchException e) {
                                System.out.println("Erreur : Veuillez entrer un nombre entier valide pour l'ID du membre.");
                                scanner.nextLine(); // Consommer la ligne restante pour éviter la boucle infinie
                            }
                        }

                        // Demander l'ID du livre avec gestion d'exception
                        while (livreId < 1) {  // S'assurer que l'ID est supérieur ou égal à 1
                            System.out.print("ID du livre : ");
                            try {
                                livreId = scanner.nextInt(); // Tenter de lire un entier
                                if (livreId < 1) {
                                    System.out.println("Erreur : L'ID du livre doit être supérieur ou égal à 1.");
                                }
                            } catch (java.util.InputMismatchException e) {
                                System.out.println("Erreur : Veuillez entrer un nombre entier valide pour l'ID du livre.");
                                scanner.nextLine(); // Consommer la ligne restante pour éviter la boucle infinie
                            }
                        }

                        // Demander la date de retour prévue
                        scanner.nextLine(); // Consommer la ligne restante
                        LocalDate dateEmprunt = LocalDate.now(); // Date d'emprunt (aujourd'hui)
                        LocalDate dateRetour = null;
                        boolean validDate = false;

                        while (!validDate) {
                            System.out.print("Date de retour prévue (YYYY-MM-DD) : ");
                            dateRetourPrevue = scanner.nextLine();
                            try {
                                dateRetour = LocalDate.parse(dateRetourPrevue); // Tenter de parser la date
                                if (!dateRetour.isBefore(dateEmprunt)) {
                                    validDate = true; // La date est valide si elle est postérieure ou égale à la date d'emprunt
                                } else {
                                    System.out.println("Erreur : La date de retour prévue doit être postérieure ou égale à la date d'emprunt (" + dateEmprunt + ").");
                                }
                            } catch (java.time.format.DateTimeParseException e) {
                                System.out.println("Erreur : Veuillez entrer une date valide au format YYYY-MM-DD.");
                            }
                        }

                        // Créer l'emprunt
                        Emprunt emprunt = new Emprunt(0, membreId, livreId, dateEmprunt, dateRetour);
                        bibliotheque.enregistrerEmprunt(emprunt);
                        break;


                    case "12":
                        // Afficher tous les emprunts
                        bibliotheque.afficherTousLesEmprunts();
                        break;

                    case "13":
                        int idEmprunt = -1;
                        String nouvelleDateRetourPrevue = null;
                        String nouvelleDateRetourEffective = null;

                        // Demander l'ID de l'emprunt avec gestion d'exception
                        while (idEmprunt < 1) {  // S'assurer que l'ID est supérieur ou égal à 1
                            System.out.print("ID de l'emprunt à modifier : ");
                            try {
                                idEmprunt = scanner.nextInt(); // Tenter de lire un entier
                                if (idEmprunt < 1) {
                                    System.out.println("Erreur : L'ID de l'emprunt doit être supérieur ou égal à 1.");
                                }
                            } catch (java.util.InputMismatchException e) {
                                System.out.println("Erreur : Veuillez entrer un nombre entier valide pour l'ID de l'emprunt.");
                                scanner.nextLine(); // Consommer la ligne restante pour éviter la boucle infinie
                            }
                        }

                        scanner.nextLine(); // Consommer la ligne restante

                        // Demander la nouvelle date de retour prévue
                        System.out.print("Nouvelle date de retour prévue (YYYY-MM-DD) ou appuyez sur Entrée pour ne pas modifier : ");
                        nouvelleDateRetourPrevue = scanner.nextLine();
                        if (!nouvelleDateRetourPrevue.isEmpty()) {
                            try {
                                LocalDate retourPrevue = LocalDate.parse(nouvelleDateRetourPrevue);  // Vérifier que la date est valide
                                if (retourPrevue.isBefore(LocalDate.now())) {
                                    System.out.println("Erreur : La date de retour prévue doit être postérieure ou égale à aujourd'hui.");
                                    nouvelleDateRetourPrevue = null;  // Annuler la modification
                                }
                            } catch (java.time.format.DateTimeParseException e) {
                                System.out.println("Erreur : Le format de la date de retour prévue est invalide.");
                                nouvelleDateRetourPrevue = null;  // Annuler la modification
                            }
                        }

                        // Demander la nouvelle date de retour effective
                        System.out.print("Nouvelle date de retour effective (YYYY-MM-DD) ou appuyez sur Entrée pour ne pas modifier : ");
                        nouvelleDateRetourEffective = scanner.nextLine();
                        if (!nouvelleDateRetourEffective.isEmpty()) {
                            try {
                                LocalDate.parse(nouvelleDateRetourEffective);  // Vérifier que la date est valide
                            } catch (java.time.format.DateTimeParseException e) {
                                System.out.println("Erreur : Le format de la date de retour effective est invalide.");
                                nouvelleDateRetourEffective = null;  // Annuler la modification
                            }
                        }

                        // Appeler la méthode pour modifier l'emprunt
                        try {
                            bibliotheque.modifierEmprunt(idEmprunt, nouvelleDateRetourPrevue, nouvelleDateRetourEffective);
                            System.out.println("models.Emprunt modifié avec succès.");
                        } catch (SQLException e) {
                            System.err.println("Erreur lors de la modification de l'emprunt : " + e.getMessage());
                        }
                        break;

                    case "14":
                        System.out.print("ID de l'emprunt : ");
                        idEmprunt = scanner.nextInt();
                        System.out.print("Date de retour effective (YYYY-MM-DD) : ");
                        scanner.nextLine(); // Consommer la ligne restante
                        String dateRetourEffective = scanner.nextLine();
                        bibliotheque.retournerLivre(idEmprunt, dateRetourEffective);
                        break;

                    case "15":
                        System.out.print("ID de l'emprunt : ");
                        int empruntId = scanner.nextInt();
                        long penalite = bibliotheque.calculerPenalite(empruntId);
                        System.out.println(penalite > 0 ? "Pénalité : " + penalite + " F CFA" : "Aucune pénalité.");
                       break;

                    case "16":
                        System.out.println("Merci d'avoir utilisé la bibliothèque !");
                        scanner.close();
                        return;

                    default:
                        System.out.println("Option invalide. Veuillez réessayer.");
                }
            } catch (SQLException e) {
                System.err.println("Erreur SQL : " + e.getMessage());
            }
        }
    }
}
