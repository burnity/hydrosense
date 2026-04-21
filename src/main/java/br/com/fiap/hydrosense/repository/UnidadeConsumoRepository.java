package br.com.fiap.hydrosense.repository;

import br.com.fiap.hydrosense.model.TipoUnidade;
import br.com.fiap.hydrosense.model.UnidadeConsumo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository para a entidade UnidadeConsumo
 */
@Repository
public interface UnidadeConsumoRepository extends JpaRepository<UnidadeConsumo, Long> {

    /**
     * Busca unidades de consumo por tipo
     */
    List<UnidadeConsumo> findByTipo(TipoUnidade tipo);

    /**
     * Busca unidades de consumo ativas
     */
    List<UnidadeConsumo> findByAtivoTrue();

    /**
     * Busca unidades de consumo por tipo e status ativo
     */
    List<UnidadeConsumo> findByTipoAndAtivoTrue(TipoUnidade tipo);

    /**
     * Busca unidades por endereco (busca parcial)
     */
    @Query("SELECT u FROM UnidadeConsumo u WHERE LOWER(u.endereco) LIKE LOWER(CONCAT('%', :endereco, '%'))")
    List<UnidadeConsumo> buscarPorEndereco(@Param("endereco") String endereco);
}
