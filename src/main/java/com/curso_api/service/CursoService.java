package com.curso_api.service;

import com.curso_api.dto.CursoCadastroRequest;
import com.curso_api.dto.CursoResponseDTO;
import com.curso_api.dto.InstrutorResumoDTO;
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

    public CursoResponseDTO criar(CursoCadastroRequest request) {
        Instrutor instrutor = instrutorRepository.findById(request.instrutorId())
                .orElseThrow(() -> new EntityNotFoundException("Instrutor não encontrado com id: " + request.instrutorId()));

        Curso curso = new Curso();
        curso.setNome(request.nome());
        curso.setDescricao(request.descricao());
        curso.setCargaHoraria(request.cargaHoraria());
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

    public CursoResponseDTO atualizar(Long id, CursoCadastroRequest request) {
        Curso curso = buscarOuLancarErro(id);

        Instrutor instrutor = instrutorRepository.findById(request.instrutorId())
                .orElseThrow(() -> new EntityNotFoundException("Instrutor não encontrado com id: " + request.instrutorId()));

        curso.setNome(request.nome());
        curso.setDescricao(request.descricao());
        curso.setCargaHoraria(request.cargaHoraria());
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
        InstrutorResumoDTO instrutorResumo = new InstrutorResumoDTO(
                curso.getInstrutor().getId(),
                curso.getInstrutor().getNome()
        );

        return new CursoResponseDTO(
                curso.getId(),
                curso.getNome(),
                curso.getDescricao(),
                curso.getCargaHoraria(),
                instrutorResumo
        );
    }
}
