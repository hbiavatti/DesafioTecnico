package br.com.example.desafiotecnico.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VotoDto {
    private Long id;
    private String pauta;
    private String cpfAssociado;
    private boolean voto;
}