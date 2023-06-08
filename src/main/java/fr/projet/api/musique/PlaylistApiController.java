package fr.projet.api.musique;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.projet.api.musique.request.PlaylistRequest;
import fr.projet.api.musique.response.PlaylistResponse;
import fr.projet.exception.PlaylistNotFoundException;
import fr.projet.exception.PlaylistNotValidException;
import fr.projet.model.logging.Logging;
import fr.projet.model.musique.Playlist;
import fr.projet.model.utilisateur.Utilisateur;
import fr.projet.repo.ILoggingRepository;
import fr.projet.repo.IPlaylistRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/playlist")
public class PlaylistApiController {
    @Autowired
    private IPlaylistRepository repoPlaylist;
    
    @Autowired
    private ILoggingRepository repoLogging;

    // Display all playlists
    @GetMapping
    @Transactional
    public List<PlaylistResponse> findAll() {
        return this.repoPlaylist.findAllFetchMusiques()
                                .stream()
                                .map(PlaylistResponse::convert)
                                .toList();
    }

    // Display the infos of a specific playlist (fetched by its id)
    @GetMapping("/{id}")
    public PlaylistResponse findById(@PathVariable int id) {
        return PlaylistResponse.convert(this.repoPlaylist.findByIdFetchMusiques(id).orElseThrow(PlaylistNotFoundException::new));
    }

    // Display all playlists ordered by number of subscribers
    @GetMapping("/plus-vues")
    @Transactional
    public List<PlaylistResponse> findAllByViews() {
        return this.repoPlaylist.findAllByOrderNbAbo()
                                .stream()
                                .map(PlaylistResponse::convert)
                                .toList();
    }

    // Save a new playlist
    @PostMapping
    public PlaylistResponse add(@Valid @RequestBody PlaylistRequest request, BindingResult result) {
        if (result.hasErrors()) {
            throw new PlaylistNotValidException();
        }
        Logging log = new Logging();

        log.setUtilisateur(request.getUtilisateur());
        log.setText("Création de la playlist : "+ request.getNom());

        this.repoLogging.save(log);
        return PlaylistResponse.convert(this.repoPlaylist.save(request.toPlaylist()));
    }

    // Update an existing playlist (fetched by its id)
    @PutMapping("/{id}")
    public PlaylistResponse update(@PathVariable int id, @Valid @RequestBody PlaylistRequest request, BindingResult result) {
        if (result.hasErrors()) {
            throw new PlaylistNotValidException();
        }

        Playlist playlist = request.toPlaylist();
        playlist.setId(id);
        return PlaylistResponse.convert(this.repoPlaylist.save(playlist));
    }

    // Delete a playlist
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable int id) {
    	Logging log = new Logging();
    	Utilisateur user = this.repoPlaylist.findById(id).get().getUtilisateur();
    	String nom = this.repoPlaylist.findById(id).get().getNom();
    	
    	log.setUtilisateur(user);
    	log.setText("Suppression de la playlist : "+ nom);
    	
        try {
        	this.repoLogging.save(log);
            this.repoPlaylist.deleteById(id);
        }

        catch (Exception e) {
            throw new PlaylistNotFoundException();
        }
    }
}
