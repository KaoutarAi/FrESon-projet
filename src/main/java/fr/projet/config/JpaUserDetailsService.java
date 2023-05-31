package fr.projet.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import fr.projet.model.utilisateur.Utilisateur;
import fr.projet.repo.IUtilisateurRepository;

@Service
public class JpaUserDetailsService implements UserDetailsService {
	@Autowired
	private IUtilisateurRepository repoUtilisateur;

	@Override
	public UserDetails loadUserByUsername(String pseudo) throws UsernameNotFoundException {
		Utilisateur utilisateur = this.repoUtilisateur.findByPseudo(pseudo)
				.orElseThrow(() -> new UsernameNotFoundException("L'utilisateur n'existe pas."));

		return User.withUsername(pseudo).password(utilisateur.getMdp()).roles(utilisateur.getRole().toString()).build();
	}

}
