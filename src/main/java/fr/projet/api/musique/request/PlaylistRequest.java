package fr.projet.api.musique.request;

import java.util.List;

import org.springframework.beans.BeanUtils;

import fr.projet.api.musique.response.MusiqueResponse;
import fr.projet.enums.Tag;
import fr.projet.model.musique.Playlist;
import fr.projet.model.utilisateur.Utilisateur;
import lombok.Getter;
import lombok.Setter;

public class PlaylistRequest {
    @Getter @Setter
    private String nom;

    @Getter @Setter
    private boolean isPublic;

    @Getter @Setter
    private Tag etiquette;

    @Getter @Setter
    private List<MusiqueResponse> musiques;

    @Getter @Setter
    private Utilisateur utilisateur;

    public Playlist toPlaylist() {
        Playlist playlist = new Playlist();
        BeanUtils.copyProperties(this, playlist);
        if (this.musiques != null) {
            playlist.setMusiques(this.musiques
                                    .stream()
                                    .map(mr -> mr.toMusic())
                                    .toList());
        }
        return playlist;
    }

}
