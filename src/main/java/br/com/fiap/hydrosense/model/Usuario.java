package br.com.fiap.hydrosense.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

/**
 * Entidade que representa um usuario do sistema
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "TB_USUARIO")
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usuario_seq")
    @SequenceGenerator(name = "usuario_seq", sequenceName = "USUARIO_SEQ", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "nome", nullable = false, length = 100)
    private String nome;

    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email;

    @Column(name = "senha", nullable = false, length = 255)
    private String senha;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false, length = 20)
    private Role role = Role.USER;

    @Column(name = "ativo")
    private Boolean ativo = true;

    @Column(name = "data_cadastro")
    private LocalDateTime dataCadastro;

    @PrePersist
    protected void onCreate() {
        if (dataCadastro == null) {
            dataCadastro = LocalDateTime.now();
        }
        if (ativo == null) {
            ativo = true;
        }
        if (role == null) {
            role = Role.USER;
        }
    }

    // Implementacao do UserDetails para Spring Security
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return ativo;
    }
}
