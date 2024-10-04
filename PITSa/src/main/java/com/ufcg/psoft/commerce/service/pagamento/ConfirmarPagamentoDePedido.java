package PITSa.src.main.java.com.ufcg.psoft.commerce.service.pagamento;

import com.ufcg.psoft.commerce.dto.pedido.PedidoPatchRequestDTO;
import com.ufcg.psoft.commerce.dto.pedido.PedidoResponseDTO;
import com.ufcg.psoft.commerce.exception.pedido.PedidoClienteDoesntMatchException;
import com.ufcg.psoft.commerce.model.cliente.Cliente;
import com.ufcg.psoft.commerce.model.pagamento.*;
import com.ufcg.psoft.commerce.model.pedido.Pedido;
import com.ufcg.psoft.commerce.repository.PedidoRepository;
import com.ufcg.psoft.commerce.service.cliente.IClienteService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class ConfirmarPagamentoDePedido implements IConfirmarPagamentoDePedido {

    @Autowired
    private PedidoRepository pedidoRepository;
    @Autowired
    private IClienteService clienteService;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public PedidoResponseDTO confirmarPagamento(Long clienteId, String codigoAcesso, Long pedidoId, PedidoPatchRequestDTO pedidoPatchRequestDTO) {
        Cliente c = clienteService.recuperarComCodigoAcesso(clienteId, codigoAcesso);
        Pedido pedido = this.pedidoRepository.findPedidoById(pedidoId);

        if(!c.getCodigoAcesso().equals(pedido.getCliente().getCodigoAcesso())){
            throw  new PedidoClienteDoesntMatchException();
        }
        if(!c.getId().equals(pedido.getCliente().getId())){
            throw  new PedidoClienteDoesntMatchException();
        }

        Character charTipoPagamento = pedidoPatchRequestDTO.getTipoPagamento();
        HashMap<Character, TipoPagamento> tipoPagamento = new HashMap<>();
        tipoPagamento.put('C', CartaoCredito.builder().build());
        tipoPagamento.put('D', CartaoDebito.builder().build());
        tipoPagamento.put('P', Pix.builder().build());

        Pagamento pagamento = Pagamento.builder().tipoPagamento(tipoPagamento.get(charTipoPagamento)).build();
        pagamento.setValorPagamento(pedido.getTotal());
        pagamento.efetuaPagamento();
        pedido.setPagamento(pagamento);
        pedido.confirmaPagamento();
        pedidoRepository.flush();

        return modelMapper.map(pedido, PedidoResponseDTO.class);
    }
}
