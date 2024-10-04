package PITSa.src.main.java.com.ufcg.psoft.commerce.service.sabor;

import com.ufcg.psoft.commerce.dto.sabor.SaborPatchStatusRequestDTO;
import com.ufcg.psoft.commerce.dto.sabor.SaborPostPutRequestDTO;
import com.ufcg.psoft.commerce.dto.sabor.SaborResponseDTO;
import com.ufcg.psoft.commerce.model.estabelecimento.Estabelecimento;
import com.ufcg.psoft.commerce.model.pizza.Pizza;
import com.ufcg.psoft.commerce.model.pizza.Sabor;

import java.util.List;

public interface ISaborService {
    SaborResponseDTO cadastrarSabor(Long estabelecimentoId, String codigoAcesso, SaborPostPutRequestDTO saborPostPutRequestDTO);
    List<SaborResponseDTO> buscarSabores(Long estabelecimentoId, String tipoSabor);
    SaborResponseDTO buscarSabor(Long saborId, Long estabelecimentoId, String codigoAcesso);
    SaborResponseDTO editarSabor(Long saborId, Long estabelecimentoId, String codigoAcesso, SaborPostPutRequestDTO payload);
    void removerSabor(Long saborId, Long estabelecimentoId ,String codigoAcesso);
    SaborResponseDTO editarStatus(SaborPatchStatusRequestDTO payload);
    Sabor buscarSaborPeloNome(String nome);
    void verificaSabores(Pizza pizza, Estabelecimento estabelecimento);
}
