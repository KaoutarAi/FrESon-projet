package fr.projet.api.logging.request;

import fr.projet.api.utilisateur.response.UtilisateurResponse;
import jakarta.validation.constraints.NotBlank;

public class LoggingRequest {
	@NotBlank
	private String text;
	private UtilisateurResponse utilisateurResponse;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public UtilisateurResponse getUtilisateurResponse() {
		return utilisateurResponse;
	}

	public void setUtilisateurResponse(UtilisateurResponse utilisateurResponse) {
		this.utilisateurResponse = utilisateurResponse;
	}

}
