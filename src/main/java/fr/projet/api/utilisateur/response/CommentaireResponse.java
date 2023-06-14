package fr.projet.api.utilisateur.response;


import java.time.LocalDateTime;

import org.springframework.beans.BeanUtils;

import fr.projet.model.utilisateur.Commentaire;
import fr.projet.model.utilisateur.Utilisateur;

public class CommentaireResponse {
	private int id;
	private String contenu;
	private LocalDateTime date;
	private Utilisateur utilisateur;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getContenu() {
		return contenu;
	}
	public void setContenu(String contenu) {
		this.contenu = contenu;
	}
	public LocalDateTime getDate() {
		return date;
	}
	
	public void setDate(LocalDateTime date) {
		this.date = date;
	} 
	
	
	public UtilisateurResponse getUtilisateur() {
		return UtilisateurResponse.convert(utilisateur);
	}
	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}
	public static CommentaireResponse convert(Commentaire commentaire) {
		CommentaireResponse response = new CommentaireResponse();

		BeanUtils.copyProperties(commentaire, response);
		
		return response;
	}
	
	

}
