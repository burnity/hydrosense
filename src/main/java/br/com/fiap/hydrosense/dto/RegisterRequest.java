package br.com.fiap.hydrosense.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para requisicao de registro de novo usuario
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {

    @NotBlank(message = "Nome e obrigatorio")
    @Size(min = 3, max = 100, message = "Nome deve ter entre 3 e 100 caracteres")
    private String nome;

    @NotBlank(message = "Email e obrigatorio")
    @Email(message = "Email invalido")
    private String email;

    @NotBlank(message = "Senha e obrigatoria")
    @Size(min = 6, message = "Senha deve ter no minimo 6 caracteres")
    private String senha;
}
