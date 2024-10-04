package PITSa.src.main.java.com.ufcg.psoft.commerce.dto.pedido;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PedidoPatchRequestDTO {
    @NotNull
    private Character tipoPagamento;
}