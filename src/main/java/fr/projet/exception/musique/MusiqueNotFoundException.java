package fr.projet.exception.musique;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class MusiqueNotFoundException extends RuntimeException {

}
