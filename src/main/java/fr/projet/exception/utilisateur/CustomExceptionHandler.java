package fr.projet.exception.utilisateur;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntityNotValidException.class)
    public ResponseEntity<Object> handleEntityNotValidException(EntityNotValidException ex, WebRequest request) {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}

