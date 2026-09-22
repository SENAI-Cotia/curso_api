package com.curso_api.dto;

public record CursoResponseDTO(
        Long id,
        String nome,
        String descricao,
        Integer cargaHoraria,
        String instrutorNome
) {}
