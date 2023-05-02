package fr.projet.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.projet.model.musique.Musique;

public interface IMusiqueRepository extends JpaRepository<Musique, Integer>{

}
