package fr.projet.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import fr.projet.model.utilisateur.Utilisateur;

public interface IUtilisateurRepository extends JpaRepository<Utilisateur, Integer> {

	public Optional<Utilisateur> findByPseudo(String pseudo);
	public Optional<Utilisateur> findByPseudoAndRole(String pseudo, String role);
	public List<Utilisateur> findAll();
	public List<Utilisateur> findAllByRole(String role);
	public Utilisateur save(Utilisateur entity);
	public void deleteById(Integer id);
	@Transactional
	public void deleteByPseudo(String pseudo);

	@Query("select u from Utilisateur u where u.role = 'CREATEUR' or u.role = 'UTILISATEUR'")
	public List<Utilisateur> findAllByRoles();

}
