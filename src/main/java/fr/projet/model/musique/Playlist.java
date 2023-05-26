package fr.projet.model.musique;

import java.util.List;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import fr.projet.enums.Tag;
import fr.projet.model.utilisateur.Utilisateur;
import fr.projet.model.utilisateur.Commentaire;
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
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    @NotBlank
    @Getter @Setter
    private String nom;

    @Column(name = "playlist_is_public")
    @NotNull
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
    @OnDelete(action = OnDeleteAction.CASCADE)
    @Getter @Setter
    private List<Musique> musiques;

    @ManyToOne
    @JoinColumn(name = "playlist_user_id")
    @Getter @Setter
    private Utilisateur utilisateur;

    @OneToMany(mappedBy = "playlist")
    @Getter @Setter
    private List<Commentaire> commentaire;

}
