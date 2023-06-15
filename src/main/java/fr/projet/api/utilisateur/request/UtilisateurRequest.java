package fr.projet.api.utilisateur.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;

public class UtilisateurRequest {
	
	private String nom;
	
	private String prenom;
	
	@Email
	private String email;
	
	
	@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^\\da-zA-Z]).{8,}$")
	private String mdp;
	
	private String mdpVerif;


	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}


	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMdp() {
		return mdp;
	}

	public void setMdp(String mdp) {
		this.mdp = mdp;
	}

	public String getMdpVerif() {
		return mdpVerif;
	}

	public void setMdpVerif(String mdpVerif) {
		this.mdpVerif = mdpVerif;
	}
	
	
}
