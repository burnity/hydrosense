package br.com.fiap.hydrosense.repository;

import br.com.fiap.hydrosense.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository para gerenciar usuarios
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    /**
     * Busca usuario por email
     * @param email Email do usuario
     * @return Optional com o usuario se encontrado
     */
    Optional<Usuario> findByEmail(String email);

    /**
     * Verifica se existe usuario com o email informado
     * @param email Email a verificar
     * @return true se existe, false caso contrario
     */
    boolean existsByEmail(String email);
}
