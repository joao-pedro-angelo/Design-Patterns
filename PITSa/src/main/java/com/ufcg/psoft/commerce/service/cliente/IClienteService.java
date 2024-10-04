package PITSa.src.main.java.com.ufcg.psoft.commerce.service.cliente;

import com.ufcg.psoft.commerce.dto.cliente.ClientePostPutRequestDTO;
import com.ufcg.psoft.commerce.dto.cliente.ClienteResponseDTO;
import com.ufcg.psoft.commerce.model.cliente.Cliente;

import java.util.List;

public interface IClienteService {
    ClienteResponseDTO alterar(Long id, String codigoAcesso, ClientePostPutRequestDTO clientePostPutRequestDTO);
    List<ClienteResponseDTO> listar();
    ClienteResponseDTO recuperar(Long id);
    ClienteResponseDTO criar(ClientePostPutRequestDTO clientePostPutRequestDTO);
    void remover(Long id, String codigoAcesso);
    Cliente recuperarComCodigoAcesso(Long id, String codigoAcesso);
}
