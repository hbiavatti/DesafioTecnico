package br.com.example.desafiotecnico.repository;

import br.com.example.desafiotecnico.entity.Voto;
import br.com.example.desafiotecnico.util.VotoCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@ActiveProfiles("test")
@DisplayName("Tests for voto respository")
class VotoRepositoryTest {

    @Autowired
    private VotoRepository votoRepository;
    @Autowired
    private PautaRepository pautaRepository;

    @Test
    @DisplayName("Save Voto when successful")
    void save_Voto_WhenSuccessful() {
        Voto voto = VotoCreator.createVotoToBeSaved();
        Voto savedVoto = this.votoRepository.save(voto);
        Assertions.assertThat(savedVoto).isNotNull();
        Assertions.assertThat(savedVoto.getId()).isNotNull();
        Assertions.assertThat(savedVoto.getCpfAssociado()).isEqualTo(voto.getCpfAssociado());
        Assertions.assertThat(savedVoto.isVoto()).isEqualTo(voto.isVoto());
    }

    @Test
    @DisplayName("Save updates Voto when successful")
    void save_UpdatesVoto_WhenSuccessful() {
        Voto voto = VotoCreator.createVotoToBeSaved();
        Voto savedVoto = this.votoRepository.save(voto);
        savedVoto.setVoto(false);
        Voto votoUpdated = this.votoRepository.save(savedVoto);
        Assertions.assertThat(votoUpdated).isNotNull();
        Assertions.assertThat(votoUpdated.getId()).isNotNull();
        Assertions.assertThat(savedVoto.getCpfAssociado()).isEqualTo(voto.getCpfAssociado());
        Assertions.assertThat(votoUpdated.isVoto()).isEqualTo(savedVoto.isVoto());
    }

    @Test
    @DisplayName("Delete removes Voto when successful")
    void delete_RemovesVoto_WhenSuccessful() {
        Voto voto = VotoCreator.createVotoToBeSaved();
        Voto savedVoto = this.votoRepository.save(voto);
        this.votoRepository.delete(savedVoto);
        Optional<Voto> optional = this.votoRepository.findById(savedVoto.getId());
        Assertions.assertThat(optional).isEmpty();
    }

    @Test
    @DisplayName("Find by Pauta and Cpf Associado return a Voto when successful")
    void findByPautaAndCpfAssociado_returnsVoto_WhenSuccessful() {
        Voto voto = VotoCreator.createVotoToBeSaved();
        voto.setPauta(pautaRepository.save(voto.getPauta()));
        Voto savedVoto = this.votoRepository.save(voto);
        Optional<Voto> votoAux = this.votoRepository.findByPautaAndCpfAssociado(savedVoto.getPauta(), savedVoto.getCpfAssociado());
        Assertions.assertThat(votoAux.get()).isNotNull().isEqualTo(savedVoto);
    }

    @Test
    @DisplayName("Find by Pauta and return a list of Voto when successful")
    void findAllByPauta_returnsVotos_WhenSuccessful() {
        Voto voto = VotoCreator.createVotoToBeSaved();
        voto.setPauta(pautaRepository.save(voto.getPauta()));
        Voto savedVoto = this.votoRepository.save(voto);
        List<Voto> votos = this.votoRepository.findAllByPauta(savedVoto.getPauta());
        Assertions.assertThat(votos).isNotEmpty().isNotNull().hasSize(1);
    }

}