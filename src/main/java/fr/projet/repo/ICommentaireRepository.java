package fr.projet.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import fr.projet.model.utilisateur.Commentaire;

public interface ICommentaireRepository extends JpaRepository<Commentaire, Integer>{
	
	@Query("select c from Commentaire c where c.playlist.id = ?1")
	public List<Commentaire> findAllByPlaylist(int playlistId);
	
	@Query("select c from Commentaire c where c.playlist.id = ?1 and c.contenu like %?2%")
	public List<Commentaire> findAllByPlaylistAndContenuContaining(int playlistId, String contenu);
	
	@Query("select c from Commentaire c where c.playlist.id = ?1 and c.utilisateur.pseudo = ?2 and c.contenu like %?3%")
	public List<Commentaire> findAllByPlaylistAndUtilisateurAndContenuContaining(
			int playlistId, String pseudo, String contenu);
	
	@Query("select c from Commentaire c where c.playlist.id = ?1 and c.utilisateur.pseudo = ?2 and extract (year from c.date) = ?3 and extract (month from c.date) = ?4 and extract (day from c.date) = ?5 and extract (hour from c.date) = ?6 and extract (minute from c.date) = ?7")
	public List<Commentaire> findAllByPlaylistAndUtilisateurAndDate(int playlistId, String pseudo, int annee, int mois, int jour, int heure, int minute);
	
	@Query("select c from Commentaire c where c.playlist.id = ?1 and c.utilisateur.pseudo = ?2 and extract (year from c.date) = ?3 and extract (month from c.date) = ?4 and extract (day from c.date) = ?5 and extract (hour from c.date) = ?6 and extract (minute from c.date) = ?7 and c.contenu like %?8%")
	public List<Commentaire> findAllByPlaylistAndUtilisateurAndDateAndContenuContaining(int playlistId, String pseudo, int annee, int mois, int jour, int heure, int minute, String contenu);

}
