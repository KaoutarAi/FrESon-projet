package fr.projet.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.projet.model.utilisateur.Utilisateur;

public interface IUtilisateurRepository extends JpaRepository<Utilisateur, Integer>{
	public Optional<Utilisateur> findByPseudo(String pseudo);
	public Optional<Utilisateur> findByPseudoRole(String pseudo, String role);
}
