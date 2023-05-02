package fr.projet.model.musique;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "musiques")
public class Musique {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "music_id")
    @Getter @Setter
    private int id;

    @Column(name = "music_title", length = 100, nullable = false)
    @Getter @Setter
    private String titre;

    @Column(name = "music_artist", length = 100, nullable = false)
    @Getter @Setter
    private String artiste;

    @Column(name = "music_genre", length = 250)
    @Getter @Setter
    private String genre;

    @Column(name = "music_duration")
    @Getter @Setter
    private int duree;

    @Column(name = "music_nb_plays")
    @Getter @Setter
    private int nbPlays;

    @Column(name = "music_nb_downloads")
    @Getter @Setter
    private int nbDownloads;

    @OneToOne
    @JoinColumn(name = "music_link")
    @Getter @Setter
    private Link adresse;

    @ManyToOne
    @JoinColumn(name = "music_album_id")
    @Getter @Setter
    private Album album;



}
