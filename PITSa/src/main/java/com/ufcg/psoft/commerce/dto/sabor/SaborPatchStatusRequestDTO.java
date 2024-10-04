package PITSa.src.main.java.com.ufcg.psoft.commerce.dto.sabor;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jdk.jfr.BooleanFlag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaborPatchStatusRequestDTO {
    @Id
    @NotNull(message = "Id sabor obrigatorio")
    private Long saborId;

    @Id
    @NotNull(message = "id estabelecimento obrigatorio")
    private Long estabelecimentoId;

    @JsonProperty("codigoAcesso")
    @NotNull(message = "Codigo de acesso obrigatorio")
    @Pattern(regexp = "^\\d{6}$",
            message = "Codigo de acesso deve ter exatamente 6 digitos numericos")
    private String codigoAcesso;

    @JsonProperty("disponivel")
    @NotNull(message = "Codigo de acesso obrigatorio")
    @BooleanFlag
    private Boolean disponivel;
}
