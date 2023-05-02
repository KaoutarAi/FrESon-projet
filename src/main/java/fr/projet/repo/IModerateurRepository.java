package fr.projet.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.projet.model.utilisateur.Moderateur;

public interface IModerateurRepository extends JpaRepository<Moderateur, Integer>{
	
}
