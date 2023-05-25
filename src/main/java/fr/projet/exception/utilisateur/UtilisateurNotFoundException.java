package fr.projet.exception.utilisateur;

import org.springframework.http.HttpStatus; 
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class UtilisateurNotFoundException extends RuntimeException {

}
