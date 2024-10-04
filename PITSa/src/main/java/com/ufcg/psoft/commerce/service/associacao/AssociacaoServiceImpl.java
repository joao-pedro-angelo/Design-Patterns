package PITSa.src.main.java.com.ufcg.psoft.commerce.service.associacao;

import com.ufcg.psoft.commerce.dto.associacao.AssociacaoPostDTO;
import com.ufcg.psoft.commerce.dto.associacao.AssociacaoPutDTO;
import com.ufcg.psoft.commerce.exception.associacao.AssociacaoNotFoundException;
import com.ufcg.psoft.commerce.exception.entregador.EntregadorIndisponivelException;
import com.ufcg.psoft.commerce.model.associacao.Associacao;
import com.ufcg.psoft.commerce.model.entregador.Entregador;
import com.ufcg.psoft.commerce.model.estabelecimento.Estabelecimento;
import com.ufcg.psoft.commerce.repository.AssociacaoRepository;
import com.ufcg.psoft.commerce.service.entregador.IEntregadorDisponibilidade;
import com.ufcg.psoft.commerce.service.entregador.IEntregadorService;
import com.ufcg.psoft.commerce.service.estabelecimento.IEstabelecimentoService;
import com.ufcg.psoft.commerce.service.notificacao.IPedidoNotificacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AssociacaoServiceImpl implements  IAssociacaoService{
    @Autowired
    private AssociacaoRepository associacaoRepository;
    @Autowired
    private IEstabelecimentoService estabelecimentoService;
    @Autowired
    private IEntregadorService entregadorService;
    @Autowired
    private IPedidoNotificacaoService pedidoNotificacaoService;

    @Autowired
    private IEntregadorDisponibilidade entregadorDisponibilidade;

    @Override
    public Associacao getAssociacao
            (Estabelecimento estabelecimento, Entregador entregador) {
        Associacao associacao = this.associacaoRepository
                .findAssociacaoByEstabelecimentoAndEntregador
                        (estabelecimento, entregador);
        if (associacao == null) throw new AssociacaoNotFoundException();
        return associacao;
    }

    @Override
    public Associacao atualizarStatus(AssociacaoPutDTO dto) {
        Associacao associacao = this.associacaoRepository
                .findById(dto.getIdAssociacao())
                .orElseThrow(AssociacaoNotFoundException::new);

        Entregador entregador = this.entregadorService
                .getByIdEntregadorEntity
                        (associacao.getEntregador().getIdEntregador());

        if(!associacao.getStatus()){
            associacao.setEntregador(entregador);
            entregador.setDisponibilidade(false);
        } else{
            entregador.setDisponibilidade(true);
        }

        associacao.setStatus(dto.getStatus());

        this.associacaoRepository.flush();
        return associacao;
    }

    @Override
    public Associacao cadastrar(AssociacaoPostDTO dto) {
        Entregador entregador = this.entregadorService
                .verificaLogin(dto.getIdEntregador(),
                        dto.getCodigoAcessoEntregador());

        Estabelecimento estabelecimento = this.estabelecimentoService
                .findById(dto.getEstabelecimentoId());

        entregadorDisponibilidade.setDisponivel(dto.getCodigoAcessoEntregador(), false);
        Associacao associacao = Associacao.builder()
                    .status(false)
                    .entregador(entregador)
                    .estabelecimento(estabelecimento)
                    .build();
        return this.associacaoRepository.save(associacao);
    }
}
