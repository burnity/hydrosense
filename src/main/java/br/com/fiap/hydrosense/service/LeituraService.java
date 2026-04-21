package br.com.fiap.hydrosense.service;

import br.com.fiap.hydrosense.dto.ConsumoResumoDTO;
import br.com.fiap.hydrosense.dto.LeituraRequestDTO;
import br.com.fiap.hydrosense.dto.LeituraResponseDTO;
import br.com.fiap.hydrosense.exception.BusinessException;
import br.com.fiap.hydrosense.exception.ResourceNotFoundException;
import br.com.fiap.hydrosense.model.Alerta;
import br.com.fiap.hydrosense.model.RegistroLeitura;
import br.com.fiap.hydrosense.model.StatusAlerta;
import br.com.fiap.hydrosense.model.UnidadeConsumo;
import br.com.fiap.hydrosense.repository.AlertaRepository;
import br.com.fiap.hydrosense.repository.RegistroLeituraRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service para gerenciamento de leituras de consumo
 */
@Service
@RequiredArgsConstructor
public class LeituraService {

    private final RegistroLeituraRepository leituraRepository;
    private final AlertaRepository alertaRepository;
    private final UnidadeConsumoService unidadeService;

    // Limite de consumo diario considerado excessivo (em m3)
    private static final BigDecimal LIMITE_CONSUMO_EXCESSIVO = new BigDecimal("1.000");

    /**
     * Lista todas as leituras
     */
    @Transactional(readOnly = true)
    public List<LeituraResponseDTO> listarTodas() {
        return leituraRepository.findAll()
                .stream()
                .map(LeituraResponseDTO::new)
                .collect(Collectors.toList());
    }

    /**
     * Busca leitura por ID
     */
    @Transactional(readOnly = true)
    public LeituraResponseDTO buscarPorId(Long id) {
        RegistroLeitura leitura = leituraRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Registro de Leitura", id));
        return new LeituraResponseDTO(leitura);
    }

    /**
     * Lista leituras de uma unidade especifica
     */
    @Transactional(readOnly = true)
    public List<LeituraResponseDTO> listarPorUnidade(Long unidadeId) {
        // Verifica se a unidade existe
        unidadeService.buscarPorId(unidadeId);

        return leituraRepository.findByUnidadeConsumoId(unidadeId)
                .stream()
                .map(LeituraResponseDTO::new)
                .collect(Collectors.toList());
    }

    /**
     * Registra uma nova leitura de consumo
     * Verifica se o consumo e excessivo e cria alerta se necessario
     */
    @Transactional
    public LeituraResponseDTO registrarLeitura(LeituraRequestDTO dto) {
        // Busca a unidade de consumo
        UnidadeConsumo unidade = unidadeService.buscarUnidadeInterna(dto.unidadeId());

        if (!unidade.getAtivo()) {
            throw new BusinessException("Nao e possivel registrar leitura para unidade inativa");
        }

        // Cria o registro de leitura
        RegistroLeitura leitura = new RegistroLeitura();
        leitura.setUnidadeConsumo(unidade);
        leitura.setVolumeM3(dto.volumeM3());
        leitura.setDataHoraLeitura(dto.dataHoraLeitura() != null ? dto.dataHoraLeitura() : LocalDateTime.now());
        leitura.setObservacao(dto.observacao());

        RegistroLeitura salva = leituraRepository.save(leitura);

        // Verifica se o consumo e excessivo e cria alerta
        verificarConsumoExcessivo(salva);

        return new LeituraResponseDTO(salva);
    }

    /**
     * Calcula resumo de consumo de uma unidade em um periodo
     */
    @Transactional(readOnly = true)
    public ConsumoResumoDTO obterResumoConsumo(Long unidadeId, LocalDateTime dataInicio, LocalDateTime dataFim) {
        UnidadeConsumo unidade = unidadeService.buscarUnidadeInterna(unidadeId);

        // Busca leituras no periodo
        List<RegistroLeitura> leituras = leituraRepository.findByUnidadeIdAndPeriodo(
                unidadeId, dataInicio, dataFim
        );

        // Calcula consumo total
        BigDecimal consumoTotal = leituraRepository.calcularConsumoTotal(
                unidadeId, dataInicio, dataFim
        );

        // Calcula media diaria
        long diasPeriodo = java.time.temporal.ChronoUnit.DAYS.between(dataInicio, dataFim) + 1;
        BigDecimal mediaDiaria = consumoTotal.divide(
                BigDecimal.valueOf(diasPeriodo), 3, RoundingMode.HALF_UP
        );

        // Conta alertas ativos
        Long alertasAtivos = alertaRepository.countAlertasAtivosByUnidadeId(unidadeId);

        return new ConsumoResumoDTO(
                unidadeId,
                unidade.getEndereco(),
                consumoTotal,
                leituras.size(),
                mediaDiaria,
                alertasAtivos
        );
    }

    /**
     * Verifica se o consumo e excessivo e cria alerta se necessario
     */
    private void verificarConsumoExcessivo(RegistroLeitura leitura) {
        if (leitura.getVolumeM3().compareTo(LIMITE_CONSUMO_EXCESSIVO) > 0) {
            Alerta alerta = new Alerta();
            alerta.setUnidadeConsumo(leitura.getUnidadeConsumo());
            alerta.setRegistroLeitura(leitura);
            alerta.setTipoAlerta("CONSUMO_EXCESSIVO");
            alerta.setMensagem(String.format(
                    "Consumo excessivo detectado: %.3f m3. Limite: %.3f m3",
                    leitura.getVolumeM3(),
                    LIMITE_CONSUMO_EXCESSIVO
            ));
            alerta.setStatus(StatusAlerta.ATIVO);

            alertaRepository.save(alerta);
        }
    }
}
