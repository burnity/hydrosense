package br.com.fiap.hydrosense.service;

import br.com.fiap.hydrosense.dto.AlertaResponseDTO;
import br.com.fiap.hydrosense.exception.BusinessException;
import br.com.fiap.hydrosense.exception.ResourceNotFoundException;
import br.com.fiap.hydrosense.model.Alerta;
import br.com.fiap.hydrosense.model.StatusAlerta;
import br.com.fiap.hydrosense.repository.AlertaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service para gerenciamento de alertas
 */
@Service
@RequiredArgsConstructor
public class AlertaService {

    private final AlertaRepository repository;

    /**
     * Lista todos os alertas
     */
    @Transactional(readOnly = true)
    public List<AlertaResponseDTO> listarTodos() {
        return repository.findAll()
                .stream()
                .map(AlertaResponseDTO::new)
                .collect(Collectors.toList());
    }

    /**
     * Lista alertas ativos
     */
    @Transactional(readOnly = true)
    public List<AlertaResponseDTO> listarAtivos() {
        return repository.findByStatus(StatusAlerta.ATIVO)
                .stream()
                .map(AlertaResponseDTO::new)
                .collect(Collectors.toList());
    }

    /**
     * Lista alertas de uma unidade especifica
     */
    @Transactional(readOnly = true)
    public List<AlertaResponseDTO> listarPorUnidade(Long unidadeId) {
        return repository.findByUnidadeConsumoId(unidadeId)
                .stream()
                .map(AlertaResponseDTO::new)
                .collect(Collectors.toList());
    }

    /**
     * Busca alerta por ID
     */
    @Transactional(readOnly = true)
    public AlertaResponseDTO buscarPorId(Long id) {
        Alerta alerta = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Alerta", id));
        return new AlertaResponseDTO(alerta);
    }

    /**
     * Resolve um alerta (marca como RESOLVIDO)
     */
    @Transactional
    public AlertaResponseDTO resolverAlerta(Long id) {
        Alerta alerta = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Alerta", id));

        if (alerta.getStatus() != StatusAlerta.ATIVO) {
            throw new BusinessException("Apenas alertas ativos podem ser resolvidos");
        }

        alerta.setStatus(StatusAlerta.RESOLVIDO);
        alerta.setDataResolucao(LocalDateTime.now());

        Alerta resolvido = repository.save(alerta);
        return new AlertaResponseDTO(resolvido);
    }

    /**
     * Ignora um alerta (marca como IGNORADO)
     */
    @Transactional
    public AlertaResponseDTO ignorarAlerta(Long id) {
        Alerta alerta = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Alerta", id));

        if (alerta.getStatus() != StatusAlerta.ATIVO) {
            throw new BusinessException("Apenas alertas ativos podem ser ignorados");
        }

        alerta.setStatus(StatusAlerta.IGNORADO);
        alerta.setDataResolucao(LocalDateTime.now());

        Alerta ignorado = repository.save(alerta);
        return new AlertaResponseDTO(ignorado);
    }

    /**
     * Deleta um alerta
     */
    @Transactional
    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Alerta", id);
        }
        repository.deleteById(id);
    }
}
