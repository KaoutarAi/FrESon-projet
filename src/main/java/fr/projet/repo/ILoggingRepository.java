package fr.projet.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.projet.model.logging.Logging;


public interface ILoggingRepository extends JpaRepository<Logging, Integer>{
	public List<Logging> findByDate(String annee, String mois, String jour);
}
