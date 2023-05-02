package fr.projet.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.projet.model.utilisateur.Createur;

public interface ICreateurRepository extends JpaRepository<Createur, Integer>{
	
}
