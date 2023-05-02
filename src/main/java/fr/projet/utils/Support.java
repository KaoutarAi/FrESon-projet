package fr.projet.utils;

import java.security.SecureRandom;

import org.springframework.beans.factory.annotation.Autowired;

public class Support {
	@Autowired
	private InscriptionChecks check;
	
	public String recuperationMotDePasse(String email) {
		
		String newMdP = new SecureRandom()
				.ints(10, 33, 122)
				.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
				.toString();
		
		return newMdP;
		// et redirection à la page de connexion
	}
	
	public void changerMotDePasse(String newMdP) {
		if (check.isValidPassword(newMdP)) {
//			Utilisateur user = trouver l'utilisateur et réaffecter toutes ses infos
//			user.setMdp(newMdP);
//			repoUser.save(); // à voir où placer cette méthode et comment récupérer l'entité complète
		}  //pas vraiment utile pour le moment mais à voir une fois qu'on commencera l'implementation web
	}
}
