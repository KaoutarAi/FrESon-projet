package fr.projet;

import fr.projet.factory.RepositoryFactory;
import fr.projet.repo.IMusiqueRepository;

public class Application {

	public static void main(String[] args) {
        // tests musique
        IMusiqueRepository repoMusique = RepositoryFactory.creaMusiqueRepository();

        repoMusique.findAll()
                   .stream()
                   .forEach(mus -> {System.out.println(mus.getId() + " - " + mus.getTitre());});
	}

}
