package fr.projet.api.logging.request;

import fr.projet.model.utilisateur.Utilisateur;
import jakarta.validation.constraints.NotBlank;

public class LoggingRequest {
	@NotBlank
	private String text;
	private Utilisateur utilisateur;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Utilisateur getUtilisateur() {
		return utilisateur;
	}

	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}

}
