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
@AllArgsConstructor
@NoArgsConstructor
public class AssociacaoPutDTO {
    @NotNull
    private Long idAssociacao;
    @NotNull
    private Long estabelecimentoId;
    @JsonProperty("codigoAcessoEstabelecimento")
    @NotBlank
    @Pattern(regexp = "^\\d{6}$",
            message = "Codigo de acesso deve ter exatamente 6 digitos numericos")
    private String codigoAcessoEstabelecimento;
    @NotNull
    private Boolean status;
}
