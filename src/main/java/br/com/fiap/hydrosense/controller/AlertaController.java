package br.com.fiap.hydrosense.controller;

import br.com.fiap.hydrosense.dto.AlertaResponseDTO;
import br.com.fiap.hydrosense.service.AlertaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller REST para gerenciamento de alertas
 */
@RestController
@RequestMapping("/api/alertas")
@RequiredArgsConstructor
public class AlertaController {

    private final AlertaService service;

    /**
     * GET /api/alertas - Lista todos os alertas
     */
    @GetMapping
    public ResponseEntity<List<AlertaResponseDTO>> listarTodos(
            @RequestParam(required = false, defaultValue = "false") Boolean apenasAtivos
    ) {
        if (apenasAtivos) {
            return ResponseEntity.ok(service.listarAtivos());
        }
        return ResponseEntity.ok(service.listarTodos());
    }

    /**
     * GET /api/alertas/{id} - Busca alerta por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<AlertaResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    /**
     * GET /api/alertas/unidade/{unidadeId} - Lista alertas de uma unidade
     */
    @GetMapping("/unidade/{unidadeId}")
    public ResponseEntity<List<AlertaResponseDTO>> listarPorUnidade(@PathVariable Long unidadeId) {
        return ResponseEntity.ok(service.listarPorUnidade(unidadeId));
    }

    /**
     * PUT /api/alertas/{id}/resolver - Resolve um alerta
     */
    @PutMapping("/{id}/resolver")
    public ResponseEntity<AlertaResponseDTO> resolver(@PathVariable Long id) {
        return ResponseEntity.ok(service.resolverAlerta(id));
    }

    /**
     * PUT /api/alertas/{id}/ignorar - Ignora um alerta
     */
    @PutMapping("/{id}/ignorar")
    public ResponseEntity<AlertaResponseDTO> ignorar(@PathVariable Long id) {
        return ResponseEntity.ok(service.ignorarAlerta(id));
    }

    /**
     * DELETE /api/alertas/{id} - Deleta um alerta
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
