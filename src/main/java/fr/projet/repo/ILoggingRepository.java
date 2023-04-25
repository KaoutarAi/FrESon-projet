package fr.projet.repo;

import java.util.List;

import fr.projet.model.logging.Logging;

public interface ILoggingRepository extends IRepository<Logging, Integer>{
	public List<Logging> findByDate(int annee, int mois, int jour);

}
