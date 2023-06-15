package fr.projet.api.utilisateur.response;

import org.springframework.beans.BeanUtils;

import fr.projet.model.utilisateur.Utilisateur;

public class UtilisateurResponse {
	private int id;
	private String pseudo;
	private String email;
	private String role;


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPseudo() {
		return pseudo;
	}

	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}


	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public static UtilisateurResponse convert(Utilisateur utilisateur) {
		UtilisateurResponse response = new UtilisateurResponse();

		BeanUtils.copyProperties(utilisateur, response);
		
		return response;
	}
}

