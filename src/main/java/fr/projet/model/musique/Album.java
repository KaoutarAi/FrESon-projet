package fr.projet.model.musique;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "albums")
public class Album {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter
    @Column(name = "album_id")
    private int id;

    @Column(name = "album_name", nullable = false)
    @NotBlank
    @Getter @Setter
    private String nom;

    @Column(name = "album_artist")
    @NotBlank
    @Getter @Setter
    private String artiste;



    @OneToMany(mappedBy = "album")
    @Getter @Setter
    private List<Musique> musiques;

}
