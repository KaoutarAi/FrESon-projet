package fr.projet.factory;


import fr.projet.repo.ILoggingRepository;
import fr.projet.repo.jpa.RepositoryLoggingJpa;
import fr.projet.repo.IMusiqueRepository;
import fr.projet.repo.jpa.MusiqueRepositoryJpa;

public class RepositoryFactory {
	public static ILoggingRepository createLoggingRepository() {
		return new RepositoryLoggingJpa();
  }
   public static IMusiqueRepository creaMusiqueRepository() {
    return new MusiqueRepositoryJpa();
  }
}

