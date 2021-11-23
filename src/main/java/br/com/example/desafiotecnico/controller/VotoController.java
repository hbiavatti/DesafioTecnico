package br.com.example.desafiotecnico.controller;

import br.com.example.desafiotecnico.dto.VotoDto;
import br.com.example.desafiotecnico.service.VotoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/v1/voto")
@RestController
@Tag(name = "voto", description = "API para criação de votos")
@RequiredArgsConstructor
public class VotoController {
    private final VotoService votoService;

    @PostMapping
    @Operation(summary = "Novo voto.", tags = {"voto"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Operação realizada com sucesso."),
            @ApiResponse(responseCode = "400", description = "Voto inválido."),
    })
    public ResponseEntity<Void> save(@RequestBody @Validated VotoDto votoDto) {
        votoService.save(votoDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}