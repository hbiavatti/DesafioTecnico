package br.com.example.desafiotecnico.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@ApiModel()
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PautaDto {
    private Long id;
    @Size(max = 100)
    @NotBlank
    private String nome;
    private long duracao;
}