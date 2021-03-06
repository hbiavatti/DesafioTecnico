package br.com.example.desafiotecnico.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PautaDto {
    @Schema(description = "ID da pauta.", example = "1")
    private Long id;
    @Schema(description = "Nome da pauta.", example = "Pauta de exemplo", required = true)
    @Size(max = 100)
    @NotEmpty(message = "O nome da pauta deve ser informado!")
    private String nome;
}