package br.com.example.desafiotecnico.service;

import br.com.example.desafiotecnico.dto.IniciarVotacaoDto;
import br.com.example.desafiotecnico.dto.PautaDto;
import br.com.example.desafiotecnico.entity.Pauta;
import br.com.example.desafiotecnico.exception.BadRequestException;
import br.com.example.desafiotecnico.jobs.FinalizaVotacaoJob;
import br.com.example.desafiotecnico.mapper.PautaMapper;
import br.com.example.desafiotecnico.repository.PautaRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class PautaService {
    private final PautaRepository repository;
    private final PautaMapper pautaMapper;
    private final Scheduler scheduler;


    public PautaDto save(PautaDto pautaDto) {
        Pauta entity = pautaMapper.toEntity(pautaDto);
        Optional<Pauta> aux = repository.findByNome(entity.getNome());
        if (aux.isPresent()) {
            throw new BadRequestException("Já existe uma pauta com o nome '" + entity.getNome() + "'!");
        }
        log.debug("Salvando pauta " + entity);
        return pautaMapper.toDto(repository.save(entity));
    }

    public PautaDto findById(Long id) {
        return pautaMapper.toDto(findByIdOrThrowBadRequestException(id));
    }

    public Pauta findByIdOrThrowBadRequestException(Long id) {
        return repository.findById(id).orElseThrow(() -> new BadRequestException("Pauta não encontrada!"));
    }

    public PautaDto findByNome(String nome) {
        return pautaMapper.toDto(findByNomeOrThrowBadRequestException(nome));
    }

    public Pauta findByNomeOrThrowBadRequestException(String nome) {
        return repository.findByNome(nome).orElseThrow(() -> new BadRequestException("Pauta '" + nome + "' não encontrada!"));
    }

    @SneakyThrows
    public Pauta iniciarVotacao(IniciarVotacaoDto votacaoDto) {
        Pauta entity = findByNomeOrThrowBadRequestException(votacaoDto.getNomePauta());
        if (!entity.isAberta()) {
            log.debug("Iniciando votação da pauta {}", entity.getNome());
            entity.setDuracao(votacaoDto.getDuracao() != null ? votacaoDto.getDuracao() : 1000);
            entity.setDataInicioVotacao(new Date());
            entity.setAberta(true);
            log.debug("Criando timer para encerramento da votação com duração de {}ms", entity.getDuracao());
            JobDetail job = newJob(entity.getNome(), "Finaliza votação pauta " + entity.getNome());
            scheduler.scheduleJob(job, trigger(job, entity.getDuracao()));
        }
        return repository.save(entity);
    }

    private JobDetail newJob(String identity, String description) {
        return JobBuilder.newJob().storeDurably()
                .withIdentity(JobKey.jobKey(identity))
                .withDescription(description)
                .ofType(FinalizaVotacaoJob.class)
                .build();
    }

    private Trigger trigger(JobDetail jobDetail, long milliseconds) {
        LocalDateTime ldt = LocalDateTime.now();
        ldt = ldt.plusSeconds(milliseconds / 1000);
        return TriggerBuilder.newTrigger().forJob(jobDetail)
                .withIdentity(jobDetail.getKey().getName(), jobDetail.getKey().getGroup())
                .startAt(Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant()))
                .build();
    }

}