package PITSa.src.main.java.com.ufcg.psoft.commerce.service.notificacao;

import com.ufcg.psoft.commerce.model.pedido.Pedido;


public interface IPedidoNotificacaoService {
    void notificarCliente(Pedido pedido);
    void notificarEstabelecimentoPedidoEntregue(Pedido pedido);
    void notificarIndisponibilidadeNaEntrega(String nomeCliente);
}
