package br.com.fiap.hydrosense.controller;

import br.com.fiap.hydrosense.dto.UnidadeConsumoRequestDTO;
import br.com.fiap.hydrosense.dto.UnidadeConsumoResponseDTO;
import br.com.fiap.hydrosense.model.TipoUnidade;
import br.com.fiap.hydrosense.service.UnidadeConsumoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller REST para gerenciamento de unidades de consumo
 */
@RestController
@RequestMapping("/api/unidades")
@RequiredArgsConstructor
public class UnidadeConsumoController {

    private final UnidadeConsumoService service;

    /**
     * GET /api/unidades - Lista todas as unidades
     */
    @GetMapping
    public ResponseEntity<List<UnidadeConsumoResponseDTO>> listarTodas(
            @RequestParam(required = false) TipoUnidade tipo,
            @RequestParam(required = false, defaultValue = "true") Boolean apenasAtivas
    ) {
        if (tipo != null) {
            return ResponseEntity.ok(service.listarPorTipo(tipo));
        }
        if (apenasAtivas) {
            return ResponseEntity.ok(service.listarAtivas());
        }
        return ResponseEntity.ok(service.listarTodas());
    }

    /**
     * GET /api/unidades/{id} - Busca unidade por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<UnidadeConsumoResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    /**
     * POST /api/unidades - Cria nova unidade
     */
    @PostMapping
    public ResponseEntity<UnidadeConsumoResponseDTO> criar(
            @Valid @RequestBody UnidadeConsumoRequestDTO dto
    ) {
        UnidadeConsumoResponseDTO criada = service.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(criada);
    }

    /**
     * PUT /api/unidades/{id} - Atualiza unidade existente
     */
    @PutMapping("/{id}")
    public ResponseEntity<UnidadeConsumoResponseDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody UnidadeConsumoRequestDTO dto
    ) {
        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    /**
     * DELETE /api/unidades/{id} - Desativa unidade (soft delete)
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> desativar(@PathVariable Long id) {
        service.desativar(id);
        return ResponseEntity.noContent().build();
    }
}
