package fr.projet.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.projet.model.utilisateur.Utilisateur;
import fr.projet.repo.IUtilisateurRepository;

@Service
public class InscriptionChecks {
	
	@Autowired
	private IUtilisateurRepository repoUser;
	
	public boolean isValidPassword(String mdp) {
		String regex = """
				^(?=.*[0-9])
				(?=.*[a-z])
				(?=.*[A-Z])
				(?=.*[@#$%^&-+=()])
				(?=\\S+$)
				.{8,20}$
				""";
		Pattern p = Pattern.compile(regex);
		if (mdp == null) {
			return false;
		}

		Matcher m = p.matcher(mdp);

		return m.matches();

	}

	public boolean isValidEmail(String email) {
		String regex = "^[a-zA-Z0-9_!#$%&'\\*+/=?{|}~^.-]+@[a-zA-Z0-9.-]+$";

		Pattern p = Pattern.compile(regex);
		if (email == null) {
			return false;
		}
		Matcher m = p.matcher(email);

		return m.matches();
	}
	
	public boolean isValidPseudo(String pseudo) {
		if(pseudo == null || pseudo.length() < 5 || repoUser.findByPseudo(pseudo).isPresent()) {
			return false;
			
		}
		return true;
	}
}
