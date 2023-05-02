package fr.projet.model.utilisateur;

import java.util.List;

import fr.projet.model.logging.Logging;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="utilisateur")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "user_role")
@DiscriminatorValue("Utilisateur")
public class Utilisateur {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="user_id")
	private int id;
	
	@Column(name="user_surname", length=100, nullable=false)
	private String nom;
	
	@Column(name="user_name", length=100, nullable=false)
	private String prenom;
	
	@Column(name="user_pseudo", length=100, unique=true, nullable=false)
	private String pseudo;
	
	@Column(name="user_email", length=100, nullable=false)
	private String email;
	
	@Column(name="user_password", length=100, nullable=false)
	private String mdp;
	
	@Column(name= "user_role", insertable = false, updatable = false)
	private String role;
	
	@OneToMany(mappedBy = "utilisateur")
	private List<Logging> loggings;

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
	
	
	
}