package fr.projet.model.utilisateur;

import java.util.List;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import fr.projet.enums.Role;
import fr.projet.model.logging.Logging;
import fr.projet.model.musique.Playlist;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "utilisateur")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "user_role")
@DiscriminatorValue("UTILISATEUR")
public class Utilisateur {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private int id;

	@Column(name = "user_surname", length = 100, nullable = false)
	private String nom;

	@Column(name = "user_name", length = 100, nullable = false)
	private String prenom;

	@Column(name = "user_pseudo", length = 100, unique = true, nullable = false)
	private String pseudo;

	@Column(name = "user_email", length = 100, nullable = false)
	private String email;

	@Column(name = "user_password", length = 100, nullable = false)
	private String mdp;

	@Column(name = "user_role", insertable = false, updatable = false)
	private String role;

	@OneToMany(mappedBy = "utilisateur")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private List<Logging> loggings;

	@OneToMany(mappedBy = "utilisateur")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private List<Commentaire> commentaires;

	@ManyToMany
	@JoinTable(
			name = "abonnements",
			joinColumns = @JoinColumn(name = "abo_user_id"),
			inverseJoinColumns = @JoinColumn(name = "abo_playlist_id")
			)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private List<Playlist> abonnements;


	@OneToMany (mappedBy = "utilisateur")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private List<Playlist> playlists;



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

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

	public String getMdp() {
		return mdp;
	}

	public void setMdp(String mdp) {
		this.mdp = mdp;
	}
	

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public List<Logging> getLoggings() {
		return loggings;
	}

	public void setLoggings(List<Logging> loggings) {
		this.loggings = loggings;
	}

	public List<Playlist> getPlaylists() {
		return playlists;
	}

	public void setPlaylists(List<Playlist> playlists) {
		this.playlists = playlists;
	}

	public List<Commentaire> getCommentaires() {
		return commentaires;
	}

	public void setCommentaires(List<Commentaire> commentaires) {
		this.commentaires = commentaires;
	}

	public List<Playlist> getAbonnements() {
		return abonnements;
	}

	public void setAbonnements(List<Playlist> abonnements) {
		this.abonnements = abonnements;
	}


}
