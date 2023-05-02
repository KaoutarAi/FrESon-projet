package fr.projet.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.projet.model.musique.Playlist;

public interface IPlaylistRepository extends JpaRepository<Playlist, Integer> {

}
