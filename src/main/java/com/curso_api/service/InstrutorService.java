package com.curso_api.service;

import com.curso_api.dto.InstrutorRequestDTO;
import com.curso_api.dto.InstrutorResponseDTO;
import com.curso_api.entity.Instrutor;
import com.curso_api.repository.InstrutorRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InstrutorService {

    private final InstrutorRepository instrutorRepository;

    public InstrutorResponseDTO criar(InstrutorRequestDTO dto) {
        Instrutor instrutor = new Instrutor();
        instrutor.setNome(dto.nome());
        instrutor.setEmail(dto.email());

        Instrutor salvo = instrutorRepository.save(instrutor);
        return toResponseDTO(salvo);
    }

    public List<InstrutorResponseDTO> listarTodos() {
        return instrutorRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public InstrutorResponseDTO buscarPorId(Long id) {
        Instrutor instrutor = instrutorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Instrutor não encontrado com id: " + id));
        return toResponseDTO(instrutor);
    }

    public InstrutorResponseDTO atualizar(Long id, InstrutorRequestDTO dto) {
        Instrutor instrutor = instrutorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Instrutor não encontrado com id: " + id));
        instrutor.setNome(dto.nome());
        instrutor.setEmail(dto.email());

        return toResponseDTO(instrutorRepository.save(instrutor));
    }

    public void deletar(Long id) {
       instrutorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Instrutor não encontrado com id: " + id));
        instrutorRepository.deleteById(id);
    }

    private InstrutorResponseDTO toResponseDTO(Instrutor instrutor) {
        return new InstrutorResponseDTO(
                instrutor.getId(),
                instrutor.getNome(),
                instrutor.getEmail()
        );
    }
}
