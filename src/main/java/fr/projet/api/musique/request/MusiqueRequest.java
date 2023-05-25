package fr.projet.api.musique.request;

import org.springframework.beans.BeanUtils;

import fr.projet.model.musique.Album;
import fr.projet.model.musique.Link;
import fr.projet.model.musique.Musique;
import lombok.Getter;
import lombok.Setter;

public class MusiqueRequest {
    @Getter @Setter
    private String titre;

    @Getter @Setter
    private String artiste;

    @Getter @Setter
    private String genre;

    @Getter @Setter
    private int duree;

    @Getter @Setter
    private Link adresse;

    @Getter @Setter
    private Album album;

    public Musique toMusique() {
        Musique music = new Musique();
        BeanUtils.copyProperties(this, music);
        return music;
    }

}
