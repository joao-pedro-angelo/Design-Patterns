package PITSa.src.main.java.com.ufcg.psoft.commerce.dto.entregador;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ufcg.psoft.commerce.model.entregador.Veiculo;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EntregadorPostRequestDTO {
    @JsonProperty("codigoAcessoEntregador")
    @NotBlank(message = "Código obrigatório")
    @Pattern(regexp = "^\\d{6}$",
            message = "Código de acesso deve ter exatamente 6 digitos numéricos")
    private String codigoAcessoEntregador;
    @NotBlank
    @JsonProperty("nomeEntregador")
    private String nomeEntregador;
    @Valid
    @JsonProperty("veiculoEntregador")
    private Veiculo veiculoEntregador;
}
