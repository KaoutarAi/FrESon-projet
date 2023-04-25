package fr.projet;

public class Menu {

	public static int print() {
		System.out.println("--------------------------");

		System.out.println("-- 1. Inscription (creer utilisateur)");
		System.out.println("-- 2. Se connecter");
		System.out.println("-- 3. Lister les utilisateurs");
		System.out.println("-- 4. Chercher un utilisateur par son id");
		System.out.println("-- 5. Lister les utilisateur par role");
		System.out.println("-- 6. Modifier mot de passe");
		System.out.println("-- 7. Supprimer un utilisateur par son id");


		System.out.println("-- 0. Quitter");

		System.out.println("--------------------------");

		return Saisie.nextInt("Choisir le menu");
	}

}
