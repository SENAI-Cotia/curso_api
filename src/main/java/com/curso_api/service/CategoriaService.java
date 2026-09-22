package com.curso_api.service;

import com.curso_api.dto.CategoriaRequestDTO;
import com.curso_api.dto.CategoriaResponseDTO;
import com.curso_api.entity.Categoria;
import com.curso_api.repository.CategoriaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public CategoriaResponseDTO criar(CategoriaRequestDTO dto) {
        Categoria categoria = new Categoria();
        categoria.setNome(dto.nome());
        return toResponseDTO(categoriaRepository.save(categoria));
    }

    public List<CategoriaResponseDTO> listarTodas() {
        return categoriaRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public CategoriaResponseDTO buscarPorId(Long id) {
        return toResponseDTO(buscarOuLancarErro(id));
    }

    public CategoriaResponseDTO atualizar(Long id, CategoriaRequestDTO dto) {
        Categoria categoria = buscarOuLancarErro(id);
        categoria.setNome(dto.nome());
        return toResponseDTO(categoriaRepository.save(categoria));
    }

    public void deletar(Long id) {
        buscarOuLancarErro(id);
        categoriaRepository.deleteById(id);
    }

    public Categoria buscarOuLancarErro(Long id) {
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada com id: " + id));
    }

    private CategoriaResponseDTO toResponseDTO(Categoria categoria) {
        return new CategoriaResponseDTO(categoria.getId(), categoria.getNome());
    }
}
