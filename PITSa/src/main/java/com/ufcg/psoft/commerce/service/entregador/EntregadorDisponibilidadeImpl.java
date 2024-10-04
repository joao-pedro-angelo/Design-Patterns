package PITSa.src.main.java.com.ufcg.psoft.commerce.service.entregador;

import com.ufcg.psoft.commerce.dto.entregador.EntregadorResponseDTO;
import com.ufcg.psoft.commerce.model.entregador.Entregador;
import com.ufcg.psoft.commerce.model.associacao.Associacao;
import com.ufcg.psoft.commerce.repository.AssociacaoRepository;
import com.ufcg.psoft.commerce.repository.EntregadorRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class EntregadorDisponibilidadeImpl implements IEntregadorDisponibilidade{

    @Autowired
    private EntregadorRepository entregadorRepository;

    @Autowired
    private AssociacaoRepository associacaoRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Boolean isDisponivel(String codigoAcesso) {
        return this.entregadorRepository
                .findEntregadorByCodigoAcessoEntregador(codigoAcesso)
                .getDisponibilidade();
    }

    @Override
    public EntregadorResponseDTO setDisponivel(String codigoAcesso, Boolean disponivel) {
        Entregador entregador = this.entregadorRepository
                .findEntregadorByCodigoAcessoEntregador(codigoAcesso);
        entregador.setDisponibilidade(disponivel);
        this.entregadorRepository.save(entregador);

        return this.modelMapper
                .map(entregador, EntregadorResponseDTO.class);
    }

    @Override
    public List<Entregador> getEntregadoresDisponiveis(Long estabelecimentoId) {
        List<Associacao> associacoes = associacaoRepository.findAll().stream().filter(associacao -> associacao.getEstabelecimento().getId().equals(estabelecimentoId)).toList();
        return associacoes.stream().map(Associacao::getEntregador).filter(Entregador::getDisponibilidade).sorted(Comparator.comparing(Entregador::getUpdatedAt)).toList();
    }
}
