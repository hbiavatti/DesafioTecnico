package br.com.example.desafiotecnico.integration;

import br.com.example.desafiotecnico.dto.VotoDto;
import br.com.example.desafiotecnico.entity.Pauta;
import br.com.example.desafiotecnico.repository.PautaRepository;
import br.com.example.desafiotecnico.repository.VotoRepository;
import br.com.example.desafiotecnico.util.VotoDtoCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Date;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class VotoControllerIT {
    @Autowired
    @Qualifier(value = "testRestTemplate")
    private TestRestTemplate testRestTemplate;
    @Autowired
    private PautaRepository pautaRepository;
    @Autowired
    private VotoRepository votoRepository;

    @TestConfiguration
    @Lazy
    static class Config {
        @Bean(name="testRestTemplate")
        public TestRestTemplate testRestTemplate(@Value("${local.server.port}") int port) {
            RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder()
                    .rootUri("http://localhost:"+port);
            return new TestRestTemplate(restTemplateBuilder);
        }
    }

//    @Test
//    @DisplayName("Save voto when successful")
//    void save_returnsVoto_WhenSuccessful() {
//        votoRepository.deleteAll();
//        pautaRepository.deleteAll();
//        Pauta p = Pauta.builder().aberta(true).dataInicioVotacao(new Date()).nome("Teste").duracao(300000).build();
//        p = pautaRepository.save(p);
//        VotoDto votoDto = VotoDtoCreator.createVotoDto();
//        votoDto.setPauta(p.getNome());
//        votoDto.setId(null);
//        ResponseEntity<Void> responseEntity = testRestTemplate.postForEntity("/desafio/v1/voto", votoDto, Void.class);
//        Assertions.assertThat(responseEntity).isNotNull();
//        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
//    }

    @Test
    @DisplayName("Save voto returns403 when When Cpf is invalid")
    void save_returns403_WhenCpfIsInvalid() {
        votoRepository.deleteAll();
        pautaRepository.deleteAll();
        Pauta p = Pauta.builder().aberta(true).dataInicioVotacao(new Date()).nome("Teste").duracao(300000).build();
        p = pautaRepository.save(p);
        VotoDto votoDto = VotoDto.builder().voto(true).pauta(p.getNome()).cpfAssociado("12345678901").build();
        ResponseEntity<Void> responseEntity = testRestTemplate.postForEntity("/desafio/v1/voto", votoDto, Void.class);
        Assertions.assertThat(responseEntity).isNotNull();
        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }
}
