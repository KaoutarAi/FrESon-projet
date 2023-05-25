package fr.projet.api.musique.response;

import org.springframework.beans.BeanUtils;

import fr.projet.model.musique.Musique;
import lombok.Getter;
import lombok.Setter;

public class MusiqueResponse {
    @Getter @Setter
    private int id;

    @Getter @Setter
    private String titre;

    @Getter @Setter
    private String artiste;

    @Getter @Setter
    private int duree;

    public static MusiqueResponse convert(Musique music) {
        MusiqueResponse response = new MusiqueResponse();
        BeanUtils.copyProperties(music, response);
        return response;
    }

    public Musique toMusic() {
        Musique music = new Musique();
        BeanUtils.copyProperties(this, music);
        return music;
    }

}
