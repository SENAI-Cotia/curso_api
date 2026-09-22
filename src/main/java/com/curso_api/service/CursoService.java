package com.curso_api.service;

import com.curso_api.dto.CursoRequestDTO;
import com.curso_api.dto.CursoResponseDTO;
import com.curso_api.entity.Curso;
import com.curso_api.entity.Instrutor;
import com.curso_api.repository.CursoRepository;
import com.curso_api.repository.InstrutorRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CursoService {

    private final CursoRepository cursoRepository;
    private final InstrutorRepository instrutorRepository;

    public CursoResponseDTO criar(CursoRequestDTO dto) {
        Instrutor instrutor = instrutorRepository.findById(dto.instrutorId())
                .orElseThrow(() -> new EntityNotFoundException("Instrutor não encontrado com id: " + dto.instrutorId()));

        Curso curso = new Curso();
        curso.setNome(dto.nome());
        curso.setDescricao(dto.descricao());
        curso.setCargaHoraria(dto.cargaHoraria());
        curso.setInstrutor(instrutor);

        return toResponseDTO(cursoRepository.save(curso));
    }

    public List<CursoResponseDTO> listarTodos() {
        return cursoRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public CursoResponseDTO buscarPorId(Long id) {
        return toResponseDTO(buscarOuLancarErro(id));
    }

    public CursoResponseDTO atualizar(Long id, CursoRequestDTO dto) {
        Curso curso = buscarOuLancarErro(id);

        Instrutor instrutor = instrutorRepository.findById(dto.instrutorId())
                .orElseThrow(() -> new EntityNotFoundException("Instrutor não encontrado com id: " + dto.instrutorId()));

        curso.setNome(dto.nome());
        curso.setDescricao(dto.descricao());
        curso.setCargaHoraria(dto.cargaHoraria());
        curso.setInstrutor(instrutor);

        return toResponseDTO(cursoRepository.save(curso));
    }

    public void deletar(Long id) {
        buscarOuLancarErro(id);
        cursoRepository.deleteById(id);
    }

    private Curso buscarOuLancarErro(Long id) {
        return cursoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Curso não encontrado com id: " + id));
    }

    private CursoResponseDTO toResponseDTO(Curso curso) {
        return new CursoResponseDTO(
                curso.getId(),
                curso.getNome(),
                curso.getDescricao(),
                curso.getCargaHoraria(),
                curso.getInstrutor().getNome()
        );
    }
}
