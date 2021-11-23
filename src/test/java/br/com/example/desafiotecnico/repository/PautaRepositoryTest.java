package br.com.example.desafiotecnico.repository;

import br.com.example.desafiotecnico.entity.Pauta;
import br.com.example.desafiotecnico.util.PautaCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

@DataJpaTest
@ActiveProfiles("test")
@DisplayName("Tests for pauta respository")
class PautaRepositoryTest {

    @Autowired
    private PautaRepository pautaRepository;

    @Test
    @DisplayName("Save Pauta when successful")
    void save_Pauta_WhenSuccessful() {
        Pauta pauta = PautaCreator.createPautaToBeSaved();
        Pauta savedPauta = this.pautaRepository.save(pauta);
        Assertions.assertThat(savedPauta).isNotNull();
        Assertions.assertThat(savedPauta.getId()).isNotNull();
        Assertions.assertThat(savedPauta.getNome()).isEqualTo(pauta.getNome());
    }

    @Test
    @DisplayName("Save updates Pauta when successful")
    void save_UpdatesPauta_WhenSuccessful() {
        Pauta pauta = PautaCreator.createPautaToBeSaved();
        Pauta savedPauta = this.pautaRepository.save(pauta);
        savedPauta.setNome("Overload");
        Pauta pautaUpdated = this.pautaRepository.save(savedPauta);
        Assertions.assertThat(pautaUpdated).isNotNull();
        Assertions.assertThat(pautaUpdated.getId()).isNotNull();
        Assertions.assertThat(pautaUpdated.getNome()).isEqualTo(savedPauta.getNome());
    }

    @Test
    @DisplayName("Delete removes Pauta when successful")
    void delete_RemovesPauta_WhenSuccessful() {
        Pauta pauta = PautaCreator.createPautaToBeSaved();
        Pauta savedPauta = this.pautaRepository.save(pauta);
        this.pautaRepository.delete(savedPauta);
        Optional<Pauta> optional = this.pautaRepository.findById(savedPauta.getId());
        Assertions.assertThat(optional).isEmpty();
    }

    @Test
    @DisplayName("Find by Nome return a list of Pauta when successful")
    void findByNome_returnsPautas_WhenSuccessful() {
        Pauta pauta = PautaCreator.createPautaToBeSaved();
        Pauta savedPauta = this.pautaRepository.save(pauta);
        Optional<Pauta> pautaAux = this.pautaRepository.findByNome(savedPauta.getNome());
        Assertions.assertThat(pautaAux.get()).isNotNull().isEqualTo(savedPauta);
    }

    @Test
    @DisplayName("Find by Nome return empty list when no pauta is found")
    void findByNome_returnsEmptyList_WhenPautaIsNotFound() {
        Optional<Pauta> pauta = this.pautaRepository.findByNome("adsada");
        Assertions.assertThat(pauta.isEmpty());
    }
}