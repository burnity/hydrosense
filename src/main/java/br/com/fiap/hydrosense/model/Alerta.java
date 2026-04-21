package br.com.fiap.hydrosense.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Entidade que representa um alerta de consumo excessivo ou anormal
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "TB_ALERTA")
public class Alerta {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "alerta_seq")
    @SequenceGenerator(name = "alerta_seq", sequenceName = "ALERTA_SEQ", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unidade_id", nullable = false)
    private UnidadeConsumo unidadeConsumo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "leitura_id")
    private RegistroLeitura registroLeitura;

    @Column(name = "tipo_alerta", nullable = false, length = 50)
    private String tipoAlerta;

    @Column(name = "mensagem", nullable = false, length = 500)
    private String mensagem;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 20)
    private StatusAlerta status;

    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao;

    @Column(name = "data_resolucao")
    private LocalDateTime dataResolucao;

    @PrePersist
    protected void onCreate() {
        if (dataCriacao == null) {
            dataCriacao = LocalDateTime.now();
        }
        if (status == null) {
            status = StatusAlerta.ATIVO;
        }
    }
}
