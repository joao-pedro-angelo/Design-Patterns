package PITSa.src.main.java.com.ufcg.psoft.commerce.dto.estabelecimento;

import com.fasterxml.jackson.annotation.JsonProperty;
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
public class EstabelecimentoPostRequestDTO {
    @JsonProperty("codigoAcessoEstabelecimento")
    @NotBlank(message = "Código obrigatório")
    @Pattern(regexp = "^\\d{6}$",
            message = "Codigo de acesso deve ter exatamente 6 digitos numericos")
    private String codigoAcessoEstabelecimento;
}
