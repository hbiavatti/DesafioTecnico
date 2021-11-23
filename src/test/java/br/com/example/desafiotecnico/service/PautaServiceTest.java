package br.com.example.desafiotecnico.service;

import br.com.example.desafiotecnico.entity.Pauta;
import br.com.example.desafiotecnico.exception.BadRequestException;
import br.com.example.desafiotecnico.mapper.PautaMapper;
import br.com.example.desafiotecnico.mapper.PautaMapperImpl;
import br.com.example.desafiotecnico.repository.PautaRepository;
import br.com.example.desafiotecnico.util.PautaCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {PautaMapperImpl.class})
class PautaServiceTest {

    @InjectMocks
    private PautaService pautaService;
    @Mock
    private PautaRepository pautaRepositoryMock;
    @Autowired
    private PautaMapper pautaMapper;

    @BeforeEach
    public void setUp() {
        BDDMockito.when(pautaRepositoryMock.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(PautaCreator.createValidPauta()));
        BDDMockito.when(pautaRepositoryMock.findByNome(ArgumentMatchers.anyString())).thenReturn(Optional.of(PautaCreator.createValidPauta()));
        BDDMockito.when(pautaRepositoryMock.save(ArgumentMatchers.any(Pauta.class))).thenReturn(PautaCreator.createValidPauta());
    }

    @Test
    @DisplayName("Find by ID returns pauta when successful")
    void findById_returnsPauta_WhenSuccessful() {
        Long expectedId = PautaCreator.createValidPauta().getId();
        Pauta pauta = pautaService.findByIdOrThrowBadRequestException(1l);
        Assertions.assertThat(pauta).isNotNull();
        Assertions.assertThat(pauta.getId()).isNotNull().isEqualTo(expectedId);
    }

    @Test
    @DisplayName("Find by ID throws BadRequestException")
    void findByIdThrowBadRequestException() {
        BDDMockito.when(pautaRepositoryMock.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.empty());
        Assertions.assertThatExceptionOfType(BadRequestException.class).isThrownBy(() -> pautaService.findById(1l));
    }

    @Test
    @DisplayName("Find by name returns pauta when successful")
    void findByName_returnsPautas_WhenSuccessful() {
        String expectedName = PautaCreator.createValidPauta().getNome();
        Pauta pauta = pautaService.findByNomeOrThrowBadRequestException("");
        Assertions.assertThat(pauta).isNotNull();
        Assertions.assertThat(pauta.getNome()).isEqualTo(expectedName);
    }
}