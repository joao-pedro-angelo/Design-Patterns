package PITSa.src.main.java.com.ufcg.psoft.commerce.service.associacao;

import com.ufcg.psoft.commerce.dto.associacao.AssociacaoPostDTO;
import com.ufcg.psoft.commerce.dto.associacao.AssociacaoPutDTO;
import com.ufcg.psoft.commerce.model.associacao.Associacao;
import com.ufcg.psoft.commerce.model.entregador.Entregador;
import com.ufcg.psoft.commerce.model.estabelecimento.Estabelecimento;

public interface IAssociacaoService {
    Associacao getAssociacao(Estabelecimento estabelecimento, Entregador entregador);
    Associacao atualizarStatus(AssociacaoPutDTO dto);
    Associacao cadastrar(AssociacaoPostDTO dto);
}
