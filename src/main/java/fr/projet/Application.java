package fr.projet;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import fr.projet.config.AppConfig;
import fr.projet.model.logging.Logging;
import fr.projet.repo.ILoggingRepository;

public class Application {

	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
		
		ILoggingRepository repoLogging = context.getBean(ILoggingRepository.class);
		
		for(Logging l : repoLogging.findByDate(2023,04,19)) {
			System.out.println(l.getText());
		}
		System.out.println("--------------------------------");

		for(Logging l : repoLogging.findByInfo("test")) {
			System.out.println(l.getText());
		}
		System.out.println("--------------------------------");
		
		for(Logging l : repoLogging.findByUser("COUCOU")) {
			System.out.println(l.getUtilisateur().getNom());
		}
		
		context.close();
	}

	
}
