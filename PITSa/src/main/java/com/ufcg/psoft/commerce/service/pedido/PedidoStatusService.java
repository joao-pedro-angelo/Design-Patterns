package PITSa.src.main.java.com.ufcg.psoft.commerce.service.pedido;

import com.ufcg.psoft.commerce.dto.pedido.PedidoResponseDTO;
import com.ufcg.psoft.commerce.exception.pedido.PedidoClienteDoesntMatchException;
import com.ufcg.psoft.commerce.model.cliente.Cliente;
import com.ufcg.psoft.commerce.model.entregador.Veiculo;
import com.ufcg.psoft.commerce.model.estabelecimento.Estabelecimento;
import com.ufcg.psoft.commerce.model.pedido.Pedido;
import com.ufcg.psoft.commerce.model.entregador.Entregador;
import com.ufcg.psoft.commerce.repository.PedidoRepository;
import com.ufcg.psoft.commerce.service.associacao.IAssociacaoService;
import com.ufcg.psoft.commerce.service.cliente.IClienteService;
import com.ufcg.psoft.commerce.service.entregador.IEntregadorDisponibilidade;
import com.ufcg.psoft.commerce.service.entregador.IEntregadorService;
import com.ufcg.psoft.commerce.service.estabelecimento.IEstabelecimentoService;
import com.ufcg.psoft.commerce.service.notificacao.IPedidoNotificacaoService;
import com.ufcg.psoft.commerce.service.validacao.IValidaPedido;
import lombok.SneakyThrows;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PedidoStatusService implements IPedidoStatusService{

    @Autowired
    private IPedidoNotificacaoService pedidoNotificacaoService;
    @Autowired
    private IPedidoService pedidoService;
    @Autowired
    private IEstabelecimentoService estabelecimentoService;
    @Autowired
    private IEntregadorService entregadorService;

    @Autowired
    private IEntregadorDisponibilidade entregadorDisponibilidadeService;
    @Autowired
    private IClienteService clienteService;

    @Autowired
    private IPedidoNotificacaoService notificacaoService;

    @Autowired
    private IAssociacaoService associacaoService;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public PedidoResponseDTO disparaPronto(Long estabelecimentoId, String codigoAcesso, Long pedidoId) {
        Estabelecimento estabelecimento = estabelecimentoService.buscarByCodigoDeAcesso(estabelecimentoId, codigoAcesso);
        Pedido pedido = pedidoService.validaPedidoEstabelecimento(pedidoId, estabelecimento.getId());

        pedido.terminoPreparo();
        List<Entregador> entregadores = entregadorDisponibilidadeService.getEntregadoresDisponiveis(estabelecimentoId);
        System.out.println("---------------------------------------------\nEntregadores: " + entregadores);
        if (!entregadores.isEmpty()) {
            try {
                this.atribuirEntregador(estabelecimentoId, codigoAcesso, pedidoId, entregadores.get(0).getIdEntregador());
            } catch (Exception ignored) {

            }
        }
        else{
            this.pedidoNotificacaoService.notificarIndisponibilidadeNaEntrega(pedido.getCliente().getNome());
        }
        return modelMapper.map(pedido, PedidoResponseDTO.class);

    }

    @Override
    public PedidoResponseDTO clienteRecebePedido(Long clienteId, String codigoAcesso, Long pedidoId) {
        Cliente cliente = clienteService.recuperarComCodigoAcesso(clienteId, codigoAcesso);
        Pedido pedido = this.pedidoService.getPedido(pedidoId);
        if(!pedido.getCliente().equals(cliente)) throw new PedidoClienteDoesntMatchException();
        pedido.clienteConfirmaEntrega();

        notificacaoService.notificarEstabelecimentoPedidoEntregue(pedido);

        return modelMapper.map(pedido, PedidoResponseDTO.class);

    }

    @Override
    public PedidoResponseDTO atribuirEntregador(Long idEstabelecimento, String codigoAcesso, Long idPedido, Long idEntregador) throws Exception {
        Estabelecimento estabelecimento = estabelecimentoService.buscarByCodigoDeAcesso(idEstabelecimento, codigoAcesso);
        Pedido pedido = pedidoService.validaPedidoEstabelecimento(idPedido, idEstabelecimento);
        Entregador entregador = entregadorService.getByIdEntregadorEntity(idEntregador);
        associacaoService.getAssociacao(estabelecimento, entregador);

        pedido.setEntregador(entregador);
        pedido.atribuidoEntregador();
        entregadorDisponibilidadeService.setDisponivel(entregador.getCodigoAcessoEntregador(), false);
        notificacaoService.notificarCliente(pedido);

        return modelMapper.map(pedido, PedidoResponseDTO.class);

    }

    @Override
    public void cancelarPedidoCliente(Long id, Long clienteId, String codigoAcesso) {
        Cliente cliente = clienteService.recuperarComCodigoAcesso(clienteId, codigoAcesso);
        Pedido pedido = pedidoService.getPedido(id);

        if (!cliente.equals(pedido.getCliente())) throw new PedidoClienteDoesntMatchException();

        pedido.cancelaPedido();

        pedidoRepository.deleteById(id);
    }

    public void atribuiPedido(Long idEntregador) throws Exception {
        List<Pedido> pedidosProntos = this.getPedidosProntos();
        if (!pedidosProntos.isEmpty()) {
            Pedido pedido = pedidosProntos.get(0);
            atribuirEntregador(pedido.getEstabelecimento().getId(), pedido.getEstabelecimento().getCodigoAcessoEstabelecimento(), pedido.getId(), idEntregador);
        }
    }

    private List<Pedido> getPedidosProntos() {
        return this.pedidoRepository.findAll().stream().filter(pedido -> pedido.getStatus().getOrderNumber().equals(2)).toList();
    }
}
