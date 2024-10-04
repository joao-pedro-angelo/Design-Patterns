package PITSa.src.main.java.com.ufcg.psoft.commerce.model.pizza;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ufcg.psoft.commerce.dto.sabor.SaborPostPutRequestDTO;
import com.ufcg.psoft.commerce.model.cliente.Cliente;
import com.ufcg.psoft.commerce.model.estabelecimento.Estabelecimento;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Sabor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name="tipo" ,nullable = false)
    private TipoSabor tipo;

    @Column(name="nome" ,nullable = false)
    private String nome;

    @Column(name="valor_pequeno" , nullable = false)
    private Double valorMedia;

    @Column(name="valor_grande" , nullable = false)
    private Double valorGrande;

    @Column(name="disponivel", nullable = false)
    private Boolean disponivel;

    @JsonProperty("estabelecimento")
    @ManyToOne(fetch = FetchType.EAGER,  cascade = CascadeType.REFRESH)
    @JoinColumn(name = "estabelecimento_id")
    private Estabelecimento estabelecimento;

    @ManyToMany
    @JsonBackReference
    @Builder.Default
    @JoinTable(
            name = "sabor_cliente",
            joinColumns = @JoinColumn(name = "sabor_id"),
            inverseJoinColumns = @JoinColumn(name = "cliente_id"))
    private List<Cliente> clientesInteressados = new ArrayList<>();

    public Sabor(SaborPostPutRequestDTO saborPayload) {
        this.tipo = saborPayload.getTipo();
        this.nome = saborPayload.getNome();
        this.valorMedia = saborPayload.getValorMedia();
        this.valorGrande = saborPayload.getValorGrande();
        this.disponivel = (saborPayload.getDisponivel() != null) ? saborPayload.getDisponivel() : true;
        this.estabelecimento = saborPayload.getEstabelecimento();
    }
}
