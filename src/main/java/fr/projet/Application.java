package fr.projet;

import java.util.List;
import java.util.Optional;

import fr.projet.factory.RepositoryFactory;
import fr.projet.model.utilisateur.Utilisateur;
import fr.projet.repo.IUtilisateurRepository;

public class Application {

	public static void main(String[] args) {

		int choixMenu = 0;

		do {
			choixMenu = Menu.print();

			switch (choixMenu) {
			case 1:
				inscription();
				break;
			case 3:
				listerUtilisateurs();
				break;
			case 4:
				chercherByUserId();
				break;
			case 6:
				modifierPassword();
				break;
			case 7:
				supprimerUtilisateur();
				break;

			}
		} while (choixMenu != 0);

		Saisie.sc.close();

	}

	public static void inscription() {
		IUtilisateurRepository repoUtilisateur = RepositoryFactory.createUtilisateurRepository();

		String pseudo = Saisie.next("Pseudo :");

		Optional<Utilisateur> optUtilisateur = repoUtilisateur.findByPseudo(pseudo);

		if (optUtilisateur.isPresent()) {
			while (pseudo.equals(optUtilisateur.get().getPseudo())) {
				System.out.println("Ce pseudo existe deja !");
				pseudo = Saisie.next("Pseudo :");
			}
		}

		Utilisateur new_user = new Utilisateur();
		new_user.setPseudo(pseudo);

		String nom = Saisie.next("Nom :");
		String prenom = Saisie.next("Prenom :");
		String email = Saisie.next("Email :");

		String mdp = Saisie.next("Mot de passe :");

		while (isPasswordFort(mdp) == false) {
			mdp = Saisie.next("Saisir mot de passe :");
		}
		new_user.setMdp(mdp);
		new_user.setNom(nom);
		new_user.setPrenom(prenom);
		new_user.setEmail(email);

		repoUtilisateur.save(new_user);

		System.out.println("Indcription valide...");
	}

	public static boolean isPasswordFort(String password) {
		// Vérifie si le mot de passe est suffisamment long (au moins 8 caractères)
		if (password.length() < 8) {
			System.out.println("mdp faible! veuillez entrer au mois 8 caracteres");
			return false;
		}

		// Vérifie si le mot de passe contient des caractères spéciaux
		if (!password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*")) {
			System.out.println("mdp faible! veuillez entrer au mois un caractere special");
			return false;
		}

		// Vérifie si le mot de passe contient des chiffres
		if (!password.matches(".*\\d.*")) {
			System.out.println("mdp faible! veuillez entrer au mois un chiffre");
			return false;
		}

		// Vérifie si le mot de passe contient des lettres majuscules et minuscules
		if (!password.matches(".*[a-z].*") || !password.matches(".*[A-Z].*")) {
			System.out.println("mdp faible! veuillez entrer des minuscules et majuscules");
			return false;
		}

		return true;
	}

	public static void listerUtilisateurs() {
		IUtilisateurRepository repoUtilisateur = RepositoryFactory.createUtilisateurRepository();
		List<Utilisateur> utilisateurs = repoUtilisateur.findAll();
		utilisateurs.forEach(u -> System.out.println("- " + u.getPseudo()));
	}

	public static void chercherByUserId() {
		IUtilisateurRepository repoUtilisateur = RepositoryFactory.createUtilisateurRepository();
		int id = Saisie.nextInt("id :");
		Optional<Utilisateur> optUtilisateur = repoUtilisateur.findById(id);
		if (optUtilisateur.isPresent()) {
			System.out.println("- " + optUtilisateur.get().getPseudo());
		}

	}
	
	public static void modifierPassword() {
		IUtilisateurRepository repoUtilisateur = RepositoryFactory.createUtilisateurRepository();
		int id = Saisie.nextInt("id de l'utilisateur à modifier:");
		String mdp = Saisie.next("nouveau mot de passe :");
		Optional<Utilisateur> optUtilisateur = repoUtilisateur.findById(id);
		if (optUtilisateur.isPresent()) {
			while (isPasswordFort(mdp) == false) {
				mdp = Saisie.next("nouveau mot de passe :");
			}
			optUtilisateur.get().setMdp(mdp);
			repoUtilisateur.save(optUtilisateur.get());
			System.out.println("mot de passe modifié ");
		}
	}
	
	public static void supprimerUtilisateur() {
		IUtilisateurRepository repoUtilisateur = RepositoryFactory.createUtilisateurRepository();
		int id = Saisie.nextInt("id de l'utilisateur à supprimer:");
		repoUtilisateur.deleteById(id);
		System.out.println("utilisateur supprimé");
	}
}
