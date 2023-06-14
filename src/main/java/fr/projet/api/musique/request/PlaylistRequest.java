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

    private int utilisateurId;

    public Playlist toPlaylist() {
        Playlist playlist = new Playlist();
        BeanUtils.copyProperties(this, playlist);
        if (this.musiques != null) {
            playlist.setMusiques(this.musiques
                                    .stream()
                                    .map(mr -> mr.toMusic())
                                    .toList());
        }
        if (this.utilisateurId != 0) {
            Utilisateur utilisateur = new Utilisateur();
            utilisateur.setId(this.utilisateurId);
            playlist.setUtilisateur(utilisateur);
        }
        return playlist;
    }

}
