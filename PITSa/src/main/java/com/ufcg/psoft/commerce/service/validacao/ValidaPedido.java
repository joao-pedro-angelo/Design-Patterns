package PITSa.src.main.java.com.ufcg.psoft.commerce.service.validacao;

import com.ufcg.psoft.commerce.exception.cliente.ClienteNaoExisteException;
import com.ufcg.psoft.commerce.exception.pedido.PedidoNotFoundException;
import com.ufcg.psoft.commerce.model.cliente.Cliente;
import com.ufcg.psoft.commerce.model.pedido.Pedido;
import com.ufcg.psoft.commerce.repository.PedidoRepository;
import com.ufcg.psoft.commerce.service.cliente.IClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ValidaPedido implements IValidaPedido {

    @Autowired
    private PedidoRepository pedidoRepository;
    @Autowired
    private IClienteService clienteService;

    @Override
    public void validaPedido(Long pedidoId, Long clienteId, String codigoAcesso) {
        Pedido pedido = this.pedidoRepository.findPedidoById(pedidoId);
        Cliente cliente = this.clienteService
                .recuperarComCodigoAcesso(clienteId, codigoAcesso);

        if (pedido == null){
            throw new PedidoNotFoundException();
        }
        if (cliente == null){
            throw new ClienteNaoExisteException();
        }
    }
}
