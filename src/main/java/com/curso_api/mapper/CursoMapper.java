package com.curso_api.mapper;

import com.curso_api.dto.CursoRequestDTO;
import com.curso_api.dto.CursoResponseDTO;
import com.curso_api.entity.Curso;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CursoMapper {

    @Mapping(source = "instrutor.nome", target = "instrutorNome")
    CursoResponseDTO toResponse(Curso curso);

    // instrutorId não pode ser convertido em Instrutor automaticamente:
    // o Mapper não tem acesso ao banco de dados.
    // O Service resolve o Instrutor e chama curso.setInstrutor() depois.
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "instrutor", ignore = true)
    Curso toEntity(CursoRequestDTO dto);
}
