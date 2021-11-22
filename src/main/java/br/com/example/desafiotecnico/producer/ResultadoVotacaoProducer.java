package br.com.example.desafiotecnico.producer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ResultadoVotacaoProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void send(String name, String message) {
        log.info("Mensagem enviada: {}", message);
        kafkaTemplate.send(name, message);
    }

}
