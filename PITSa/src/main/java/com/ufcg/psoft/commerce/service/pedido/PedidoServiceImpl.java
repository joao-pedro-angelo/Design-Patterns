package PITSa.src.main.java.com.ufcg.psoft.commerce.service.pedido;

import com.ufcg.psoft.commerce.dto.estabelecimento.EstabelecimentoResponseDTO;
import com.ufcg.psoft.commerce.dto.pedido.PedidoPostPutRequestDTO;
import com.ufcg.psoft.commerce.dto.pedido.PedidoResponseDTO;
import com.ufcg.psoft.commerce.exception.estabelecimento.EstabelecimentoNotFoundException;
import com.ufcg.psoft.commerce.exception.pedido.PedidoEstabelecimentoDoesntMatchException;
import com.ufcg.psoft.commerce.exception.pedido.PedidoNotFoundException;
import com.ufcg.psoft.commerce.model.cliente.Cliente;
import com.ufcg.psoft.commerce.model.cliente.Endereco;
import com.ufcg.psoft.commerce.model.estabelecimento.Estabelecimento;
import com.ufcg.psoft.commerce.model.pedido.ItemVenda;
import com.ufcg.psoft.commerce.model.pedido.Pedido;
import com.ufcg.psoft.commerce.repository.PedidoRepository;
import com.ufcg.psoft.commerce.service.associacao.IAssociacaoService;
import com.ufcg.psoft.commerce.service.cliente.IClienteService;
import com.ufcg.psoft.commerce.service.entregador.IEntregadorService;
import com.ufcg.psoft.commerce.service.estabelecimento.IEstabelecimentoService;
import com.ufcg.psoft.commerce.service.sabor.ISaborService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ufcg.psoft.commerce.model.pedido.statePedido.StatePedido;
import com.ufcg.psoft.commerce.model.pedido.statePedido.PedidoRecebido;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class PedidoServiceImpl implements  IPedidoService{

    @Autowired
    IEntregadorService entregadorService;

    @Autowired
    IEstabelecimentoService estabelecimentoService;

    @Autowired
    PedidoRepository pedidoRepository;

    @Autowired
    IClienteService clienteService;
    @Autowired
    IAssociacaoService associacaoService;
    @Autowired
    ISaborService saborService;
    @Autowired
    ModelMapper modelMapper;


    @Override
    public void apagarPedidoEstabelecimento
            (Long id, Long estabelecimentoId, String codigoAcesso) {
        Pedido pedido = getPedido(id);
        Estabelecimento estabelecimento =
                this.estabelecimentoService
                        .buscarByCodigoDeAcesso
                                (estabelecimentoId, codigoAcesso);

        if (!estabelecimento.equals(pedido.getEstabelecimento()))
            throw new PedidoEstabelecimentoDoesntMatchException();

        this.pedidoRepository.deleteById(id);
    }

    @Override
    public Pedido getPedido(Long id) {
        Pedido pedido = pedidoRepository.findPedidoById(id);

        if (pedido == null) {
            throw new PedidoNotFoundException();
        }

        return pedido;
    }

    @Override
    public Pedido validaPedidoEstabelecimento(Long pedidoId, Long estabelecimentoId) {
        Pedido pedido = this.pedidoRepository
                .findPedidoById(pedidoId);
        EstabelecimentoResponseDTO estabelecimento = this.estabelecimentoService
                .buscar(estabelecimentoId);

        if (pedido == null) {
            throw new PedidoNotFoundException();
        }

        if (estabelecimento == null) {
            throw new EstabelecimentoNotFoundException();
        }

        if (!pedido.getEstabelecimento().getId().equals(estabelecimento.getId())) {
            throw new PedidoEstabelecimentoDoesntMatchException();
        }

        return pedido;
    }

    @Override
    public List<PedidoResponseDTO> buscarPedidosEstabelecimento
            (Long estabelecimentoId, String codigoAcesso) {
        Estabelecimento estabelecimento =
                this.estabelecimentoService
                        .buscarByCodigoDeAcesso(estabelecimentoId, codigoAcesso);

        List<Pedido> pedidos = this.pedidoRepository
                .findPedidosByEstabelecimento(estabelecimento);

        List<PedidoResponseDTO> pedidosResponseDTOs = new ArrayList<>();
        for (Pedido pedido : pedidos)
            pedidosResponseDTOs.add(this.modelMapper
                    .map(pedido, PedidoResponseDTO.class));

        return pedidosResponseDTOs;
    }

    @Override
    public PedidoResponseDTO buscarPedidoEstabelecimento
            (Long estabelecimentoId, String codigoAcesso, Long pedidoId) {
        Estabelecimento estabelecimento = this.estabelecimentoService
                .buscarByCodigoDeAcesso(estabelecimentoId, codigoAcesso);
        Pedido pedido = this
                .validaPedidoEstabelecimento(pedidoId, estabelecimento.getId());

        return this.modelMapper.map(pedido, PedidoResponseDTO.class);
    }

    @Override
    public PedidoResponseDTO cadastraPedido
            (Long clienteId, String codigoAcesso,
             Long estabelecimentoId, PedidoPostPutRequestDTO pedidoPostPutRequestDTO) {
        Cliente cliente = this.clienteService
                .recuperarComCodigoAcesso(clienteId,codigoAcesso);
        Estabelecimento estabelecimento = this.estabelecimentoService
                .findById(estabelecimentoId);
        Endereco endereco = pedidoPostPutRequestDTO.getEndereco();

        if (endereco == null) pedidoPostPutRequestDTO.setEndereco(cliente.getEndereco());

        Collection<ItemVenda> itens = pedidoPostPutRequestDTO.getItens();
        for (ItemVenda item : itens) this.saborService.verificaSabores(item.getPizza(), estabelecimento);

        Pedido pedido = this.pedidoRepository.save(Pedido.builder()
                .itens(pedidoPostPutRequestDTO.getItens())
                .estabelecimento(estabelecimento)
                .cliente(cliente)
                .build());

        pedido.setEndereco(pedidoPostPutRequestDTO.getEndereco());
        pedido.getEstabelecimento().getPedidos().add(pedido);

        StatePedido pedidoRecebido = PedidoRecebido.builder().build();
        pedidoRecebido.setPedido(pedido);
        pedido.setStatus(pedidoRecebido);

        pedidoRecebido.setOrderNumber(1);

        return modelMapper.map(pedido, PedidoResponseDTO.class);
    }

    @Override
    public PedidoResponseDTO atualizaPedido
            (Long pedidoId, Long clienteId, String codigoAcesso,
             PedidoPostPutRequestDTO pedidoPostPutRequestDTO) {
        this.clienteService.recuperarComCodigoAcesso(clienteId,codigoAcesso);
        Pedido pedido = getPedido(pedidoId);

        Endereco endereco = pedidoPostPutRequestDTO.getEndereco();

        this.pedidoRepository.flush();

        if (endereco != null) {
            pedido.setEndereco(endereco);
        }

        if (pedidoPostPutRequestDTO.getItens() != null
                && !pedidoPostPutRequestDTO.getItens().isEmpty())
            pedido.setItens(pedidoPostPutRequestDTO.getItens());

        return this.modelMapper.map(pedido, PedidoResponseDTO.class);
    }
}
