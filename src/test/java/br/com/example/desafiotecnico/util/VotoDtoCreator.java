package br.com.example.desafiotecnico.util;

import br.com.example.desafiotecnico.dto.VotoDto;
import br.com.example.desafiotecnico.entity.Voto;

public class VotoDtoCreator {

    public static VotoDto createVotoDto() {
        Voto v = VotoCreator.createValidVoto();
        return VotoDto.builder().cpfAssociado(v.getCpfAssociado()).voto(v.isVoto()).pauta(v.getPauta().getNome()).id(1l).build();
    }
}
