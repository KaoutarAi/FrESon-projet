package fr.projet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import fr.projet.config.AppConfig;
import fr.projet.model.utilisateur.Utilisateur;
import fr.projet.repo.IAlbumRepository;
import fr.projet.repo.IMusiqueRepository;
import fr.projet.repo.IPlaylistRepository;

@Component
public class Application {
        @Autowired
        private IMusiqueRepository repoMusic;

        @Autowired
        private IPlaylistRepository repoPlaylist;

        @Autowired
        private IAlbumRepository repoAlbum;

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
        // repoMusic.findByTitreContaining("Mus", PageRequest.of(0, 3)).forEach(m -> System.out.println(m.getTitre()));
        // repoMusic.findAll()
        //          .stream()
        //          .forEach(
        //             m -> {
        //                 if (m.getAlbum() != null) {
        //                     System.out.println(m.getAlbum().getNom());
        //                 }
        //             }
        //             );
        // repoMusic.findByAlbumContaining("Alb", PageRequest.of(0, 5)).forEach(m -> System.out.println(m.getTitre()));
        // Utilisateur user = new Utilisateur();
        // user.setId(1);
        // repoPlaylist.findByUtilisateur(user, PageRequest.of(0, 5)).forEach(p -> System.out.println(p.getNom()));

        // repoPlaylist.findAll().forEach(p -> System.out.println(p.getId() + " " + p.getUtilisateur().getPseudo()));
    }
}
