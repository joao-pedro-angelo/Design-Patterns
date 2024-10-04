package PITSa.src.main.java.com.ufcg.psoft.commerce.dto.estabelecimento;

import jakarta.persistence.Id;
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
public class EstabelecimentoPutDTO {
    @Id
    @NotNull
    private Long id;
    @NotBlank
    @Pattern(regexp = "^\\d{6}$",
            message = "Codigo de acesso deve ter exatamente 6 digitos numericos")
    private String atualCodigoEstabelecimento;
    @NotBlank
    @Pattern(regexp = "^\\d{6}$",
            message = "Codigo de acesso deve ter exatamente 6 digitos numericos")
    private String novoCodigoEstabelecimento;
}
