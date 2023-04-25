package fr.projet.factory;

import fr.projet.repo.IAdministrateurRepository;
import fr.projet.repo.ICreateurRepository;
import fr.projet.repo.IModerateurRepository;
import fr.projet.repo.IUtilisateurRepository;
import fr.projet.repo.jpa.AdministrateurRepositoryJpa;
import fr.projet.repo.jpa.CreateurRepositoryJpa;
import fr.projet.repo.jpa.ModerateurRepositoryJpa;
import fr.projet.repo.jpa.UtilisateurRepositoryJpa;

public class RepositoryFactory {
	public static IUtilisateurRepository createUtilisateurRepository() {
		return new UtilisateurRepositoryJpa();
	}
	
	public static ICreateurRepository createCreateurRepository() {
		return new CreateurRepositoryJpa();
	}
	
	public static IModerateurRepository createModerateurRepository() {
		return new ModerateurRepositoryJpa();
	}
	
	public static IAdministrateurRepository createAdministrateurRepository() {
		return new AdministrateurRepositoryJpa();
	}
}
