package fr.projet.model.utilisateur;

import java.time.LocalDateTime;

import fr.projet.model.musique.Playlist;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="commentaire")
public class Commentaire {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="com_id")
	private int id;
	
	@Column(name="com_content", length=255, nullable=false)
	private String contenu;
	
	@Column(name = "com_date", nullable = false)
	private LocalDateTime date;
	
	@ManyToOne
	@JoinColumn(name = "com_user_id", nullable = false)
	private Utilisateur utilisateur;
	
	@ManyToOne
	@JoinColumn(name = "com_playlist_id", nullable = false)
	private Playlist playlist;

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

	public Utilisateur getUtilisateur() {
		return utilisateur;
	}

	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}

	public Playlist getPlaylist() {
		return playlist;
	}

	public void setPlaylist(Playlist playlist) {
		this.playlist = playlist;
	}
	

}
