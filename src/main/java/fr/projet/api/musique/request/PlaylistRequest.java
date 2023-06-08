package fr.projet.api.musique.request;

import java.util.List;

import org.springframework.beans.BeanUtils;

import fr.projet.api.musique.response.MusiqueResponse;
import fr.projet.enums.Tag;
import fr.projet.model.musique.Playlist;
import fr.projet.model.utilisateur.Utilisateur;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PlaylistRequest {
    private String nom;

    private boolean isPublic;

    private Tag etiquette;

    private List<MusiqueResponse> musiques;

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
