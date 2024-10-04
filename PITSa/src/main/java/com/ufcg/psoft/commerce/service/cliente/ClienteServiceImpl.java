package PITSa.src.main.java.com.ufcg.psoft.commerce.service.cliente;

import com.ufcg.psoft.commerce.exception.cliente.ClienteNaoExisteException;
import com.ufcg.psoft.commerce.exception.gerais.CodigoDeAcessoInvalidoException;
import com.ufcg.psoft.commerce.repository.ClienteRepository;
import com.ufcg.psoft.commerce.dto.cliente.ClientePostPutRequestDTO;
import com.ufcg.psoft.commerce.dto.cliente.ClienteResponseDTO;
import com.ufcg.psoft.commerce.model.cliente.Cliente;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClienteServiceImpl implements IClienteService {

    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    @Transactional
    public ClienteResponseDTO alterar
            (Long id,
             String codigoAcesso,
             ClientePostPutRequestDTO clientePostPutRequestDTO) {
        Cliente cliente = this.clienteRepository
                .findById(id)
                .orElseThrow(ClienteNaoExisteException::new);
        if (!cliente.getCodigoAcesso().equals(codigoAcesso)) {
            throw new CodigoDeAcessoInvalidoException();
        }
        this.modelMapper.map(clientePostPutRequestDTO, cliente);
        this.clienteRepository.save(cliente);
        return this.modelMapper.map(cliente, ClienteResponseDTO.class);
    }

    @Override
    @Transactional
    public ClienteResponseDTO criar(ClientePostPutRequestDTO clientePostPutRequestDTO) {
        if (this.clienteRepository.existsByCodigoAcesso(
                clientePostPutRequestDTO.getCodigoAcesso())){
            throw new CodigoDeAcessoInvalidoException();
        }
        Cliente cliente = this.modelMapper
                .map(clientePostPutRequestDTO, Cliente.class);
        this.clienteRepository.save(cliente);
        return modelMapper.map(cliente, ClienteResponseDTO.class);
    }

    @Override
    @Transactional
    public void remover(Long id, String codigoAcesso) {
        Cliente cliente = this.clienteRepository
                .findById(id)
                .orElseThrow(ClienteNaoExisteException::new);
        if (!cliente.getCodigoAcesso().equals(codigoAcesso)) {
            throw new CodigoDeAcessoInvalidoException();
        }
        this.clienteRepository.delete(cliente);
    }

    @Override
    public List<ClienteResponseDTO> listar() {
        List<Cliente> clientes = this.clienteRepository.findAll();
        return clientes.stream()
                .map(ClienteResponseDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public ClienteResponseDTO recuperar(Long id) {
        Cliente cliente = this.clienteRepository
                .findById(id)
                .orElseThrow(ClienteNaoExisteException::new);
        return new ClienteResponseDTO(cliente);
    }

    @Override
    public Cliente recuperarComCodigoAcesso(Long id, String codigoAcesso) {
        Cliente cliente = this.clienteRepository
                .findById(id)
                .orElseThrow(ClienteNaoExisteException::new);
        if (!cliente.getCodigoAcesso().equals(codigoAcesso)) {
            throw new ClienteNaoExisteException();
        }
        return cliente;
    }
}
