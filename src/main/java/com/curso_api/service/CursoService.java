package com.curso_api.service;

import com.curso_api.dto.CursoRequestDTO;
import com.curso_api.dto.CursoResponseDTO;
import com.curso_api.entity.Curso;
import com.curso_api.entity.Instrutor;
import com.curso_api.mapper.CursoMapper;
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
    private final CursoMapper cursoMapper;

    public CursoResponseDTO criar(CursoRequestDTO dto) {
        Instrutor instrutor = buscarInstrutorOuLancarErro(dto.instrutorId());

        Curso curso = cursoMapper.toEntity(dto);
        curso.setInstrutor(instrutor);

        return cursoMapper.toResponse(cursoRepository.save(curso));
    }

    public List<CursoResponseDTO> listarTodos() {
        return cursoRepository.findAll()
                .stream()
                .map(cursoMapper::toResponse)
                .toList();
    }

    public CursoResponseDTO buscarPorId(Long id) {
        return cursoMapper.toResponse(buscarOuLancarErro(id));
    }

    public CursoResponseDTO atualizar(Long id, CursoRequestDTO dto) {
        Curso curso = buscarOuLancarErro(id);
        Instrutor instrutor = buscarInstrutorOuLancarErro(dto.instrutorId());

        curso.setNome(dto.nome());
        curso.setDescricao(dto.descricao());
        curso.setCargaHoraria(dto.cargaHoraria());
        curso.setInstrutor(instrutor);

        return cursoMapper.toResponse(cursoRepository.save(curso));
    }

    public void deletar(Long id) {
        buscarOuLancarErro(id);
        cursoRepository.deleteById(id);
    }

    private Curso buscarOuLancarErro(Long id) {
        return cursoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Curso não encontrado com id: " + id));
    }

    private Instrutor buscarInstrutorOuLancarErro(Long id) {
        return instrutorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Instrutor não encontrado com id: " + id));
    }
}
