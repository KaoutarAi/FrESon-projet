package fr.projet.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import fr.projet.model.musique.Album;

public interface IAlbumRepository extends JpaRepository<Album, Integer>{
    @Query("SELECT a FROM Album a LEFT JOIN FETCH a.musiques m ")
    public List<Album> findAllFetchMusiques();

    @Query("SELECT a FROM Album a LEFT JOIN FETCH a.musiques m WHERE a.id = ?1")
    public Optional<Album> findByIdFetchMusiques(int id);
}
