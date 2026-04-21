package br.com.fiap.hydrosense.controller;

import br.com.fiap.hydrosense.dto.ConsumoResumoDTO;
import br.com.fiap.hydrosense.dto.LeituraRequestDTO;
import br.com.fiap.hydrosense.dto.LeituraResponseDTO;
import br.com.fiap.hydrosense.service.LeituraService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Controller REST para gerenciamento de leituras de consumo
 */
@RestController
@RequestMapping("/api/leituras")
@RequiredArgsConstructor
public class LeituraController {

    private final LeituraService service;

    /**
     * GET /api/leituras - Lista todas as leituras
     */
    @GetMapping
    public ResponseEntity<List<LeituraResponseDTO>> listarTodas() {
        return ResponseEntity.ok(service.listarTodas());
    }

    /**
     * GET /api/leituras/{id} - Busca leitura por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<LeituraResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    /**
     * GET /api/leituras/unidade/{unidadeId} - Lista leituras de uma unidade
     */
    @GetMapping("/unidade/{unidadeId}")
    public ResponseEntity<List<LeituraResponseDTO>> listarPorUnidade(@PathVariable Long unidadeId) {
        return ResponseEntity.ok(service.listarPorUnidade(unidadeId));
    }

    /**
     * POST /api/leituras - Registra nova leitura
     */
    @PostMapping
    public ResponseEntity<LeituraResponseDTO> registrar(
            @Valid @RequestBody LeituraRequestDTO dto
    ) {
        LeituraResponseDTO registrada = service.registrarLeitura(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(registrada);
    }

    /**
     * GET /api/leituras/unidade/{unidadeId}/resumo - Obtem resumo de consumo
     */
    @GetMapping("/unidade/{unidadeId}/resumo")
    public ResponseEntity<ConsumoResumoDTO> obterResumo(
            @PathVariable Long unidadeId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataFim
    ) {
        return ResponseEntity.ok(service.obterResumoConsumo(unidadeId, dataInicio, dataFim));
    }
}
