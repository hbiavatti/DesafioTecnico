package br.com.example.desafiotecnico.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Pauta {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Nome da pauta deve ser informado!")
    @Column(nullable = false, unique = true)
    @ToString.Include
    private String nome;
    private Date dataInicioVotacao;
    private long duracao = 1000;
    @Column(nullable = false)
    private boolean aberta;

}
