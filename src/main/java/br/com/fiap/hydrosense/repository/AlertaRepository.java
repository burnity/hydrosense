package br.com.fiap.hydrosense.repository;

import br.com.fiap.hydrosense.model.Alerta;
import br.com.fiap.hydrosense.model.StatusAlerta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository para a entidade Alerta
 */
@Repository
public interface AlertaRepository extends JpaRepository<Alerta, Long> {

    /**
     * Busca alertas por unidade de consumo
     */
    List<Alerta> findByUnidadeConsumoId(Long unidadeId);

    /**
     * Busca alertas por status
     */
    List<Alerta> findByStatus(StatusAlerta status);

    /**
     * Busca alertas ativos de uma unidade
     */
    List<Alerta> findByUnidadeConsumoIdAndStatus(Long unidadeId, StatusAlerta status);

    /**
     * Busca alertas criados em um periodo especifico
     */
    @Query("SELECT a FROM Alerta a WHERE a.dataCriacao BETWEEN :dataInicio AND :dataFim " +
           "ORDER BY a.dataCriacao DESC")
    List<Alerta> findByPeriodo(
            @Param("dataInicio") LocalDateTime dataInicio,
            @Param("dataFim") LocalDateTime dataFim
    );

    /**
     * Conta alertas ativos por unidade
     */
    @Query("SELECT COUNT(a) FROM Alerta a WHERE a.unidadeConsumo.id = :unidadeId " +
           "AND a.status = 'ATIVO'")
    Long countAlertasAtivosByUnidadeId(@Param("unidadeId") Long unidadeId);
}
