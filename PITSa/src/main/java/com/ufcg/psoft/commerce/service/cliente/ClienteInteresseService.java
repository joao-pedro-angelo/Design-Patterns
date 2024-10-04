package PITSa.src.main.java.com.ufcg.psoft.commerce.service.cliente;

import com.ufcg.psoft.commerce.dto.cliente.ClienteInteresseRequestDTO;
import com.ufcg.psoft.commerce.dto.cliente.ClienteInteresseResponseDTO;
import com.ufcg.psoft.commerce.exception.sabor.InvalidInteresseException;
import com.ufcg.psoft.commerce.model.cliente.Cliente;
import com.ufcg.psoft.commerce.model.estabelecimento.Estabelecimento;
import com.ufcg.psoft.commerce.model.pizza.Sabor;
import com.ufcg.psoft.commerce.service.estabelecimento.IEstabelecimentoService;
import com.ufcg.psoft.commerce.service.sabor.ISaborService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteInteresseService implements IClienteInteresseService {
    @Autowired
    private ISaborService saborService;
    @Autowired
    private IEstabelecimentoService estabelecimentoService;
    @Autowired
    private IClienteService clienteService;


    @Override
    public ClienteInteresseResponseDTO addInteresse
            (ClienteInteresseRequestDTO clienteInteresseRequestDTO) {
        Cliente cliente = this.clienteService
                .recuperarComCodigoAcesso(
                        clienteInteresseRequestDTO.getIdCliente(),
                        clienteInteresseRequestDTO.getCodigoAcesso()
                );

        Estabelecimento estabelecimento = estabelecimentoService
                .findById(clienteInteresseRequestDTO.getIdEstabelecimento());

        Sabor sabor = this.saborService
                .buscarSaborPeloNome(clienteInteresseRequestDTO
                        .getSabor());
        if (sabor.getDisponivel()) throw new InvalidInteresseException();

        cliente.getSaboresDeInteresse().add(sabor);
        sabor.getClientesInteressados().add(cliente);

        ClienteInteresseResponseDTO dtoResponse = new ClienteInteresseResponseDTO();
        dtoResponse.setNome(cliente.getNome());
        dtoResponse.setInteresses(cliente.getSaboresDeInteresse());

        return dtoResponse;
    }
}
