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
import fr.projet.model.musique.Playlist;
import fr.projet.repo.IPlaylistRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/playlist")
public class PlaylistApiController {
    @Autowired
    private IPlaylistRepository repoPlaylist;

    @GetMapping
    @Transactional
    public List<PlaylistResponse> findAll() {
        return this.repoPlaylist.findAllFetchMusiques()
                                .stream()
                                .map(PlaylistResponse::convert)
                                .toList();
    }

    @GetMapping("/{id}")
    public PlaylistResponse findById(@PathVariable int id) {
        return PlaylistResponse.convert(this.repoPlaylist.findByIdFetchMusiques(id).orElseThrow(PlaylistNotFoundException::new));
    }

    @PostMapping
    public PlaylistResponse add(@Valid @RequestBody PlaylistRequest request, BindingResult result) {
        if (result.hasErrors()) {
            throw new PlaylistNotValidException();
        }

        return PlaylistResponse.convert(this.repoPlaylist.save(request.toPlaylist()));
    }

    @PutMapping("/{id}")
    public PlaylistResponse update(@PathVariable int id, @Valid @RequestBody PlaylistRequest request, BindingResult result) {
        if (result.hasErrors()) {
            throw new PlaylistNotValidException();
        }

        Playlist playlist = request.toPlaylist();
        playlist.setId(id);
        return PlaylistResponse.convert(this.repoPlaylist.save(playlist));
    }

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
