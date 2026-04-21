package br.com.fiap.hydrosense.service;

import br.com.fiap.hydrosense.dto.UnidadeConsumoRequestDTO;
import br.com.fiap.hydrosense.dto.UnidadeConsumoResponseDTO;
import br.com.fiap.hydrosense.exception.ResourceNotFoundException;
import br.com.fiap.hydrosense.model.TipoUnidade;
import br.com.fiap.hydrosense.model.UnidadeConsumo;
import br.com.fiap.hydrosense.repository.UnidadeConsumoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service para gerenciamento de unidades de consumo
 */
@Service
@RequiredArgsConstructor
public class UnidadeConsumoService {

    private final UnidadeConsumoRepository repository;

    /**
     * Lista todas as unidades de consumo
     */
    @Transactional(readOnly = true)
    public List<UnidadeConsumoResponseDTO> listarTodas() {
        return repository.findAll()
                .stream()
                .map(UnidadeConsumoResponseDTO::new)
                .collect(Collectors.toList());
    }

    /**
     * Lista unidades de consumo ativas
     */
    @Transactional(readOnly = true)
    public List<UnidadeConsumoResponseDTO> listarAtivas() {
        return repository.findByAtivoTrue()
                .stream()
                .map(UnidadeConsumoResponseDTO::new)
                .collect(Collectors.toList());
    }

    /**
     * Lista unidades por tipo
     */
    @Transactional(readOnly = true)
    public List<UnidadeConsumoResponseDTO> listarPorTipo(TipoUnidade tipo) {
        return repository.findByTipoAndAtivoTrue(tipo)
                .stream()
                .map(UnidadeConsumoResponseDTO::new)
                .collect(Collectors.toList());
    }

    /**
     * Busca unidade por ID
     */
    @Transactional(readOnly = true)
    public UnidadeConsumoResponseDTO buscarPorId(Long id) {
        UnidadeConsumo unidade = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Unidade de Consumo", id));
        return new UnidadeConsumoResponseDTO(unidade);
    }

    /**
     * Cria uma nova unidade de consumo
     */
    @Transactional
    public UnidadeConsumoResponseDTO criar(UnidadeConsumoRequestDTO dto) {
        UnidadeConsumo unidade = new UnidadeConsumo();
        unidade.setEndereco(dto.endereco());
        unidade.setTipo(dto.tipo());
        unidade.setNumeroHabitantes(dto.numeroHabitantes());
        unidade.setAreaM2(dto.areaM2());

        UnidadeConsumo salva = repository.save(unidade);
        return new UnidadeConsumoResponseDTO(salva);
    }

    /**
     * Atualiza uma unidade de consumo existente
     */
    @Transactional
    public UnidadeConsumoResponseDTO atualizar(Long id, UnidadeConsumoRequestDTO dto) {
        UnidadeConsumo unidade = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Unidade de Consumo", id));

        unidade.setEndereco(dto.endereco());
        unidade.setTipo(dto.tipo());
        unidade.setNumeroHabitantes(dto.numeroHabitantes());
        unidade.setAreaM2(dto.areaM2());

        UnidadeConsumo atualizada = repository.save(unidade);
        return new UnidadeConsumoResponseDTO(atualizada);
    }

    /**
     * Desativa uma unidade de consumo (soft delete)
     */
    @Transactional
    public void desativar(Long id) {
        UnidadeConsumo unidade = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Unidade de Consumo", id));
        unidade.setAtivo(false);
        repository.save(unidade);
    }

    /**
     * Busca uma unidade internamente (para uso em outros services)
     */
    @Transactional(readOnly = true)
    public UnidadeConsumo buscarUnidadeInterna(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Unidade de Consumo", id));
    }
}
