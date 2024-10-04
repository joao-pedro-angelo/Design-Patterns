package PITSa.src.main.java.com.ufcg.psoft.commerce.dto.entregador;

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
public class EntregadorPutDTO {
    @NotNull
    private Long idEntregador;
    @NotBlank
    @Pattern(regexp = "^\\d{6}$",
            message = "Código de acesso deve ter exatamente 6 digitos numéricos")
    private String atualCodigoEntregador;
    @NotBlank
    @Pattern(regexp = "^\\d{6}$",
            message = "Código de acesso deve ter exatamente 6 digitos numéricos")
    private String novoCodigoEntregador;
}
