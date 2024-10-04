package PITSa.src.main.java.com.ufcg.psoft.commerce.repository;

import com.ufcg.psoft.commerce.model.associacao.Associacao;
import com.ufcg.psoft.commerce.model.entregador.Entregador;
import com.ufcg.psoft.commerce.model.estabelecimento.Estabelecimento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssociacaoRepository extends JpaRepository<Associacao, Long> {
    Associacao findAssociacaoById(Long Id);
    Associacao findAssociacaoByEstabelecimentoAndEntregador
            (Estabelecimento estabelecimento, Entregador entregador);
}
