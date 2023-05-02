package fr.projet;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import fr.projet.config.AppConfig;
import fr.projet.model.logging.Logging;
import fr.projet.model.utilisateur.Createur;
import fr.projet.model.utilisateur.Utilisateur;
import fr.projet.repo.ILoggingRepository;
import fr.projet.repo.IUtilisateurRepository;

@Component
public class Application {
	@Autowired
	private IUtilisateurRepository repoUtilisateur;

	
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
		
		Application app = context.getBean(Application.class);
		app.run();
		
		context.close();
	}

	
	public void run() {
		repoUtilisateur.findAll().forEach(u->System.out.println(u.getRole()+" - "+u.getEmail()));
		
		
		repoUtilisateur.findByPseudo("kawai").ifPresentOrElse(
			u -> System.out.println(u.getRole()+" - "+u.getEmail()),
			() -> System.out.println("Pas d'utilisateur avec ce pseudo !")
		);
		
		repoUtilisateur.findByPseudoAndRole("modER", "Modérateur").ifPresentOrElse(
				u -> System.out.println(u.getEmail()),
				() -> System.out.println("Pas de moderateur avec ce pseudo !")
			);
		
		repoUtilisateur.findAllByRole("Utilisateur").forEach(u->System.out.println(u.getEmail()));
		
		Utilisateur user = new Utilisateur();
		user.setNom("nom");
		user.setPrenom("user");
		user.setEmail("nom.user@gmail.com");
		user.setPseudo("nomU");
		user.setMdp("dkjdfk");
		repoUtilisateur.save(user);
		
		Utilisateur userc = new Createur();
		userc.setNom("nom");
		userc.setPrenom("prenom");
		userc.setEmail("nom.prenom@gmail.com");
		userc.setPseudo("nomC");
		userc.setMdp("tutkujhj");
		repoUtilisateur.save(userc);
		
		repoUtilisateur.deleteById(8);
		repoUtilisateur.deleteByPseudo("nomC");

	}	

}
