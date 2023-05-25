package fr.projet.api.musique.response;

import java.util.List;

import org.springframework.beans.BeanUtils;

import fr.projet.enums.Tag;
import fr.projet.model.musique.Playlist;
import lombok.Getter;
import lombok.Setter;

public class PlaylistResponse {
    @Getter @Setter
    private int id;

    @Getter @Setter
    private String nom;

    @Getter @Setter
    private Tag etiquette;

    @Getter @Setter
    private List<MusiqueResponse> musiques;

    public static PlaylistResponse convert(Playlist playlist) {
        PlaylistResponse response = new PlaylistResponse();
        BeanUtils.copyProperties(playlist, response);
        if (playlist.getMusiques() != null) {
            response.musiques = playlist.getMusiques()
                                        .stream()
                                        .map(MusiqueResponse::convert)
                                        .toList();
        }

        return response;
    }
}
