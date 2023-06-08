package fr.projet.api.musique.request;

import org.springframework.beans.BeanUtils;

import fr.projet.model.musique.Album;
import fr.projet.model.musique.Link;
import fr.projet.model.musique.Musique;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MusiqueRequest {
    private String titre;

    private String artiste;

    private String genre;

    private int duree;

    private Link adresse;

    private Album album;

    public Musique toMusique() {
        Musique music = new Musique();
        BeanUtils.copyProperties(this, music);
        return music;
    }

}
