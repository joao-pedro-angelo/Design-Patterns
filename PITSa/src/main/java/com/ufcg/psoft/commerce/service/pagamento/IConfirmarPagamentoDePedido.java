package PITSa.src.main.java.com.ufcg.psoft.commerce.service.pagamento;

import com.ufcg.psoft.commerce.dto.pedido.PedidoPatchRequestDTO;
import com.ufcg.psoft.commerce.dto.pedido.PedidoResponseDTO;

@FunctionalInterface
public interface IConfirmarPagamentoDePedido {
    PedidoResponseDTO confirmarPagamento(Long clienteId,
                                         String codigoAcesso,
                                         Long pedidoId,
                                         PedidoPatchRequestDTO pedidoPatchRequestDTO);
}
