package com.curso_api.service;

import com.curso_api.dto.InstrutorRequestDTO;
import com.curso_api.dto.InstrutorResponseDTO;
import com.curso_api.entity.Instrutor;
import com.curso_api.mapper.InstrutorMapper;
import com.curso_api.repository.InstrutorRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InstrutorService {

    private final InstrutorRepository instrutorRepository;
    private final InstrutorMapper instrutorMapper;

    public InstrutorResponseDTO criar(InstrutorRequestDTO dto) {
        Instrutor salvo = instrutorRepository.save(instrutorMapper.toEntity(dto));
        return instrutorMapper.toResponse(salvo);
    }

    public List<InstrutorResponseDTO> listarTodos() {
        return instrutorRepository.findAll()
                .stream()
                .map(instrutorMapper::toResponse)
                .toList();
    }

    public InstrutorResponseDTO buscarPorId(Long id) {
        return instrutorMapper.toResponse(buscarOuLancarErro(id));
    }

    public InstrutorResponseDTO atualizar(Long id, InstrutorRequestDTO dto) {
        Instrutor instrutor = buscarOuLancarErro(id);
        instrutorMapper.update(dto, instrutor);
        return instrutorMapper.toResponse(instrutorRepository.save(instrutor));
    }

    public void deletar(Long id) {
        buscarOuLancarErro(id);
        instrutorRepository.deleteById(id);
    }

    private Instrutor buscarOuLancarErro(Long id) {
        return instrutorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Instrutor não encontrado com id: " + id));
    }
}
