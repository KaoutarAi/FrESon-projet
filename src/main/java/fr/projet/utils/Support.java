package fr.projet.utils;

import java.security.SecureRandom;

public class Support {
	public String recuperationMotDePasse(String email) {
		String newMdP = new SecureRandom()
				.ints(10, 33, 122)
				.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
				.toString();
		
		return newMdP;
	}
	
	public void changerMotDePasse() {
		
	}
}
