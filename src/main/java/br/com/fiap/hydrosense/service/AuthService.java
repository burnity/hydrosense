package br.com.fiap.hydrosense.service;

import br.com.fiap.hydrosense.config.JwtService;
import br.com.fiap.hydrosense.dto.AuthResponse;
import br.com.fiap.hydrosense.dto.LoginRequest;
import br.com.fiap.hydrosense.dto.RegisterRequest;
import br.com.fiap.hydrosense.exception.BusinessException;
import br.com.fiap.hydrosense.model.Role;
import br.com.fiap.hydrosense.model.Usuario;
import br.com.fiap.hydrosense.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Servico de autenticacao
 */
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    /**
     * Registra um novo usuario
     */
    @Transactional
    public AuthResponse register(RegisterRequest request) {
        // Verifica se o email ja esta cadastrado
        if (usuarioRepository.existsByEmail(request.getEmail())) {
            throw new BusinessException("Email ja cadastrado");
        }

        // Cria o usuario
        Usuario usuario = new Usuario();
        usuario.setNome(request.getNome());
        usuario.setEmail(request.getEmail());
        usuario.setSenha(passwordEncoder.encode(request.getSenha()));
        usuario.setRole(Role.USER);
        usuario.setAtivo(true);

        // Salva no banco
        usuario = usuarioRepository.save(usuario);

        // Gera o token JWT
        String jwtToken = jwtService.generateToken(usuario);

        return new AuthResponse(jwtToken, usuario.getEmail(), usuario.getNome());
    }

    /**
     * Autentica um usuario
     */
    public AuthResponse login(LoginRequest request) {
        // Autentica com Spring Security
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getSenha()
                )
        );

        // Busca o usuario
        Usuario usuario = usuarioRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BusinessException("Usuario nao encontrado"));

        // Gera o token JWT
        String jwtToken = jwtService.generateToken(usuario);

        return new AuthResponse(jwtToken, usuario.getEmail(), usuario.getNome());
    }
}
