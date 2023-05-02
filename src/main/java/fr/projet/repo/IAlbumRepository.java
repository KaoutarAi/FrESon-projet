package fr.projet.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.projet.model.musique.Album;

public interface IAlbumRepository extends JpaRepository<Album, Integer>{


}
