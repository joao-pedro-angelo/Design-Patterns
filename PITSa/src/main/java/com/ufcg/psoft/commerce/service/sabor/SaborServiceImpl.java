package PITSa.src.main.java.com.ufcg.psoft.commerce.service.sabor;

import com.ufcg.psoft.commerce.dto.sabor.SaborPatchStatusRequestDTO;
import com.ufcg.psoft.commerce.dto.sabor.SaborPostPutRequestDTO;
import com.ufcg.psoft.commerce.dto.sabor.SaborResponseDTO;
import com.ufcg.psoft.commerce.exception.sabor.SaborEstabelecimentoDoesntMatchException;
import com.ufcg.psoft.commerce.model.pizza.Pizza;
import com.ufcg.psoft.commerce.model.pizza.TipoSabor;
import com.ufcg.psoft.commerce.model.cliente.Cliente;
import com.ufcg.psoft.commerce.model.estabelecimento.Estabelecimento;
import com.ufcg.psoft.commerce.model.pizza.Sabor;
import com.ufcg.psoft.commerce.repository.SaborRepository;
import com.ufcg.psoft.commerce.service.estabelecimento.IEstabelecimentoService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SaborServiceImpl implements ISaborService {
    @Autowired
    private SaborRepository repository;

    @Autowired
    private IEstabelecimentoService estabelecimentoService;

    @Override
    public SaborResponseDTO cadastrarSabor(Long estabelecimentoId ,String codigoAcesso, SaborPostPutRequestDTO saborPostPutRequestDTO) {
        Estabelecimento estabelecimento = estabelecimentoService.buscarByCodigoDeAcesso(estabelecimentoId, codigoAcesso);
        Sabor sabor = new Sabor(saborPostPutRequestDTO);
        sabor.setEstabelecimento(estabelecimento);
        Sabor sabor_salvo = this.repository.save(sabor);
        return new SaborResponseDTO(sabor_salvo);
    }

    public List<SaborResponseDTO> buscarSabores(Long estabelecimentoId, String tipoSabor) {
        estabelecimentoService.findById(estabelecimentoId); // valida estabelecimento
        if (tipoSabor != null) {
            return repository.findByTipo(TipoSabor.valueOf(tipoSabor.toUpperCase())).stream().map(SaborResponseDTO::new).filter(sabor -> sabor.getEstabelecimentoId().equals(estabelecimentoId)).collect(Collectors.toList());
        }
        return repository.findAllByOrderByDisponivelDesc().stream().map(SaborResponseDTO::new).filter(sabor -> sabor.getEstabelecimentoId().equals(estabelecimentoId)).collect(Collectors.toList());
    }

    public SaborResponseDTO buscarSabor(Long saborId, Long estabelecimentoId, String codigoAcesso) {
        estabelecimentoService.buscarByCodigoDeAcesso(estabelecimentoId, codigoAcesso);
        Optional<Sabor> saborOptional = this.repository.findById(saborId);
        if (saborOptional.isEmpty()) {
            throw new EntityNotFoundException("Ler sabor: Sabor não encontrado com id" + saborId);
        }
        return new SaborResponseDTO(saborOptional.get());
    }

    @Override
    public SaborResponseDTO editarSabor(Long saborId, Long estabelecimentoId, String codigoAcesso, SaborPostPutRequestDTO payload) {
        Estabelecimento estabelecimento = estabelecimentoService.buscarByCodigoDeAcesso(estabelecimentoId, codigoAcesso);
        Optional<Sabor> saborOptional = repository.findById(saborId);
        if (saborOptional.isEmpty()) {
            throw new EntityNotFoundException("Editar sabor: Sabor não encontrado");
        }
        Sabor sabor = saborOptional.get();
        if (!Objects.equals(sabor.getEstabelecimento().getId(), estabelecimento.getId())) {
            throw new EntityNotFoundException("Editar sabor: Sabor não encontrado");
        }
        sabor.setNome(payload.getNome());
        sabor.setTipo(payload.getTipo());
        sabor.setValorGrande(payload.getValorGrande());
        sabor.setValorMedia(payload.getValorMedia());
        sabor.setDisponivel(payload.getDisponivel());
        Sabor saborAtualizado = repository.save(sabor);
        return new SaborResponseDTO(saborAtualizado);
    }

    @Override
    public void removerSabor(Long saborId, Long estabelecimentoId, String codigoAcesso) {
        Estabelecimento estabelecimento = estabelecimentoService.buscarByCodigoDeAcesso(estabelecimentoId, codigoAcesso);
        Optional<Sabor> saborOptional = repository.findById(saborId);

        if (saborOptional.isEmpty()) {
            throw new EntityNotFoundException("Remover sabor: Sabor não encontrado ou codigo de acesso invalido");
        }
        Sabor sabor = saborOptional.get();
        if (!Objects.equals(sabor.getEstabelecimento().getId(), estabelecimento.getId())) {
            throw new EntityNotFoundException("Editar sabor: Sabor não encontrado");
        }

        repository.delete(sabor);
    }

    @Override
    public SaborResponseDTO editarStatus(SaborPatchStatusRequestDTO payload) {
        estabelecimentoService.buscarByCodigoDeAcesso(payload.getEstabelecimentoId(), payload.getCodigoAcesso());
        Sabor sabor = verificarSabor(payload.getSaborId(), payload.getEstabelecimentoId());
        sabor.setDisponivel(payload.getDisponivel());
        if (payload.getDisponivel()) {
            notificaClientes(sabor.getClientesInteressados(), sabor);
        }
        repository.save(sabor);
        return new SaborResponseDTO(sabor);
    }

    @Override
    public Sabor buscarSaborPeloNome(String nome) {
        Sabor sabor =  this.repository
                .findSaborByNome(nome);
        if (sabor == null) throw new EntityNotFoundException();
        return sabor;
    }

    private Sabor verificarSabor(Long id, Long estabelecimentoId) {
        Optional<Sabor> saborOptional = repository.findById(id);
        if (saborOptional.isEmpty()) {
            throw new EntityNotFoundException("Sabor não encontrado ou codigo de acesso invalido");
        }
        Sabor sabor = saborOptional.get();
        if (!Objects.equals(sabor.getEstabelecimento().getId(), estabelecimentoId)) {
            throw new EntityNotFoundException("Sabor não encontrado");
        }
        return sabor;
    }

    private void notificaClientes(List<Cliente> clientes, Sabor sabor) {
        clientes.forEach(cliente -> System.out.println("O sabor " + sabor.getNome() + " está disponível para " + cliente.getNome()));
    }

    @Override
    public void verificaSabores(Pizza pizza, Estabelecimento estabelecimento) {
        Collection<Sabor> sabores = pizza.acessaSabores();
        if (sabores.stream().anyMatch(sabor ->
                !sabor.getEstabelecimento().getId().equals(estabelecimento.getId())))
            throw new SaborEstabelecimentoDoesntMatchException();
    }
}
