package fr.projet.api.utilisateur;

import java.util.Optional;

import org.springframework.web.bind.annotation.RequestHeader;

import fr.projet.config.jwt.JwtUtil;

public class UtilisateurConnecte {
	
	//recupérer le pseudo de l'utilisateur connecté
    public static String getPseudo(@RequestHeader("Authorization") String token) {
        String jwtToken = token.substring(7);
        String pseudo = null;

        Optional<String> optPseudo = JwtUtil.getUsername(jwtToken);
        if (optPseudo.isPresent()) {
			pseudo = optPseudo.get();
        }
        return pseudo;
    }

}
