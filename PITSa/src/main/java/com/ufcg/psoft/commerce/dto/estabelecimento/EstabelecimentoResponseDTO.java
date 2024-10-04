package PITSa.src.main.java.com.ufcg.psoft.commerce.dto.estabelecimento;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ufcg.psoft.commerce.model.estabelecimento.Estabelecimento;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EstabelecimentoResponseDTO {
    @JsonProperty("id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty("codigoAcessoEstabelecimento")
    @NotBlank
    @Size(min = 6, max = 6)
    private String codigoAcessoEstabelecimento;

    public EstabelecimentoResponseDTO(Estabelecimento estabelecimento){
        this.id = estabelecimento.getId();
        this.codigoAcessoEstabelecimento =
                estabelecimento.getCodigoAcessoEstabelecimento();
    }
}
