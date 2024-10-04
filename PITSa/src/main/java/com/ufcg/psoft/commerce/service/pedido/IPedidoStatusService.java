package PITSa.src.main.java.com.ufcg.psoft.commerce.service.pedido;

import com.ufcg.psoft.commerce.dto.pedido.PedidoResponseDTO;
import com.ufcg.psoft.commerce.model.pedido.Pedido;

import java.util.List;

public interface IPedidoStatusService {
    public PedidoResponseDTO disparaPronto(Long estabelecimentoId, String codigoAcesso, Long pedidoId);
    public PedidoResponseDTO clienteRecebePedido(Long clienteId, String codigoAcesso, Long pedidoId);
    public PedidoResponseDTO atribuirEntregador(Long idEstabelecimento, String codigoAcesso, Long idPedido, Long idEntregador) throws Exception;
    public void cancelarPedidoCliente(Long id, Long clienteId, String codigoAcesso);

    public void atribuiPedido(Long entregadorId) throws Exception;
}
