package br.com.example.desafiotecnico;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@EnableKafka
@SpringBootApplication
public class DesafioTecnicoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DesafioTecnicoApplication.class, args);
    }

}
