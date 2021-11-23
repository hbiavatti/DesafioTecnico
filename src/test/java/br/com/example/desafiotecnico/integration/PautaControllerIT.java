package br.com.example.desafiotecnico.integration;


import br.com.example.desafiotecnico.dto.PautaDto;
import br.com.example.desafiotecnico.entity.Pauta;
import br.com.example.desafiotecnico.repository.PautaRepository;
import br.com.example.desafiotecnico.repository.VotoRepository;
import br.com.example.desafiotecnico.util.PautaCreator;
import br.com.example.desafiotecnico.util.PautaDtoCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class PautaControllerIT {
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
        public TestRestTemplate testRestTemplateCreator(@Value("${local.server.port}") int port) {
            RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder().rootUri("http://localhost:"+port);
            return new TestRestTemplate(restTemplateBuilder);
        }
    }

    @BeforeEach
    private void setUp() {
        votoRepository.deleteAll();
        pautaRepository.deleteAll();
    }

    @Test
    @DisplayName("Find by ID returns pauta when successful")
    void findById_returnsPauta_WhenSuccessful() {
        Pauta pautaSaved = pautaRepository.save(PautaCreator.createPautaToBeSaved());
        Long expectedId = pautaSaved.getId();
        Pauta pauta = testRestTemplate.getForObject("/desafio/v1/pauta/{id}", Pauta.class, expectedId);
        Assertions.assertThat(pauta).isNotNull();
        Assertions.assertThat(pauta.getId()).isNotNull().isEqualTo(expectedId);
    }

    @Test
    @DisplayName("Find by name returns pauta when successful")
    void findByName_returnsPauta_WhenSuccessful() {
        Pauta pautaSaved = pautaRepository.save(PautaCreator.createPautaToBeSaved());
        String expectedName = pautaSaved.getNome();
        Pauta pauta = testRestTemplate.getForObject("/desafio/v1/pauta/byNome/{nome}", Pauta.class, expectedName);
        Assertions.assertThat(pauta).isNotNull();
        Assertions.assertThat(pauta.getNome()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("Find by name returns null when pauta is not found")
    void findByName_returnsnull_WhenPautaIsNotFound() {
        ResponseEntity<Pauta> pauta = testRestTemplate.getForEntity("/desafio/v1/pauta/byNome/{nome}", Pauta.class, "ABCD");
        Assertions.assertThat(pauta).isNotNull();
        Assertions.assertThat(pauta.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    @DisplayName("Create returns pauta when successful")
    void create_returnsPauta_WhenSuccessful() {
        PautaDto pautaDto = PautaDtoCreator.createPautaDto();
        ResponseEntity<Pauta> responseEntity = testRestTemplate.postForEntity("/desafio/v1/pauta", pautaDto, Pauta.class);
        Assertions.assertThat(responseEntity).isNotNull();
        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Assertions.assertThat(responseEntity.getBody()).isNotNull();
        Assertions.assertThat(responseEntity.getBody().getId()).isNotNull();
    }

}
