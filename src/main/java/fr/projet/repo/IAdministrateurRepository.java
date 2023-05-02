package fr.projet.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.projet.model.utilisateur.Administrateur;

public interface IAdministrateurRepository extends JpaRepository<Administrateur, Integer>{

}
