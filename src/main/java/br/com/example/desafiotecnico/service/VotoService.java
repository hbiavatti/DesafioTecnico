package br.com.example.desafiotecnico.service;

import br.com.example.desafiotecnico.dto.ValidaCpf;
import br.com.example.desafiotecnico.dto.VotoDto;
import br.com.example.desafiotecnico.entity.Voto;
import br.com.example.desafiotecnico.enumerator.StatusCpf;
import br.com.example.desafiotecnico.exception.BadRequestException;
import br.com.example.desafiotecnico.exception.VoteNotValidException;
import br.com.example.desafiotecnico.repository.VotoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class VotoService {
    private final VotoRepository repository;
    private final PautaService pautaService;

    private static final String URL = "https://user-info.herokuapp.com/users/";

    public VotoDto save(VotoDto votoDto) {
        Voto entity = new Voto();
        votoDto.setCpfAssociado(votoDto.getCpfAssociado().replaceAll("\\D", ""));
        entity.setPauta(pautaService.findByNome(votoDto.getPauta()));
        if (!entity.getPauta().isAberta()) {
            log.debug("Votação encerrada para a pauta {}!", entity.getPauta().getNome());
            throw new BadRequestException("Votação encerrada para a pauta " + entity.getPauta().getNome() + "!");
        }
        if (!validaCpfAssiciado(votoDto.getCpfAssociado())) {
            log.debug("CPF '{}' não habilitado para votação!", votoDto.getCpfAssociado());
            throw new VoteNotValidException("CPF '" + votoDto.getCpfAssociado() + "' não habilitado para votação!");
        }
        entity.setVoto(votoDto.isVoto());
        entity.setCpfAssociado(votoDto.getCpfAssociado());
        Optional<Voto> aux = repository.findByPautaAndCpfAssociado(entity.getPauta(), entity.getCpfAssociado());
        if (aux.isPresent()) {
            log.debug("CPF '{}' já votou na pauta {}!", votoDto.getCpfAssociado(), votoDto.getPauta());
            throw new BadRequestException("Não é permitido votar mais de uma vez por pauta.");
        }
        log.debug("Novo voto registrado com sucesso!");
        votoDto.setId(repository.save(entity).getId());
        return votoDto;
    }

    public boolean validaCpfAssiciado(String cpfAssociado) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<ValidaCpf> response = restTemplate.getForEntity(URL + cpfAssociado, ValidaCpf.class);
            if (response.getStatusCode().equals(HttpStatus.OK)) {
                return response.getBody().getStatus().equals(StatusCpf.ABLE_TO_VOTE);
            } else {
                throw new VoteNotValidException("CPF '" + cpfAssociado + "' inválido!");
            }
        } catch (RestClientException ex) {
            throw new VoteNotValidException("CPF '" + cpfAssociado + "' inválido!");
        }
    }
}