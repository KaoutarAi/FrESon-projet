package fr.projet.api.musique.response;

import java.util.List;

import org.springframework.beans.BeanUtils;

import fr.projet.enums.Tag;
import fr.projet.model.musique.Playlist;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PlaylistResponse {
    private int id;

    private String nom;

    private Tag etiquette;

    private List<MusiqueResponse> musiques;

    public PlaylistResponse(Playlist playlist) {
        BeanUtils.copyProperties(playlist, this);
        if (playlist.getMusiques() != null) {
            this.musiques = playlist.getMusiques()
                                        .stream()
                                        .map(MusiqueResponse::new)
                                        .toList();
        }
    }
}
