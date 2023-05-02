package fr.projet.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import fr.projet.model.musique.Musique;

public interface IMusiqueRepository extends JpaRepository<Musique, Integer>{
    // when user search a music by title/artist/album, query a (short) list of candidate musics
    public List<Musique> findByTitreContaining(String partTitle, Pageable pageable);

    public List<Musique> findByArtisteContaining(String partArtiste, Pageable pageable);

    @Query("SELECT msc FROM Musique msc WHERE msc.album.nom LIKE CONCAT('%', ?1, '%')")
    public List<Musique> findByAlbumContaining(String partAlbum, Pageable pageable);

    // search a music by exact title/artist/album
    public Optional<Musique> findByTitre(String title);

    public Optional<Musique> findByArstite(String artiste);

    @Query("SELECT msc FROM Musique msc WHERE msc.album.nom = ?1")
    public Optional<Musique> findByAlbum(String album);

    // used for recommendation (most streamed)
    public List<Musique> findAllByOrderByNbPlaysDesc(Pageable pageable);







}
