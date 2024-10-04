package PITSa.src.main.java.com.ufcg.psoft.commerce.service.notificacao;

import com.ufcg.psoft.commerce.model.cliente.Cliente;
import com.ufcg.psoft.commerce.model.entregador.Entregador;
import com.ufcg.psoft.commerce.model.estabelecimento.Estabelecimento;
import com.ufcg.psoft.commerce.model.pedido.Pedido;
import org.springframework.stereotype.Service;

@Service
public class PedidoNotificacaoService implements IPedidoNotificacaoService {

    @Override
    public void notificarCliente(Pedido pedido) {
        Cliente cliente = pedido.getCliente();
        Entregador entregador = pedido.getEntregador();

        String mensagem = String.format(
                "-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*\n" +
                        "Notificação para o cliente %s:\nO seu pedido está em rota. " +
                        "O entregador é %s, com o veículo %s.\n" +
                "-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*",
                cliente.getNome(),
                entregador.getNomeEntregador(),
                entregador.getVeiculoEntregador()
        );

        System.out.println(mensagem);
    }

    @Override
    public void notificarEstabelecimentoPedidoEntregue(Pedido pedido) {
        Estabelecimento estabelecimento = pedido.getEstabelecimento();
        String mensagem = String.format(
                "-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*\n" +
                        "Notificação para o estabelecimento %s:\n" +
                        "O pedido foi entregue: id: %s:\n" +
                        "-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*",
                estabelecimento.getId(),
                pedido.getId()
        );

        System.out.println(mensagem);

    }

    @Override
    public void notificarIndisponibilidadeNaEntrega(String nomeCliente) {
        String mensagem = "Você, "+ nomeCliente+ ", será reembolsado. Houve um problema na entrega e" +
                " não poderemos efetuar teu pedido por falta de entregadores.";
        System.out.println(mensagem);
    }
}
