package com.curso_api.mapper;

import com.curso_api.dto.InstrutorRequestDTO;
import com.curso_api.dto.InstrutorResponseDTO;
import com.curso_api.entity.Instrutor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface InstrutorMapper {

    InstrutorResponseDTO toResponse(Instrutor instrutor);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "cursos", ignore = true)
    Instrutor toEntity(InstrutorRequestDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "cursos", ignore = true)
    void update(InstrutorRequestDTO dto, @MappingTarget Instrutor instrutor);
}
