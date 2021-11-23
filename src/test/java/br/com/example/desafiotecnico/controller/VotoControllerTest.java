package br.com.example.desafiotecnico.controller;

import br.com.example.desafiotecnico.dto.VotoDto;
import br.com.example.desafiotecnico.service.VotoService;
import br.com.example.desafiotecnico.util.VotoDtoCreator;
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
class VotoControllerTest {

    @InjectMocks
    private VotoController votoController;
    @Mock
    private VotoService votoServiceMock;


    @BeforeEach
    public void setUp() {
        BDDMockito.when(votoServiceMock.save(ArgumentMatchers.any(VotoDto.class))).thenReturn(VotoDtoCreator.createVotoDto());
    }

    @Test
    @DisplayName("Save returns voto when successful")
    void save_returnsVoto_WhenSuccessful() {
        Assertions.assertThatCode(
                () -> votoController.save(VotoDtoCreator.createVotoDto()).getBody()
        ).doesNotThrowAnyException();
        ResponseEntity<Void> entity = votoController.save(VotoDtoCreator.createVotoDto());
        Assertions.assertThat(entity).isNotNull();
        Assertions.assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

}