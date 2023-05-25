package fr.projet.api.musique.request;

import org.springframework.beans.BeanUtils;

import fr.projet.model.musique.Album;
import lombok.Getter;
import lombok.Setter;

public class AlbumRequest {
    @Getter @Setter
    private String nom;

    @Getter @Setter
    private String artiste;

    public Album toAlbum() {
        Album album = new Album();
        BeanUtils.copyProperties(this, album);
        return album;
    }
}
