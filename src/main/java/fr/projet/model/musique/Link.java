package fr.projet.model.musique;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "adresses")
public class Link {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter
    private int id;

    @Column(name = "link_api", length = 500)
    @Getter @Setter
    private String adresseApi;

    @Column(name = "link_local", length = 500)
    @Getter @Setter
    private String adresseLocale;

}
