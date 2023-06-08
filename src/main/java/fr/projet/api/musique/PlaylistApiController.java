package fr.projet.api.musique;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.projet.api.musique.request.PlaylistRequest;
import fr.projet.api.musique.response.PlaylistResponse;
import fr.projet.enums.Tag;
import fr.projet.exception.PlaylistNotFoundException;
import fr.projet.exception.PlaylistNotValidException;
import fr.projet.model.musique.Playlist;
import fr.projet.repo.IPlaylistRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/playlist")
public class PlaylistApiController {
    @Autowired
    private IPlaylistRepository repoPlaylist;

    // Display all playlists
    @GetMapping
    @Transactional
    public List<PlaylistResponse> findAll() {
        return this.repoPlaylist.findAllFetchMusiques()
                                .stream()
                                .map(PlaylistResponse::new)
                                .toList();
    }

    // Display all public playlists
    @GetMapping("/public")
    @Transactional
    public List<PlaylistResponse> findAllPublic() {
        return this.repoPlaylist.findAllPublicFetchMusiques()
                                .stream()
                                .map(PlaylistResponse::new)
                                .toList();
    }



    // Display the infos of a specific playlist (fetched by its id)
    @GetMapping("/{id}")
    public PlaylistResponse findById(@PathVariable int id) {
        return new PlaylistResponse(this.repoPlaylist.findByIdFetchMusiques(id).orElseThrow(PlaylistNotFoundException::new));
    }

    @GetMapping("/par-nom")
    public List<PlaylistResponse> findByNomContaining(@RequestParam String substring, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int limit) {
        return this.repoPlaylist.findByNomContaining(substring, PageRequest.of(page, limit))
                                .stream()
                                .map(PlaylistResponse::new)
                                .toList();
    }

    @GetMapping("/par-utilisateur")
    public List<PlaylistResponse> findByUtilisateurContaining(@RequestParam String substring, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int limit) {
        return this.repoPlaylist.findByUtilisateurContaining(substring, PageRequest.of(page, limit))
                                .stream()
                                .map(PlaylistResponse::new)
                                .toList();
    }

    @GetMapping("/par-etiquette")
    public List<PlaylistResponse> findByTagContaining(@RequestParam Tag tag, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int limit) {
        return this.repoPlaylist.findByEtiquette(tag, PageRequest.of(page, limit))
                                .stream()
                                .map(PlaylistResponse::new)
                                .toList();
    }

    // Display all playlists ordered by number of subscribers
    @GetMapping("/plus-vues")
    @Transactional
    public List<PlaylistResponse> findAllByViews() {
        return this.repoPlaylist.findAllByOrderNbAbo()
                                .stream()
                                .map(PlaylistResponse::new)
                                .toList();
    }

    @GetMapping("/plus-vues/top")
    @Transactional
    public List<PlaylistResponse> findTopByViews(@RequestParam(defaultValue = "10") int limit) {
        return this.repoPlaylist.findTopByNbAbo(PageRequest.of(0, limit))
                                .stream()
                                .map(PlaylistResponse::new)
                                .toList();
    }

    @GetMapping("/plus-recents")
    @Transactional
    public List<PlaylistResponse> findAllByDates() {
        return this.repoPlaylist.findAllByCreationDateDesc()
                                .stream()
                                .map(PlaylistResponse::new)
                                .toList();
    }

    @GetMapping("/plus-recents/top")
    @Transactional
    public List<PlaylistResponse> findTopByDates(@RequestParam(defaultValue = "10") int limit) {
        return this.repoPlaylist.findTopByCreationDate(PageRequest.of(0, limit))
                                .stream()
                                .map(PlaylistResponse::new)
                                .toList();
    }

    // Save a new playlist
    @PostMapping
    public PlaylistResponse add(@Valid @RequestBody PlaylistRequest request, BindingResult result) {
        if (result.hasErrors()) {
            throw new PlaylistNotValidException();
        }

        return new PlaylistResponse(this.repoPlaylist.save(request.toPlaylist()));
    }

    // Update an existing playlist (fetched by its id)
    @PutMapping("/{id}")
    public PlaylistResponse update(@PathVariable int id, @Valid @RequestBody PlaylistRequest request, BindingResult result) {
        if (result.hasErrors()) {
            throw new PlaylistNotValidException();
        }

        Playlist playlist = request.toPlaylist();
        playlist.setId(id);
        return new PlaylistResponse(this.repoPlaylist.save(playlist));
    }

    // Delete a playlist
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable int id) {
        try {
            this.repoPlaylist.deleteById(id);
        }

        catch (Exception e) {
            throw new PlaylistNotFoundException();
        }
    }
}
