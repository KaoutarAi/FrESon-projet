package fr.projet.api.musique.response;

import java.util.List;

import org.springframework.beans.BeanUtils;

import fr.projet.model.musique.Album;
import lombok.Getter;
import lombok.Setter;

public class AlbumResponse {
    @Getter @Setter
    private int id;

    @Getter @Setter
    private String nom;

    @Getter @Setter
    private String artiste;

    @Getter @Setter
    private List<MusiqueResponse> musiques;

    public static AlbumResponse convert(Album album) {
        AlbumResponse response = new AlbumResponse();
        // Hibernate.initialize(album.getMusiques()); // Fill the attribute musiques when the JSON serialization
        BeanUtils.copyProperties(album, response);
        if (album.getMusiques() != null) {
            response.musiques = album.getMusiques().stream()
            .map(MusiqueResponse::convert).toList();
        }
        return response;
    }

}
