package fr.projet.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import fr.projet.enums.Tag;
import fr.projet.model.musique.Playlist;
import fr.projet.model.utilisateur.Utilisateur;

public interface IPlaylistRepository extends JpaRepository<Playlist, Integer> {

    @Query("SELECT p FROM Playlist p LEFT JOIN FETCH p.musiques m ")
    public List<Playlist> findAllFetchMusiques();

    @Query("SELECT p FROM Playlist p LEFT JOIN FETCH p.musiques m WHERE p.id = ?1")
    public Optional<Playlist> findByIdFetchMusiques(int id);

    // when user search a playlist by title/user, query a (short) list of candidate playlists
    public List<Playlist> findByNomContaining(String partNom, Pageable pageable);

    @Query("SELECT pl FROM Playlist pl WHERE pl.utilisateur.pseudo LIKE CONCAT('%', ?1 , '%')")
    public List<Playlist> findByUtilisateurContaining(String partUserNom, Pageable pageable);

    public List<Playlist> findByEtiquette(Tag tag, Pageable pageable);


    public Optional<Playlist> findByNom(String nom);

    public List<Playlist> findByUtilisateur(Utilisateur user, Pageable pageable);

    // @Query(
    //     value = "SELECT * FROM playlists\n" + //
    //                     "JOIN\n" + //
    //                     "    (SELECT abo_playlist_id, COUNT(*) as nbAbo\n" + //
    //                     "        FROM abonnements\n" + //
    //                     "        GROUP BY abo_playlist_id\n" + //
    //                     "        ORDER BY nbAbo DESC) AS abonnes\n" + //
    //                     "ON playlist_id = abo_playlist_id;",
    //     nativeQuery = true)
    @Query("select p from Playlist p order by size(p.abonnes) desc")
    public List<Playlist> findAllByOrderNbAbo();

    @Query("select p from Playlist p order by size(p.abonnes) desc")
    public List<Playlist> findTopByNbAbo(Pageable pageable);

    // @Query(value = "SELECT\n" + //
    //                 "    l.*,\n" + //
    //                 "    p.*\n" + //
    //                 "FROM\n" + //
    //                 "    playlists p\n" + //
    //                 "INNER JOIN\n" + //
    //                 "    loggings l\n" + //
    //                 "    ON l.log_infos LIKE 'creation Playlist - id=' || CAST(p.playlist_id AS VARCHAR(255)) || ' %'\n" + //
    //                 "ORDER BY l.log_date DESC",
    //             nativeQuery = true)
    @Query("select p from Playlist p, Logging l WHERE  l.text LIKE CONCAT('creation Playlist - id=', CAST(p.id AS string), ' %') ORDER BY l.date DESC")
    public List<Playlist> findAllByCreationDateDesc();

    @Query("select p from Playlist p, Logging l WHERE  l.text LIKE CONCAT('creation Playlist - id=', CAST(p.id AS string), ' %') ORDER BY l.date DESC")
    public List<Playlist> findTopByCreationDate(Pageable pageable);


}
