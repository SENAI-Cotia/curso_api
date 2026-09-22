package com.curso_api.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "cursos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String descricao;

    private Integer cargaHoraria;

    @ManyToOne()
    @JoinColumn(name = "instrutor_id")
    private Instrutor instrutor;
}
