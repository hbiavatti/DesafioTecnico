package br.com.example.desafiotecnico.util;


import br.com.example.desafiotecnico.entity.Pauta;

import java.util.Date;

public class PautaCreator {

    public static Pauta createPautaToBeSaved() {
        return Pauta.builder().nome("Teste 1").build();
    }

    public static Pauta createValidPauta() {
        return Pauta.builder().nome("Teste 1").id(1l).build();
    }

    public static Pauta createValidUpdatedPauta() {
        return Pauta.builder().nome("Teste 1 updated").id(1l).build();
    }

    public static Pauta createVotePauta() {
        return Pauta.builder().nome("Teste 1 updated").aberta(true).duracao(300000).dataInicioVotacao(new Date()).id(1l).build();
    }
}
