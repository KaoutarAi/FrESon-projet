package fr.projet.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import fr.projet.config.jwt.JwtHeaderAuthorizationFilter;

@Configuration
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
	@Bean
	public SecurityFilterChain filterChain (HttpSecurity http, JwtHeaderAuthorizationFilter jwtFilter) throws Exception{
		http.authorizeHttpRequests(authorize -> {
			authorize.requestMatchers("/accueil", "/api/utilisateur/**", "/api/commentaire/**", "/api/musique/**", "/api/playlist/**").permitAll();

			authorize.requestMatchers("/**").authenticated();
		});
//		http.httpBasic(Customizer.withDefaults());

		http.csrf(c -> c.disable());

		http.cors(c -> {
			CorsConfigurationSource source = request -> {
				CorsConfiguration config = new CorsConfiguration();

				config.setAllowedOrigins(List.of("*"));

				config.setAllowedMethods(List.of("*"));

				config.setAllowedHeaders(List.of("*"));

				return config;
			};
			c.configurationSource(source);
		});


		http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

		http.sessionManagement(manager -> manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

		return http.build();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public static MethodSecurityExpressionHandler methodExpressionHandler(RoleHierarchy roleHierarchy) {
		DefaultMethodSecurityExpressionHandler hdlr = new DefaultMethodSecurityExpressionHandler();

		hdlr.setRoleHierarchy(roleHierarchy);

		return hdlr;
	}

	@Bean
	public RoleHierarchy roleHierachy() {
		String hierarchy = "ROLE_ADMINISTRATEUR > ROLE_MODERATEUR > ROLE_UTILISATEUR \n ROLE_CREATEUR > ROLE_UTILISATEUR";

		RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();

		roleHierarchy.setHierarchy(hierarchy);

		return roleHierarchy;
	}
}
