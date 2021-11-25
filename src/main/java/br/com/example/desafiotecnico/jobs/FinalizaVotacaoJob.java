package br.com.example.desafiotecnico.jobs;

import br.com.example.desafiotecnico.entity.Pauta;
import br.com.example.desafiotecnico.entity.Voto;
import br.com.example.desafiotecnico.producer.ResultadoVotacaoProducer;
import br.com.example.desafiotecnico.repository.PautaRepository;
import br.com.example.desafiotecnico.repository.VotoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

import java.util.List;
import java.util.Optional;

/**
 * Classe para realizar o serviço Quartz de finalização de votação
 */
@Slf4j
@RequiredArgsConstructor
public class FinalizaVotacaoJob implements Job {
    private final PautaRepository pautaRepository;
    private final VotoRepository votoRepository;
    private final ResultadoVotacaoProducer resultadoVotacaoProducer;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        Optional<Pauta> entity = pautaRepository.findByNome(jobExecutionContext.getJobDetail().getKey().getName());
        if (entity.isPresent()) {
            finalizaVotacao(entity.get());
        }
    }

    private void finalizaVotacao(Pauta pauta) {
        log.debug("Finalizando votação da pauta {}", pauta.getNome());
        pauta.setAberta(false);
        pautaRepository.save(pauta);
        log.debug("Votação da pauta {} finalizada com sucesso!", pauta.getNome());
        calculaResultadoVotacao(pauta);
    }

    private void calculaResultadoVotacao(Pauta pauta) {
        log.debug("Iniciando contagem de votos da pauta {}", pauta.getNome());
        List<Voto> votos = votoRepository.findAllByPauta(pauta);
        long sim = votos.stream().filter(v -> v.isVoto()).count();
        log.debug("{} votaram SIM", sim);
        long nao = votos.stream().filter(v -> !v.isVoto()).count();
        log.debug("{} votaram NÃO", nao);
        StringBuilder resultado = new StringBuilder();
        resultado.append("SIM ->").append(sim);
        resultado.append("\n").append("NÃO -> ").append(nao);
        log.debug("Contagem de votos da pauta {} finalizada com sucesso!", pauta.getNome());
        resultadoVotacaoProducer.send("RESULTADO_" + pauta.getNome().toUpperCase().replaceAll(" ", "_"), resultado.toString());
    }
}
