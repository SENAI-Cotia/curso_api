package com.curso_api.service;

import com.curso_api.dto.CursoCadastroRequest;
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

    public Curso criar(CursoCadastroRequest request) {
        Instrutor instrutor = instrutorRepository.findById(request.instrutorId())
                .orElseThrow(() -> new EntityNotFoundException("Instrutor não encontrado com id: " + request.instrutorId()));

        Curso curso = new Curso();
        curso.setNome(request.nome());
        curso.setDescricao(request.descricao());
        curso.setCargaHoraria(request.cargaHoraria());
        curso.setInstrutor(instrutor);

        return cursoRepository.save(curso);
    }

    public List<Curso> listarTodos() {
        return cursoRepository.findAll();
    }

    public Curso buscarPorId(Long id) {
        return cursoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Curso não encontrado com id: " + id));
    }

    public Curso atualizar(Long id, CursoCadastroRequest request) {
        Curso curso = buscarPorId(id);

        Instrutor instrutor = instrutorRepository.findById(request.instrutorId())
                .orElseThrow(() -> new EntityNotFoundException("Instrutor não encontrado com id: " + request.instrutorId()));

        curso.setNome(request.nome());
        curso.setDescricao(request.descricao());
        curso.setCargaHoraria(request.cargaHoraria());
        curso.setInstrutor(instrutor);

        return cursoRepository.save(curso);
    }

    public void deletar(Long id) {
        buscarPorId(id);
        cursoRepository.deleteById(id);
    }
}
