package fr.projet.config.jwt;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import fr.projet.model.utilisateur.Utilisateur;
import fr.projet.repo.IUtilisateurRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtHeaderAuthorizationFilter extends OncePerRequestFilter {

	@Autowired
	private IUtilisateurRepository repoUtilisateur;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String authHeader = request.getHeader("Authorization");
//		System.out.println(authHeader);
		String token = null;

		if (authHeader != null) {
			token = authHeader.substring(7); // on retire Bearer qui fait 7 caractères
		}

		Optional<String> optUsername = JwtUtil.getUsername(token);

		// Si on a le nom d'utilisateur, alors le jeton est valide
		if (optUsername.isPresent()) {
			String username = optUsername.get();
			Optional<Utilisateur> optUtilisateur = this.repoUtilisateur.findByPseudo(username);

			// Si on a l'utilisateur on peut l'authentifier
			if (optUtilisateur.isPresent()) {
				// s'il est présent c'est qu'il existe en BDD

				Utilisateur utilisateur = optUtilisateur.get();

				// Simuler la connexion grace au jetons

				// Créer la liste des roles
				List<GrantedAuthority> authorities = new ArrayList<>();

				authorities.add(new SimpleGrantedAuthority("ROLE_" + utilisateur.getRole()));

				// recréer un nouvel authentication
				Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, authorities);

				// injecter cet authentication dans le contexte de sécurité
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}

		}

		// important pour passer à la suite
		filterChain.doFilter(request, response);

	}

}
