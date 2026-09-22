package com.curso_api.mapper;

import com.curso_api.dto.CursoResponseDTO;
import com.curso_api.entity.Curso;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CursoMapper {

    // instrutorNome ficará null por enquanto — MapStruct não consegue
    // mapear automaticamente instrutor.nome → instrutorNome
    CursoResponseDTO toResponse(Curso curso);
}
