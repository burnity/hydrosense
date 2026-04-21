package br.com.fiap.hydrosense.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Entidade que representa um registro de leitura de consumo de agua
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "TB_REGISTRO_LEITURA")
public class RegistroLeitura {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "leitura_seq")
    @SequenceGenerator(name = "leitura_seq", sequenceName = "REGISTRO_LEITURA_SEQ", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unidade_id", nullable = false)
    private UnidadeConsumo unidadeConsumo;

    @Column(name = "volume_m3", nullable = false, precision = 10, scale = 3)
    private BigDecimal volumeM3;

    @Column(name = "data_hora_leitura")
    private LocalDateTime dataHoraLeitura;

    @Column(name = "fonte_leitura", length = 50)
    private String fonteLeitura;

    @Column(name = "observacao", length = 500)
    private String observacao;

    @PrePersist
    protected void onCreate() {
        if (dataHoraLeitura == null) {
            dataHoraLeitura = LocalDateTime.now();
        }
        if (fonteLeitura == null) {
            fonteLeitura = "SENSOR";
        }
    }
}
