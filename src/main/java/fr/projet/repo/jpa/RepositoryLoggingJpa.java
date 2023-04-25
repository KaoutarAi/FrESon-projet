package fr.projet.repo.jpa;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import fr.projet.model.logging.Logging;
import fr.projet.repo.ILoggingRepository;
import jakarta.persistence.EntityManager;

public class RepositoryLoggingJpa extends AbstractRepositoryJpa implements ILoggingRepository {

	@Override
	public List<Logging> findAll() {
		try(EntityManager em = emf.createEntityManager()) {
			return em
				.createQuery("select l from Logging l", Logging.class)
				.getResultList();
		}
	}

	@Override
	public Optional<Logging> findById(Integer id) {
		try (EntityManager em = emf.createEntityManager()) {
			return Optional.ofNullable(em.find(Logging.class, id));
		} catch (Exception e) {
			e.printStackTrace();
			return Optional.empty();
		}
	}

	@Override
	public Logging save(Logging entity) {
		try (EntityManager em = emf.createEntityManager()) {
			em.getTransaction().begin();
			try {
				if (entity.getId() == 0) {
					em.persist(entity);
				} else {
					entity = em.merge(entity);
				}
				em.getTransaction().commit();
			} catch (Exception e) {
				e.printStackTrace();
				em.getTransaction().rollback();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return entity;
	}

	@Override
	public void deleteById(Integer id) {
		try (EntityManager em = emf.createEntityManager()) {
			em.getTransaction().begin();
			try {
				em.remove(em.find(Logging.class, id));
				em.getTransaction().commit();
			} catch (Exception e) {
				em.getTransaction().rollback();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public List<Logging> findByDate(int annee, int mois, int jour) {
		try(EntityManager em = emf.createEntityManager()) {
			//Construire une variable de type LocalDate à partir des années, mois & jour
			// utiliser les fonctions YEAHR(), MONTH(), DAY() ou date_part()
			return em
				.createQuery("select l from Logging l where year(l.date) = ?1 and month(l.date) = ?2 and day(l.date) = ?3", Logging.class)
				.setParameter(1, annee)
				.setParameter(2, mois)
				.setParameter(3, jour)
				.getResultList();
			
		}
		catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}

}
