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

    private boolean isPublic;

    private List<MusiqueResponse> musiques;

    private int utilisateurId;

    private String utilisateurPseudo;

    public PlaylistResponse(Playlist playlist) {
        BeanUtils.copyProperties(playlist, this);
        if (playlist.getMusiques() != null) {
            this.musiques = playlist.getMusiques()
                                        .stream()
                                        .map(MusiqueResponse::new)
                                        .toList();
        }
        if (playlist.getUtilisateur() != null) {
            this.utilisateurId = playlist.getUtilisateur().getId();
            this.utilisateurPseudo = playlist.getUtilisateur().getPseudo();
        }
    }
}
