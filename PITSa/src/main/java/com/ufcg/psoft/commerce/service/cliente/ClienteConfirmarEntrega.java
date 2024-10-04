package PITSa.src.main.java.com.ufcg.psoft.commerce.service.cliente;

import com.ufcg.psoft.commerce.service.pedido.IPedidoStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteConfirmarEntrega implements IClienteConfirmarEntrega{

    @Autowired
    private IPedidoStatusService pedidoStatusService;

    @Override
    public void confirmarEntrega(Long idPedido, Long clienteId, String codigoAcesso) {
        this.pedidoStatusService.clienteRecebePedido(clienteId, codigoAcesso, idPedido);
    }
}
