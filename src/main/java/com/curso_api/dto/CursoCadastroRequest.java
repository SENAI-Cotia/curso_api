package com.curso_api.dto;

public record CursoCadastroRequest(
        String nome,
        String descricao,
        Integer cargaHoraria,
        Long instrutorId
) {}
