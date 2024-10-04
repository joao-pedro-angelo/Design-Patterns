package PITSa.src.main.java.com.ufcg.psoft.commerce.model.entregador;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class Veiculo {
    @NotBlank(message = "Placa do veiculo e obrigatoria")
    @JsonProperty("placaVeiculo")
    private String placaVeiculo;
    @NotBlank( message = "Cor do veiculo e obrigatoria")
    @JsonProperty("corVeiculo")
    private String corVeiculo;
    @JsonProperty("tipoVeiculo")
    @NotNull
    private TipoVeiculo tipoVeiculo;
}