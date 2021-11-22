package br.com.example.desafiotecnico.service;

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
import java.util.Date;

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
        Pauta aux = findByNome(entity.getNome());
        if (aux != null) {
            throw new BadRequestException("Já existe uma pauta com o nome '"+entity.getNome()+"'!");
        }
        log.debug("Salvando pauta " + entity.toString());
        return pautaMapper.toDto(repository.saveAndFlush(entity));
    }

    public Pauta findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new BadRequestException("Pauta não encontrada!"));
    }

    public Pauta findByNome(String nome) {
        return repository.findByNome(nome).orElseThrow(() -> new BadRequestException("Pauta '" + nome + "' não encontrada!"));
    }

    @SneakyThrows
    public Pauta iniciarVotacao(Long id, Long tempo) {
        Pauta entity = findById(id);
        log.debug("Iniciando votação da pauta {}", entity.getNome());
        entity.setDuracao(tempo != null ? tempo : 1000);
        entity.setDataInicioVotacao(new Date());
        entity.setAberta(true);
        log.debug("Criando timer para encerramento da votação com duração de {}ms", entity.getDuracao());
        JobDetail job = newJob(entity.getNome(), "Finaliza votação pauta " + entity.getNome());
        scheduler.scheduleJob(job, trigger(job, entity.getDuracao()));
        return repository.saveAndFlush(entity);
    }

    private JobDetail newJob(String identity, String description) {
        return JobBuilder.newJob().storeDurably()
                .withIdentity(JobKey.jobKey(identity))
                .withDescription(description)
                .ofType(FinalizaVotacaoJob.class)
                .build();
    }

    private SimpleTrigger trigger(JobDetail jobDetail, long milliseconds) {
        return TriggerBuilder.newTrigger().forJob(jobDetail)
                .withIdentity(jobDetail.getKey().getName(), jobDetail.getKey().getGroup())
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInMilliseconds(milliseconds))
                .build();
    }

}