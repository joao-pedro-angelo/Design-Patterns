package PITSa.src.main.java.com.ufcg.psoft.commerce.dto.sabor;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ufcg.psoft.commerce.model.pizza.TipoSabor;
import com.ufcg.psoft.commerce.model.pizza.Sabor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaborResponseDTO {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("nome")
    private String nome;

    @JsonProperty("tipo")
    private TipoSabor tipo;

    @JsonProperty("valorGrande")
    private Double valorGrande;

    @JsonProperty("valorMedia")
    private Double valorMedia;

    @JsonProperty("disponivel")
    private Boolean disponivel;

    @JsonProperty("estabelecimentoId")
    private Long estabelecimentoId;

    public SaborResponseDTO(Sabor sabor) {
        this(sabor.getId(),
                sabor.getNome(),
                sabor.getTipo(),
                sabor.getValorGrande(),
                sabor.getValorMedia(),
                sabor.getDisponivel(),
                sabor.getEstabelecimento().getId()
        );
    }
}
