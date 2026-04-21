package br.com.fiap.hydrosense.controller;

import br.com.fiap.hydrosense.dto.AuthResponse;
import br.com.fiap.hydrosense.dto.LoginRequest;
import br.com.fiap.hydrosense.dto.RegisterRequest;
import br.com.fiap.hydrosense.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller para autenticacao
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    /**
     * Registra um novo usuario
     */
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        AuthResponse response = authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Autentica um usuario
     */
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        AuthResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }

    /**
     * Endpoint de teste para verificar se o usuario esta autenticado
     */
    @GetMapping("/me")
    public ResponseEntity<String> me() {
        return ResponseEntity.ok("Voce esta autenticado!");
    }
}
