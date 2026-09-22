package com.curso_api.dto;

import jakarta.validation.constraints.NotBlank;

public record CategoriaRequestDTO(
        @NotBlank(message = "Nome é obrigatório")
        String nome
) {}
