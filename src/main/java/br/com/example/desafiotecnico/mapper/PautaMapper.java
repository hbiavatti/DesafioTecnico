package br.com.example.desafiotecnico.mapper;

import br.com.example.desafiotecnico.dto.PautaDto;
import br.com.example.desafiotecnico.entity.Pauta;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PautaMapper extends EntityMapper<PautaDto, Pauta> {
}