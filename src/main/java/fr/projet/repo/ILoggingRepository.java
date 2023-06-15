package fr.projet.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import fr.projet.model.logging.Logging;



public interface ILoggingRepository extends JpaRepository<Logging, Integer>{
	@Query("SELECT l FROM Logging l WHERE EXTRACT (year FROM l.date) = ?1 AND EXTRACT (month FROM l.date) = ?2 AND EXTRACT (day FROM l.date) = ?3")
	public List<Logging> findByDate(int annee, int mois, int jour);
	
	@Query("SELECT l FROM Logging l WHERE l.text LIKE %?1%")
	public List<Logging> findByInfo(String info);
	
	@Query("SELECT l FROM Logging l  WHERE l.utilisateur.pseudo LIKE %?1% ")
	public List<Logging> findByPseudo(String pseudo);
	
	@Query("SELECT l FROM Logging l WHERE EXTRACT (year FROM l.date) = ?1 AND EXTRACT (month FROM l.date) = ?2")
	public List<Logging> findByMonth(int annee, int mois);
	
	@Query("SELECT l FROM Logging l WHERE EXTRACT (year FROM l.date) = ?1")
	public List<Logging> findByYear(int annee);
	
	@Query("SELECT l FROM Logging l WHERE l.utilisateur.id = ?1")
	public List<Logging> findByUtilisateurId(int id);
}
