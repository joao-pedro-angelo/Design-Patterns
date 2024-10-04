package PITSa.src.main.java.com.ufcg.psoft.commerce.dto.associacao;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AssociacaoPostDTO {
    @NotNull
    @JsonProperty("idEntregador")
    private Long idEntregador;
    @NotBlank
    @JsonProperty("codigoAcessoEntregador")
    @Pattern(regexp = "^\\d{6}$",
            message = "Codigo de acesso deve ter exatamente 6 digitos numericos")
    private String codigoAcessoEntregador;
    @NotNull
    private Long estabelecimentoId;
}
