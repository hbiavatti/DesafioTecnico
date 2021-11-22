package br.com.example.desafiotecnico.dto;

import br.com.example.desafiotecnico.enumerator.StatusCpf;
import lombok.Data;

@Data
public class ValidaCpf {
    private StatusCpf status;
}
