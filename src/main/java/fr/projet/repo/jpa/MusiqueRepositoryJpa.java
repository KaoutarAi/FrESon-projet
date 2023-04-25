package fr.projet.repo.jpa;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import fr.projet.model.musique.Musique;
import fr.projet.repo.IMusiqueRepository;
import jakarta.persistence.EntityManager;

public class MusiqueRepositoryJpa extends AbstractRepositoryJpa implements IMusiqueRepository {

    @Override
    public Musique save(Musique entity) {
        try (EntityManager em = emf.createEntityManager()) {
			em.getTransaction().begin();

			try {
				if (entity.getId() == 0) {
					em.persist(entity);
				}

				else {
					entity = em.merge(entity);
				}

				em.getTransaction().commit();
			}

			catch (Exception ex) {
				ex.printStackTrace();
				em.getTransaction().rollback();
			}
		}

		catch (Exception ex) {
			ex.printStackTrace();
		}

		return entity;
    }

    @Override
    public List<Musique> findAll() {
		try (EntityManager em = emf.createEntityManager()) {
			return em
				.createQuery("select c from Client c", Musique.class)
				.getResultList();
		}
		catch (Exception ex) {
			ex.printStackTrace();
			return new ArrayList<>();
		}
    }

    @Override
    public void deleteById(Integer id) {
		try (EntityManager em = emf.createEntityManager()) {
			em.getTransaction().begin();

			try {
				em	.createQuery("DELETE FROM Musique m where m.id = ?1")
					.setParameter(1, id)
					.executeUpdate();

				em.getTransaction().commit();
			}
			catch (Exception ex) {
				ex.printStackTrace();
				em.getTransaction().rollback();
			}
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
    }

	@Override
	public Optional<Musique> findById(Integer id) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

}
