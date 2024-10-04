package PITSa.src.main.java.com.ufcg.psoft.commerce.model.entregador;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table(name = "tb_entregador")
@Entity(name = "Entregador")
public class Entregador {
    @JsonProperty("idEntregador")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long idEntregador;

    @JsonProperty("nomeEntregador")
    @Column(nullable = false)
    private String nomeEntregador;

    @Column(columnDefinition = "BOOLEAN DEFAULT true")
    @Builder.Default
    private Boolean disponibilidade = Boolean.TRUE;

    @Embedded
    @JsonProperty("veiculoEntregador")
    private Veiculo veiculoEntregador;

    @JsonProperty("codigoAcessoEntregador")
    @Column(name = "codigoAcessoEntregador", unique = true, nullable = false)
    private String codigoAcessoEntregador;

    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime updatedAt;

    @Transient
    private boolean disponibilidadeOriginal;

    @PostLoad
    public void armazenarDisponibilidadeOriginal() {
        this.disponibilidadeOriginal = this.disponibilidade;
    }

    @PreUpdate
    public void atualizarDataSeNecessario() {
        if (this.disponibilidade != this.disponibilidadeOriginal) {
            this.updatedAt = LocalDateTime.now();
        }
    }
}
