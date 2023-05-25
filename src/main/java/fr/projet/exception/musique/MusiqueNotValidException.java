package fr.projet.exception.musique;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class MusiqueNotValidException extends RuntimeException {

}
