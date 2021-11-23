package br.com.example.desafiotecnico.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IniciarVotacaoDto {
    @Size(max = 100)
    @NotEmpty(message = "O nome da pauta deve ser informado!")
    private String pauta;
    private Long duracao;
}