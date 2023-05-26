package fr.projet.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.projet.model.utilisateur.Commentaire;

public interface ICommentaireRepository extends JpaRepository<Commentaire, Integer>{
	
	public List<Commentaire> findByContenuContaining(String contenu);

}
