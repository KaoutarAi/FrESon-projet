package fr.projet.model.utilisateur;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("MODERATEUR")
public class Moderateur extends Utilisateur{
//
}
