package br.com.example.desafiotecnico.controller;

import br.com.example.desafiotecnico.dto.IniciarVotacaoDto;
import br.com.example.desafiotecnico.dto.PautaDto;
import br.com.example.desafiotecnico.entity.Pauta;
import br.com.example.desafiotecnico.service.PautaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RequestMapping("/v1/pauta")
@RestController
@Tag(name = "pauta", description = "API para criação de pautas")
@RequiredArgsConstructor
public class PautaController {
    private final PautaService pautaService;

    @PostMapping
    @Operation(summary = "Nova pauta.", tags = {"pauta"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Operação realizada com sucesso."),
            @ApiResponse(responseCode = "400", description = "Pauta inválida."),
    })
    public ResponseEntity<PautaDto> create(@RequestBody @Validated PautaDto pautaDto) {
        return new ResponseEntity<>(pautaService.save(pautaDto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Localiza uma pauta pelo Id.", tags = {"pauta"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Operação realizada com sucesso."),
            @ApiResponse(responseCode = "400", description = "Pauta não encontrada."),
    })
    public ResponseEntity<PautaDto> findById(@PathVariable("id")
                                             @Parameter(description = "Id da pauta a ser encontrata!") Long id) {
        return ResponseEntity.ok(pautaService.findById(id));
    }

    @GetMapping("/byNome/{nome}")
    @Operation(summary = "Localiza uma pauta pelo nome.", tags = {"pauta"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Operação realizada com sucesso."),
            @ApiResponse(responseCode = "400", description = "Pauta não encontrada."),
    })
    public ResponseEntity<PautaDto> findByNome(@PathVariable("nome")
                                               @Parameter(description = "Nome da pauta a ser encontrata!") String nome) {
        return ResponseEntity.ok(pautaService.findByNome(nome));
    }

    @PostMapping("/iniciar")
    @Operation(summary = "Iniciar votação pauta.", tags = {"pauta"})
    @ApiResponses({
            @ApiResponse(responseCode = "202", description = "Operação realizada com sucesso."),
            @ApiResponse(responseCode = "400", description = "Pauta inválida."),
    })
    public ResponseEntity<Void> start(@RequestBody @Validated IniciarVotacaoDto votacaoDto) {
        Pauta p = pautaService.iniciarVotacao(votacaoDto);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }


}