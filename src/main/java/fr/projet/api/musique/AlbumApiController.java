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

import fr.projet.api.musique.request.AlbumRequest;
import fr.projet.api.musique.response.AlbumResponse;
import fr.projet.exception.AlbumNotFoundException;
import fr.projet.exception.AlbumNotValidException;
import fr.projet.model.musique.Album;
import fr.projet.repo.IAlbumRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/album")
public class AlbumApiController {
    @Autowired
    private IAlbumRepository repoAlbum;

    @GetMapping
    @Transactional
    public List<AlbumResponse> findAll() {
        return this.repoAlbum.findAllFetchMusiques()
                             .stream()
                             .map(AlbumResponse::convert)
                             .toList();
    }

    @GetMapping("/{id}")
    public AlbumResponse findById(@PathVariable int id) {
        return AlbumResponse.convert(this.repoAlbum.findByIdFetchMusiques(id).orElseThrow(AlbumNotFoundException::new));
    }

    @PostMapping
    public AlbumResponse add(@Valid @RequestBody AlbumRequest request, BindingResult result) {
        if (result.hasErrors()) {
            throw new AlbumNotValidException();
        }

        return AlbumResponse.convert(this.repoAlbum.save(request.toAlbum()));
    }

    @PutMapping("/{id}")
    public AlbumResponse update(@PathVariable int id, @Valid @RequestBody AlbumRequest request, BindingResult result) {
        if (result.hasErrors()) {
            throw new AlbumNotValidException();
        }

        Album album = request.toAlbum();
        album.setId(id);

        return AlbumResponse.convert(this.repoAlbum.save(album));
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable int id) {
        try {
            this.repoAlbum.deleteById(id);
        }
        catch (Exception ex) {
            throw new AlbumNotFoundException();
        }
    }

}
