package PITSa.src.main.java.com.ufcg.psoft.commerce.model.estabelecimento;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ufcg.psoft.commerce.model.pedido.Pedido;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Table(name = "tb_estabelecimento")
@Entity(name = "Estabelecimento")
public class Estabelecimento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    private Long id;

    @JsonProperty("codigoAcessoEstabelecimento")
    @Column(name = "codigoAcessoEstabelecimento", unique = true)
    private String codigoAcessoEstabelecimento;

    @OneToMany(mappedBy = "estabelecimento", cascade = {CascadeType.REFRESH, CascadeType.REMOVE}, fetch = FetchType.LAZY)
    @Builder.Default
    @ToString.Exclude
    private Set<Pedido> pedidos = new HashSet<>();
}
