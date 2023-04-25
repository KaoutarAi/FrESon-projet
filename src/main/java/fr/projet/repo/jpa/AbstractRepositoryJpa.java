package fr.projet.repo.jpa;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public abstract class AbstractRepositoryJpa {
    protected static EntityManagerFactory emf = Persistence.createEntityManagerFactory("FrESonUnit");
}
