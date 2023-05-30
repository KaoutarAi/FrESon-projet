package fr.projet.api.musique;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.projet.api.musique.response.PlaylistResponse;
import fr.projet.repo.IPlaylistRepository;
import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/api/home/playlists")
public class HomePlaylistApiController {
    @Autowired
    private IPlaylistRepository repoPlaylist;
    @GetMapping("/parvues")
    @Transactional
    public List<PlaylistResponse> findAllByViews() {
        return this.repoPlaylist.findAllByOrderNbAbo()
                                .stream()
                                .map(PlaylistResponse::convert)
                                .toList();
    }

    @GetMapping("/test")
    public void TestJPQLQuery() {
        System.out.println("PASSAGE HERE\n----------------------\n");
        // this.repoPlaylist.findAllByOrderNbAbo().forEach(p -> System.out.println(p.getNom()));
        // this.repoPlaylist.findAllByOrderNbAbo().forEach(p -> System.out.println(p.getNom()));
        this.repoPlaylist.findAllByCreationDateDesc().forEach(p -> System.out.println(p.getNom()));


    }
}
