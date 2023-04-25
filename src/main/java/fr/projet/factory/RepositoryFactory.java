package fr.projet.factory;

import fr.projet.repo.ILoggingRepository;
import fr.projet.repo.jpa.RepositoryLoggingJpa;

public class RepositoryFactory {
	public static ILoggingRepository createLoggingRepository() {
		return new RepositoryLoggingJpa();
	}
}
