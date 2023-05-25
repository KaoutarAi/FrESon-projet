package fr.projet.api.musique.response;

import org.springframework.beans.BeanUtils;

import fr.projet.model.musique.Album;
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

    @Getter @Setter
    private Album album;

    public static MusiqueResponse convert(Musique music) {
        MusiqueResponse response = new MusiqueResponse();
        BeanUtils.copyProperties(music, response);
        return response;

    }

}
