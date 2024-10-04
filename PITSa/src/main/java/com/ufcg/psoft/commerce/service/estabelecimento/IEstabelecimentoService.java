package PITSa.src.main.java.com.ufcg.psoft.commerce.service.estabelecimento;

import com.ufcg.psoft.commerce.dto.estabelecimento.EstabelecimentoPostRequestDTO;
import com.ufcg.psoft.commerce.dto.estabelecimento.EstabelecimentoPutDTO;
import com.ufcg.psoft.commerce.dto.estabelecimento.EstabelecimentoResponseDTO;
import com.ufcg.psoft.commerce.model.estabelecimento.Estabelecimento;

import java.util.List;

public interface IEstabelecimentoService {
    EstabelecimentoResponseDTO alterar(EstabelecimentoPutDTO dto);
    List<EstabelecimentoResponseDTO> listar();
    EstabelecimentoResponseDTO buscar(Long id);
    EstabelecimentoResponseDTO criar(EstabelecimentoPostRequestDTO dto);
    void remover(Long id, String codigoAcesso);
    Estabelecimento buscarByCodigoDeAcesso(Long id, String codigoAcesso);
    Estabelecimento findById(Long id);
}
