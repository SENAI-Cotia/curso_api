package com.curso_api.controller;

import com.curso_api.dto.InstrutorRequestDTO;
import com.curso_api.dto.InstrutorResponseDTO;
import com.curso_api.service.InstrutorService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/instrutores")
public class InstrutorController {

    private final InstrutorService instrutorService;

    public InstrutorController(InstrutorService instrutorService) {
        this.instrutorService = instrutorService;
    }

    @PostMapping
    public ResponseEntity<InstrutorResponseDTO> criar(@Valid @RequestBody InstrutorRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(instrutorService.criar(dto));
    }

    @GetMapping
    public ResponseEntity<List<InstrutorResponseDTO>> listarTodos() {
        return ResponseEntity.ok(instrutorService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<InstrutorResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(instrutorService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<InstrutorResponseDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody InstrutorRequestDTO dto) {
        return ResponseEntity.ok(instrutorService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        instrutorService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
