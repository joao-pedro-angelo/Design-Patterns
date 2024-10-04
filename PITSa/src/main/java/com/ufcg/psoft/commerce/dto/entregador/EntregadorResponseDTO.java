package PITSa.src.main.java.com.ufcg.psoft.commerce.dto.entregador;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ufcg.psoft.commerce.model.entregador.Entregador;
import com.ufcg.psoft.commerce.model.entregador.Veiculo;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EntregadorResponseDTO {
    @JsonProperty("id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEntregador;
    @NotBlank
    private String nomeEntregador;
    @Valid
    private Veiculo veiculoEntregador;

    public EntregadorResponseDTO(Entregador entregador){
        this.idEntregador = entregador.getIdEntregador();
        this.nomeEntregador = entregador.getNomeEntregador();
        this.veiculoEntregador = entregador.getVeiculoEntregador();
    }
}
