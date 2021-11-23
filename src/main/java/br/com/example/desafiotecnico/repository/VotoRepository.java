package br.com.example.desafiotecnico.repository;

import br.com.example.desafiotecnico.entity.Pauta;
import br.com.example.desafiotecnico.entity.Voto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VotoRepository extends JpaRepository<Voto, Long> {
    Optional<Voto> findByPautaAndCpfAssociado(Pauta p, String cpfAssociado);

    List<Voto> findAllByPauta(Pauta pauta);
}