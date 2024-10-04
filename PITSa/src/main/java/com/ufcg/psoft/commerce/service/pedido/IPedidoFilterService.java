package PITSa.src.main.java.com.ufcg.psoft.commerce.service.pedido;

import com.ufcg.psoft.commerce.model.pedido.Pedido;
import com.ufcg.psoft.commerce.model.pedido.statePedido.StatePedido;

import java.util.List;

public interface IPedidoFilterService {
    public List<Pedido> filterPedidosByState(List<Pedido> pedidos, Integer statePedidoNumber);

    public List<Pedido> filterAndOrderAllPedidos(List<Pedido> pedidos);

    public Integer verificaStatus(String statusStr);

    public StatePedido retornaState(Integer statusNumber);

}
