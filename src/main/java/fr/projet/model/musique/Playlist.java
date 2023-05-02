package fr.projet.model.musique;

import java.util.List;

import fr.projet.enums.Tag;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "playlists")
public class Playlist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "playlist_id")
    @Getter @Setter
    private int id;

    @Column(name = "playlist_name")
    @Getter @Setter
    private String nom;

    @Column(name = "playlist_is_public")
    @Getter @Setter
    private boolean isPublic;

    @Column(name = "playlist_tag")
    @Enumerated(EnumType.STRING)
    @Getter @Setter
    private Tag etiquette;

    @ManyToMany
    @JoinTable(
        name = "playlists_contenu",
        joinColumns = @JoinColumn(name = "playlist_id"),
        inverseJoinColumns = @JoinColumn(name = "music_id")
    )
    private List<Musique> musiques;


    // Ajouter utilisateur
}
