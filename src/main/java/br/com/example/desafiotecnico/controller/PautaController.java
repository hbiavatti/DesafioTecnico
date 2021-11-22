package br.com.example.desafiotecnico.controller;

import br.com.example.desafiotecnico.dto.PautaDto;
import br.com.example.desafiotecnico.entity.Pauta;
import br.com.example.desafiotecnico.mapper.PautaMapper;
import br.com.example.desafiotecnico.service.PautaService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Timer;
import java.util.TimerTask;

@RequestMapping("/v1/pauta")
@RestController
@Api("pauta")
@RequiredArgsConstructor
public class PautaController {
    private final PautaService pautaService;
    private final PautaMapper pautaMapper;

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
    public ResponseEntity<PautaDto> findById(@PathVariable("id") Long id) {
        PautaDto pauta = pautaMapper.toDto(pautaService.findById(id));
        return ResponseEntity.ok(pauta);
    }

    @GetMapping("/byNome/{nome}")
    @Operation(summary = "Localiza uma pauta pelo nome.", tags = {"pauta"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Operação realizada com sucesso."),
            @ApiResponse(responseCode = "400", description = "Pauta não encontrada."),
    })
    public ResponseEntity<PautaDto> findByNome(@PathVariable("nome") String nome) {
        PautaDto pauta = pautaMapper.toDto(pautaService.findByNome(nome));
        return ResponseEntity.ok(pauta);
    }

    @GetMapping("/iniciar/{id}/{tempo}")
    @Operation(summary = "Iniciar votação pauta.", tags = {"pauta"}, parameters = {@Parameter(description = "Id da pauta para iniciar a votação."), @Parameter(description = "Tempo da sessão de votaçao.")})
    @ApiResponses({
            @ApiResponse(responseCode = "202", description = "Operação realizada com sucesso."),
            @ApiResponse(responseCode = "400", description = "Pauta inválida."),
    })
    public ResponseEntity<Void> start(@PathVariable Long id, @PathVariable Long tempo) {
        Pauta p = pautaService.iniciarVotacao(id, tempo);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }


}