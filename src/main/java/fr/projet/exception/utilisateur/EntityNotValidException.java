package fr.projet.exception.utilisateur;

import org.springframework.http.HttpStatus; 
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class EntityNotValidException extends RuntimeException {

}
