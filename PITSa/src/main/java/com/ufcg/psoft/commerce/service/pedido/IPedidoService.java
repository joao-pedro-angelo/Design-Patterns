package PITSa.src.main.java.com.ufcg.psoft.commerce.service.pedido;

import com.ufcg.psoft.commerce.dto.pedido.PedidoPostPutRequestDTO;
import com.ufcg.psoft.commerce.dto.pedido.PedidoResponseDTO;
import com.ufcg.psoft.commerce.model.pedido.Pedido;

import java.util.List;

public interface IPedidoService {
    void apagarPedidoEstabelecimento(Long id, Long estabelecimentoId, String codigoAcesso);

    Pedido getPedido(Long id);

    Pedido validaPedidoEstabelecimento(Long pedidoId, Long estabelecimentoId);

    List<PedidoResponseDTO> buscarPedidosEstabelecimento(Long estabelecimentoId, String codigoAcesso);

    PedidoResponseDTO buscarPedidoEstabelecimento(Long estabelecimentoId, String codigoAcesso, Long pedidoId);

    PedidoResponseDTO cadastraPedido(Long clienteId,
                                     String codigoAcesso,
                                     Long estabelecimentoId,
                                     PedidoPostPutRequestDTO pedidoPostPutRequestDTO);

    PedidoResponseDTO atualizaPedido(Long pedidoId, Long clienteId, String codigoAcesso, PedidoPostPutRequestDTO pedidoPostPutRequestDTO);
}
