package PITSa.src.main.java.com.ufcg.psoft.commerce.model.cliente;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ufcg.psoft.commerce.model.pizza.Sabor;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Cliente {

    @JsonProperty("id")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @JsonProperty("nome")
    @Column(nullable = false)
    private String nome;

    @JsonIgnore
    @Column(name = "codigoAcesso", nullable = false, unique = true)
    private String codigoAcesso;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "endereco_id", referencedColumnName = "id")
    private Endereco endereco;


    @ManyToMany(mappedBy = "clientesInteressados", fetch = FetchType.LAZY)
    @JsonIgnore
    @Builder.Default
    private Set<Sabor> saboresDeInteresse = new HashSet<>();
}
