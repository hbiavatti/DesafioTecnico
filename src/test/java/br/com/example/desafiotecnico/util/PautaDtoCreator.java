package br.com.example.desafiotecnico.util;

import br.com.example.desafiotecnico.dto.PautaDto;

public class PautaDtoCreator {

    public static PautaDto createPautaDto() {
        return PautaDto.builder().nome(PautaCreator.createValidPauta().getNome()).build();
    }
}
