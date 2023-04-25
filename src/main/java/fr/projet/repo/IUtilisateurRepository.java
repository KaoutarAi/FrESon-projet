package fr.projet.repo;

import java.util.Optional;

import fr.projet.model.utilisateur.Utilisateur;

public interface IUtilisateurRepository extends IRepository<Utilisateur, Integer>{
	public Optional<Utilisateur> findByPseudo(String pseudo);
	public Optional<Utilisateur> findByPseudoRole(String pseudo, String role);
}
