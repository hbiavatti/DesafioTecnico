package br.com.example.desafiotecnico.util;


import br.com.example.desafiotecnico.entity.Voto;

public class VotoCreator {

    public static Voto createVotoToBeSaved() {
        return Voto.builder().pauta(PautaCreator.createValidPauta()).voto(true).cpfAssociado("05595759550").build();
    }

    public static Voto createValidVoto() {
        return Voto.builder().pauta(PautaCreator.createValidPauta()).voto(true).cpfAssociado("05595759550").id(1l).build();
    }

    public static Voto createValidUpdatedVoto() {
        return Voto.builder().pauta(PautaCreator.createValidPauta()).voto(true).cpfAssociado("05595759550").id(1l).build();
    }
}
