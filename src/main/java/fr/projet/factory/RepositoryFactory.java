package fr.projet.factory;

import fr.projet.repo.IMusiqueRepository;
import fr.projet.repo.jpa.MusiqueRepositoryJpa;

public class RepositoryFactory {
    public static IMusiqueRepository creaMusiqueRepository() {
        return new MusiqueRepositoryJpa();
    }
}
