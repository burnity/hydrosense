package br.com.fiap.hydrosense.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para resposta de autenticacao
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {

    private String token;
    private String type = "Bearer";
    private String email;
    private String nome;

    public AuthResponse(String token, String email, String nome) {
        this.token = token;
        this.type = "Bearer";
        this.email = email;
        this.nome = nome;
    }
}
