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

    // when user search a playlist by title/user, query a (short) list of candidate playlists
    public List<Playlist> findByNomContaining(String partNom, Pageable pageable);

    @Query("SELECT pl FROM Playlist pl WHERE pl.utilisateur.pseudo LIKE CONCAT('%', ?1 , '%')")
    public List<Playlist> findByUtilisateurContaining(String partUserNom, Pageable pageable);

    public List<Playlist> findByEtiquette(Tag tag, Pageable pageable);


    public Optional<Playlist> findByNom(String nom);

    public List<Playlist> findByUtilisateur(Utilisateur user, Pageable pageable);



}
