package br.com.example.desafiotecnico.exception;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class ValidacaoExceptionDetails extends ExceptionDetails {
    private final String fields;
    private final String fieldsMessage;
}
