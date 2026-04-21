package br.com.fiap.hydrosense.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Entidade que representa uma unidade de consumo de agua
 * (residencia ou comercio)
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "TB_UNIDADE_CONSUMO")
public class UnidadeConsumo {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "unidade_seq")
    @SequenceGenerator(name = "unidade_seq", sequenceName = "UNIDADE_CONSUMO_SEQ", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "endereco", nullable = false, length = 255)
    private String endereco;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false, length = 20)
    private TipoUnidade tipo;

    @Column(name = "numero_habitantes")
    private Integer numeroHabitantes;

    @Column(name = "area_m2", precision = 10, scale = 2)
    private BigDecimal areaM2;

    @Column(name = "data_cadastro")
    private LocalDateTime dataCadastro;

    @Column(name = "ativo")
    private Boolean ativo;

    @OneToMany(mappedBy = "unidadeConsumo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RegistroLeitura> leituras = new ArrayList<>();

    @OneToMany(mappedBy = "unidadeConsumo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Alerta> alertas = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        if (dataCadastro == null) {
            dataCadastro = LocalDateTime.now();
        }
        if (ativo == null) {
            ativo = true;
        }
    }
}
