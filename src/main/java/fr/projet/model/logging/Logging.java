package fr.projet.model.logging;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import fr.projet.model.utilisateur.Utilisateur;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "loggings")
public class Logging {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "log_id")
	private int id;
	
	@ManyToOne
	@JoinColumn(name = "log_user_id", nullable = false)
	private Utilisateur utilisateur;

	@Column(name = "log_infos", length = 255, nullable = false)
	private String text;

	@Column(name = "log_date", nullable = false)
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

	public void setUtilisateur(Utilisateur user) {
		this.utilisateur = user;
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

	@Override
	public String toString() {
		DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy_HH:mm");
		return "Log #" + this.id + " - " + this.getDate().format(format) + " - " + this.text + " - " + "Utilisateur : "
				+ this.utilisateur.getId();
	}

}
