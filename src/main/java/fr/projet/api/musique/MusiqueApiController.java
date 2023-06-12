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

import fr.projet.api.musique.request.MusiqueRequest;
import fr.projet.api.musique.response.MusiqueDetailedResponse;
import fr.projet.api.musique.response.MusiqueResponse;
import fr.projet.exception.musique.MusiqueNotFoundException;
import fr.projet.exception.musique.MusiqueNotValidException;
import fr.projet.model.musique.Album;
import fr.projet.model.musique.Musique;
import fr.projet.repo.IMusiqueRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/musique")
public class MusiqueApiController {
    @Autowired
    private IMusiqueRepository repoMusic;

    // Display all musics
    @GetMapping
    public List<MusiqueResponse> findAll() {
        return this.repoMusic.findAll()
                             .stream()
                             .map(MusiqueResponse::new)
                             .toList();
    }

    // Display a specific music (fetched by its id)
    @GetMapping("/{id}")
    public MusiqueDetailedResponse findById(@PathVariable int id) {
        return new MusiqueDetailedResponse(this.repoMusic.findById(id).orElseThrow(MusiqueNotFoundException::new));
    }

    // Fetch music by part of title
    @GetMapping("/par-titre")
    public List<MusiqueResponse> findByTitreContaining(@RequestParam String substring, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int limit) {
        return this.repoMusic.findByTitreContaining(substring, PageRequest.of(page, limit))
                   .stream()
                   .map(MusiqueResponse::new)
                   .toList();
    }

    // Fetch music by artist containing
    @GetMapping("/par-artiste")
    public List<MusiqueResponse> findByArtisteContaining(@RequestParam String substring, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int limit) {
        return this.repoMusic.findByArtisteContaining(substring, PageRequest.of(page, limit))
                   .stream()
                   .map(MusiqueResponse::new)
                   .toList();
    }

    // Fetch music by album containing
    @GetMapping("/par-album")
    public List<MusiqueResponse> findByAlbumContaining(@RequestParam String substring, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int limit) {
        return this.repoMusic.findByAlbumContaining(substring, PageRequest.of(page, limit))
                   .stream()
                   .map(MusiqueResponse::new)
                   .toList();
    }

    // Fetch musics belonging to a specific album
    @GetMapping("/album/{id}")
    public List<MusiqueResponse> findByAlbumId(@PathVariable int id) {
        return this.repoMusic.findByAlbumId(id)
                   .stream()
                   .map(MusiqueResponse::new)
                   .toList();
    }

    @GetMapping("/popularite")
    public List<MusiqueResponse> findAllByNbPlays(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int limit) {
        return this.repoMusic.findAllByOrderByNbPlaysDesc(PageRequest.of(page, limit))
                   .stream()
                   .map(MusiqueResponse::new)
                   .toList();
    }


    // Create a new music TODO: add authorization for Moderator
    @PostMapping
    public MusiqueResponse add(@Valid @RequestBody MusiqueRequest request, BindingResult result) {
        if (result.hasErrors()) {
            throw new MusiqueNotValidException();
        }

        return new MusiqueResponse(this.repoMusic.save(request.toMusique()));
    }

    // Update an existing music TODO: same as above
    @PutMapping("/{id}")
    public MusiqueResponse update(@PathVariable int id, @Valid @RequestBody MusiqueRequest request, BindingResult result) {
        if (result.hasErrors()) {
            throw new MusiqueNotValidException();
        }

        Musique music = request.toMusique();
        music.setId(id);
        
        Album album = new Album();
        album.setId(request.getAlbum().getId());
        music.setAlbum(album);

        return new MusiqueResponse(this.repoMusic.save(music));
    }

    // Delete a music TODO: same as above
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable int id) {
        try {
            this.repoMusic.deleteById(id);
        }
        catch (Exception ex) {
            throw new MusiqueNotFoundException();
        }
    }

}
