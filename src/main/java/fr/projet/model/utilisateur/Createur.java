package fr.projet.model.utilisateur;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("Créateur")
public class Createur extends Utilisateur{

}
