package br.com.example.desafiotecnico.controller;

import br.com.example.desafiotecnico.dto.IniciarVotacaoDto;
import br.com.example.desafiotecnico.dto.PautaDto;
import br.com.example.desafiotecnico.service.PautaService;
import br.com.example.desafiotecnico.util.PautaCreator;
import br.com.example.desafiotecnico.util.PautaDtoCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class PautaControllerTest {

    @InjectMocks
    private PautaController pautaController;
    @Mock
    private PautaService pautaServiceMock;

    @BeforeEach
    public void setUp() {
        BDDMockito.when(pautaServiceMock.findById(ArgumentMatchers.anyLong())).thenReturn(PautaDtoCreator.createPautaDto());
        BDDMockito.when(pautaServiceMock.findByNome(ArgumentMatchers.anyString())).thenReturn(PautaDtoCreator.createPautaDto());
        BDDMockito.when(pautaServiceMock.save(ArgumentMatchers.any(PautaDto.class))).thenReturn(PautaDtoCreator.createPautaDto());
        BDDMockito.when(pautaServiceMock.iniciarVotacao(ArgumentMatchers.any(IniciarVotacaoDto.class))).thenReturn(PautaCreator.createVotePauta());
    }

    @Test
    @DisplayName("Find by ID returns pauta when successful")
    void findById_returnsPauta_WhenSuccessful() {
        pautaServiceMock.save(PautaDtoCreator.createPautaDto());
        Long expectedId = PautaCreator.createValidPauta().getId();
        PautaDto pauta = pautaController.findById(1l).getBody();
        Assertions.assertThat(pauta).isNotNull();
        Assertions.assertThat(pauta.getId()).isNotNull().isEqualTo(expectedId);
    }

    @Test
    @DisplayName("Find by name returns pauta when successful")
    void findByName_returnsListOfPautas_WhenSuccessful() {
        String expectedName = PautaCreator.createValidPauta().getNome();
        PautaDto pauta = pautaController.findByNome("").getBody();
        Assertions.assertThat(pauta).isNotNull();
        Assertions.assertThat(pauta.getNome()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("Find by name returns null when pauta is not found")
    void findByName_returnsEmptyListOfPautas_WhenPautaIsNotFound() {
        BDDMockito.when(pautaServiceMock.findByNome(ArgumentMatchers.anyString())).thenReturn(null);
        PautaDto pauta = pautaController.findByNome("pauta").getBody();
        Assertions.assertThat(pauta).isNull();
    }

    @Test
    @DisplayName("Save returns pauta when successful")
    void save_returnsPauta_WhenSuccessful() {
        PautaDto pauta = pautaController.create(PautaDtoCreator.createPautaDto()).getBody();
        Assertions.assertThat(pauta).isNotNull().isEqualTo(PautaDtoCreator.createPautaDto());
    }

    @Test
    @DisplayName("Start vote pauta when successful")
    void start_vote_WhenSuccessful() {
        IniciarVotacaoDto votacaoDto = IniciarVotacaoDto.builder().pauta(PautaCreator.createValidPauta().getNome()).build();
        Assertions.assertThatCode(() -> pautaController.start(votacaoDto)).doesNotThrowAnyException();
        ResponseEntity<Void> entity = pautaController.start(votacaoDto);
        Assertions.assertThat(entity).isNotNull();
        Assertions.assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.ACCEPTED);
    }
}