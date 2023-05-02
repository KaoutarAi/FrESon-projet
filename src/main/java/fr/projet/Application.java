package fr.projet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import fr.projet.config.AppConfig;
import fr.projet.repo.IMusiqueRepository;

@Component
public class Application {
        @Autowired
        private IMusiqueRepository repoMusic;

	public static void main(String[] args) {
         try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class)) {
            Application app = context.getBean(Application.class);
            app.run();
        }
        catch (Exception ex) {
            ex.printStackTrace();
	    }
    }

    public void run() {
        repoMusic.findAll().forEach(m -> System.out.println(m.getTitre()));;
    }
}
