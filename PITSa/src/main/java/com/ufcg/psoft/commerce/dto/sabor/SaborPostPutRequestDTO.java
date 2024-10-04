package PITSa.src.main.java.com.ufcg.psoft.commerce.dto.sabor;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ufcg.psoft.commerce.model.pizza.TipoSabor;
import com.ufcg.psoft.commerce.model.estabelecimento.Estabelecimento;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaborPostPutRequestDTO {
    @NotEmpty(message = "nome não pode ser nulo ou vazio")
    @JsonProperty("nome")
    private String nome;

    @JsonProperty("tipo")
    private TipoSabor tipo;

    @NotNull(message = "valorGrande não pode ser nulo")
    @JsonProperty("valorGrande")
    private Double valorGrande;

    @NotNull(message = "valorMedia não pode ser nulo")
    @JsonProperty("valorMedia")
    private Double valorMedia;

    @JsonProperty("disponivel")
    private Boolean disponivel;

    @JsonProperty("estabelecimento")
    private Estabelecimento estabelecimento;
}
