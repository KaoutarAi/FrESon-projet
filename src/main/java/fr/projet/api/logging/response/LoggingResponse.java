package fr.projet.api.logging.response;

import java.time.LocalDateTime;

import org.springframework.beans.BeanUtils;

import fr.projet.model.logging.Logging;
import fr.projet.model.utilisateur.Utilisateur;

public class LoggingResponse {
	private int id;
	private Utilisateur utilisateur;
	private String text;
	private LocalDateTime date;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Utilisateur getUtilisateur() {
		return utilisateur;
	}
	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public LocalDateTime getDate() {
		return date;
	}
	public void setDate(LocalDateTime date) {
		this.date = date;
	}
	
	public static LoggingResponse convert(Logging logging) {
		LoggingResponse response = new LoggingResponse();
		
		BeanUtils.copyProperties(logging, response);
		
		return response;
	}
}
