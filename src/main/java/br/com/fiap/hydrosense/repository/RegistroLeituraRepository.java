package br.com.fiap.hydrosense.repository;

import br.com.fiap.hydrosense.model.RegistroLeitura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository para a entidade RegistroLeitura
 */
@Repository
public interface RegistroLeituraRepository extends JpaRepository<RegistroLeitura, Long> {

    /**
     * Busca leituras por unidade de consumo
     */
    List<RegistroLeitura> findByUnidadeConsumoId(Long unidadeId);

    /**
     * Busca leituras por unidade em um periodo especifico
     */
    @Query("SELECT r FROM RegistroLeitura r WHERE r.unidadeConsumo.id = :unidadeId " +
           "AND r.dataHoraLeitura BETWEEN :dataInicio AND :dataFim " +
           "ORDER BY r.dataHoraLeitura DESC")
    List<RegistroLeitura> findByUnidadeIdAndPeriodo(
            @Param("unidadeId") Long unidadeId,
            @Param("dataInicio") LocalDateTime dataInicio,
            @Param("dataFim") LocalDateTime dataFim
    );

    /**
     * Calcula o consumo total de uma unidade em um periodo
     */
    @Query("SELECT COALESCE(SUM(r.volumeM3), 0) FROM RegistroLeitura r " +
           "WHERE r.unidadeConsumo.id = :unidadeId " +
           "AND r.dataHoraLeitura BETWEEN :dataInicio AND :dataFim")
    BigDecimal calcularConsumoTotal(
            @Param("unidadeId") Long unidadeId,
            @Param("dataInicio") LocalDateTime dataInicio,
            @Param("dataFim") LocalDateTime dataFim
    );

    /**
     * Busca as ultimas N leituras de uma unidade
     */
    @Query("SELECT r FROM RegistroLeitura r WHERE r.unidadeConsumo.id = :unidadeId " +
           "ORDER BY r.dataHoraLeitura DESC")
    List<RegistroLeitura> findTopNByUnidadeId(@Param("unidadeId") Long unidadeId);
}
