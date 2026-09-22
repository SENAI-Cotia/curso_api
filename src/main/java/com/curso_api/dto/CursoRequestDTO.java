package com.curso_api.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CursoRequestDTO(

        @NotBlank(message = "Nome é obrigatório")
        String nome,

        @NotBlank(message = "Descrição é obrigatória")
        String descricao,

        @NotNull(message = "Carga horária é obrigatória")
        @Min(value = 1, message = "Carga horária mínima é 1 hora")
        Integer cargaHoraria,

        @NotNull(message = "Instrutor é obrigatório")
        Long instrutorId
) {}
