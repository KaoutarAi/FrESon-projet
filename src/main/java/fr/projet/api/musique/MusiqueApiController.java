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

import fr.projet.api.musique.response.MusiqueResponse;
import fr.projet.api.request.MusiqueRequest;
import fr.projet.exception.musique.MusiqueNotFoundException;
import fr.projet.exception.musique.MusiqueNotValidException;
import fr.projet.model.musique.Musique;
import fr.projet.repo.IMusiqueRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/musique")
public class MusiqueApiController {
    @Autowired
    private IMusiqueRepository repoMusic;

    @GetMapping
    public List<MusiqueResponse> findAll() {
        return this.repoMusic.findAll()
                             .stream()
                             .map(MusiqueResponse::convert)
                             .toList();
    }

    @GetMapping("/{id}")
    public MusiqueResponse findById(@PathVariable int id) {
        return MusiqueResponse.convert(this.repoMusic.findById(id).orElseThrow(MusiqueNotFoundException::new));
    }

    @PostMapping
    public MusiqueResponse add(@Valid @RequestBody MusiqueRequest request, BindingResult result) {
        if (result.hasErrors()) {
            throw new MusiqueNotValidException();
        }

        return MusiqueResponse.convert(this.repoMusic.save(request.toMusique()));
    }

    @PutMapping("/{id}")
    public MusiqueResponse update(@PathVariable int id, @Valid @RequestBody MusiqueRequest request, BindingResult result) {
        if (result.hasErrors()) {
            throw new MusiqueNotValidException();
        }

        Musique music = request.toMusique();
        music.setId(id);
        return MusiqueResponse.convert(this.repoMusic.save(music));
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable int id) {
        try {
            this.repoMusic.deleteById(id);
        }
        catch (Exception ex) {
            throw new MusiqueNotValidException();
        }
    }

}
