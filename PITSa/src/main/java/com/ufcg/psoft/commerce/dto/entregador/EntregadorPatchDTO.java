package PITSa.src.main.java.com.ufcg.psoft.commerce.dto.entregador;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EntregadorPatchDTO {
    @NotNull(message = "disponiblidade não pode ser vazia!")
    private Boolean disponibilidade;
}