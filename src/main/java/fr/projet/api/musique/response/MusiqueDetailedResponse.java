package fr.projet.api.musique.response;

import org.springframework.beans.BeanUtils;

import fr.projet.model.musique.Album;
import fr.projet.model.musique.Link;
import fr.projet.model.musique.Musique;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MusiqueDetailedResponse {

    private int id;

    private String titre;

    private String artiste;

    private int duree;

    private int nbDownloads;

    private int nbPlays;

    private int linkId;

    private String linkAudio;

    private String linkDownload;

    private String image;

    private int albumId;

    private String albumNom;

    public MusiqueDetailedResponse(Musique music) {
        BeanUtils.copyProperties(music, this);
        if (music.getAdresse() != null) {
            this.linkId = music.getAdresse().getId();
            this.linkAudio = music.getAdresse().getAdresseApi();
            this.linkDownload = music.getAdresse().getAdresseApiDwnload();
            this.image = music.getAdresse().getAdresseImage();
        }

        if (music.getAlbum() != null) {
            this.albumId = music.getAlbum().getId();
            this.albumNom = music.getAlbum().getNom();
        }
    }

    public Musique toMusic() {
        Musique music = new Musique();
        BeanUtils.copyProperties(this, music);

        Album album = new Album();
        Link link = new Link();
        album.setId(this.albumId);
        link.setId(linkId);
        music.setAlbum(album);
        music.setAdresse(link);

        return music;
    }

}
