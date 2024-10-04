package PITSa.src.main.java.com.ufcg.psoft.commerce.service.entregador;

import com.ufcg.psoft.commerce.dto.entregador.EntregadorPostRequestDTO;
import com.ufcg.psoft.commerce.dto.entregador.EntregadorPutDTO;
import com.ufcg.psoft.commerce.dto.entregador.EntregadorResponseDTO;
import com.ufcg.psoft.commerce.model.entregador.Entregador;

import java.util.List;


public interface IEntregadorService {
    EntregadorResponseDTO cadastrarEntregador(EntregadorPostRequestDTO entregadorPostRequestDTO) throws Exception;
    EntregadorResponseDTO getEntregadorById(Long id) throws  Exception;
    List<EntregadorResponseDTO> listaEntregadores() throws Exception;
    EntregadorResponseDTO atualizaEntregador(EntregadorPutDTO entregadorPutDTO) throws Exception;
    void removeEntregador(Long id, String codigoAcessoEntregador) throws Exception;
    Entregador verificaLogin(Long id, String codigoAcesso);
    Entregador getByIdEntregadorEntity(Long id);
}
