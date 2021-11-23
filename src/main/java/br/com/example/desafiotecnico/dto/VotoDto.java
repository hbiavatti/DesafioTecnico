package br.com.example.desafiotecnico.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VotoDto {
    @Schema(description = "ID do voto.", example = "1", required = false)
    private Long id;
    @Schema(description = "Nome da pauta.", example = "Pauta exemplo 1", required = true)
    @NotEmpty(message = "Nome da pauta deve ser informada!")
    private String pauta;
    @Schema(description = "CPF do associado.", example = "14265624898", required = true)
    @NotEmpty(message = "CPF do associado deve ser informado!")
    private String cpfAssociado;
    @Schema(description = "Voto do associado.", example = "true ou false", required = true)
    private boolean voto;
}