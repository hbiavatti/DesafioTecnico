package br.com.example.desafiotecnico.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class VotoInvalidoException extends RuntimeException {
    public VotoInvalidoException(String message) {
        super(message);
    }
}
