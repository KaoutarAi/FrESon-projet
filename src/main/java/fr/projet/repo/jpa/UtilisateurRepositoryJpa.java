package fr.projet.repo.jpa;

import java.util.ArrayList;
import java.util.List; 
import java.util.Optional;

import fr.projet.model.utilisateur.Utilisateur;
import fr.projet.repo.IUtilisateurRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;

public class UtilisateurRepositoryJpa extends AbstractRepositoryJpa implements IUtilisateurRepository{
	
	@Override
	public Optional<Utilisateur> findByPseudo(String pseudo) {
		try (EntityManager em = emf.createEntityManager()) {
			return Optional.ofNullable(
				em	.createQuery("select u from Utilisateur u where u.pseudo = ?1", Utilisateur.class)
					.setParameter(1, pseudo)
					.getSingleResult()
			);
		}
		catch (NoResultException e) {
			System.out.println("Ce pseudo n'existe pas!");
			return Optional.empty();
		}
		catch (Exception ex) {
			ex.printStackTrace();
			return Optional.empty();
		}
	}

	@Override
	public List<Utilisateur> findAll() {
		try (EntityManager em = emf.createEntityManager()) {
			return em
				.createQuery("select u from Utilisateur u", Utilisateur.class)
				.getResultList();
		}
		
		catch (Exception ex) {
			ex.printStackTrace();
			return new ArrayList<>();
		}
	}

	@Override
	public Optional<Utilisateur> findById(Integer id) {
		try (EntityManager em = emf.createEntityManager()) {
			return Optional.ofNullable(em.find(Utilisateur.class, id));
		}
		
		catch (Exception ex) {
			ex.printStackTrace();
			return Optional.empty();
		}
	}

	@Override
	public Utilisateur save(Utilisateur entity) {
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
	public void deleteById(Integer id) {
		try (EntityManager em = emf.createEntityManager()) {
			em.getTransaction().begin();
			
			try {
				em	.createQuery("delete from Utilisateur u where u.id = ?1")
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
	public Optional<Utilisateur> findByPseudoRole(String pseudo, String role) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}


}
