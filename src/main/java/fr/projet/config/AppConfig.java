package fr.projet.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("fr.formation")
@PropertySource("classpath:/jpa_postgres.properties")
public class AppConfig {
	
}
