package PITSa.src.main.java.com.ufcg.psoft.commerce.service.entregador;


import com.ufcg.psoft.commerce.dto.entregador.EntregadorPostRequestDTO;
import com.ufcg.psoft.commerce.dto.entregador.EntregadorPutDTO;
import com.ufcg.psoft.commerce.dto.entregador.EntregadorResponseDTO;
import com.ufcg.psoft.commerce.exception.entregador.EntregadorNotFoundException;
import com.ufcg.psoft.commerce.exception.gerais.CodigoDeAcessoInvalidoException;
import com.ufcg.psoft.commerce.model.entregador.Entregador;
import com.ufcg.psoft.commerce.repository.EntregadorRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EntregadorServiceImpl implements IEntregadorService{

    @Autowired
    private EntregadorRepository entregadorRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public EntregadorResponseDTO cadastrarEntregador
            (EntregadorPostRequestDTO entregadorPostRequestDTO){
        if (this.entregadorRepository.existsByCodigoAcessoEntregador(
                entregadorPostRequestDTO.getCodigoAcessoEntregador())){
            throw new CodigoDeAcessoInvalidoException();
        }

        Entregador entregador = this.modelMapper
                .map(entregadorPostRequestDTO, Entregador.class);
        this.entregadorRepository.save(entregador);
        return modelMapper.map(entregador, EntregadorResponseDTO.class);
    }

    @Override
    public EntregadorResponseDTO getEntregadorById(Long id) {
        Entregador entregador = entregadorRepository.findById(id)
                .orElseThrow(EntregadorNotFoundException::new);
        return new EntregadorResponseDTO(entregador);
    }

    public Entregador getByIdEntregadorEntity(Long id){
        return this.entregadorRepository.findById(id)
                .orElseThrow(EntregadorNotFoundException::new);
    }

    @Override
    public List<EntregadorResponseDTO> listaEntregadores() {
        return entregadorRepository.findAll().stream()
                .map(EntregadorResponseDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public EntregadorResponseDTO atualizaEntregador
            (EntregadorPutDTO entregadorPutDTO) {
        Entregador entregador =
                this.entregadorRepository
                        .findById(entregadorPutDTO.getIdEntregador())
                        .orElseThrow(EntregadorNotFoundException::new);
        if (!entregador.getCodigoAcessoEntregador()
                .equals(entregadorPutDTO.getAtualCodigoEntregador())) {
            throw new CodigoDeAcessoInvalidoException();
        }
        entregador.setCodigoAcessoEntregador
                (entregadorPutDTO.getNovoCodigoEntregador());
        Entregador entregadorAtualizado = entregadorRepository.save(entregador);
        return new EntregadorResponseDTO(entregadorAtualizado);
    }

    @Override
    @Transactional
    public void removeEntregador(Long id, String codigoAcessoEntregador) {
        if (!this.entregadorRepository
                .existsByCodigoAcessoEntregador(codigoAcessoEntregador)){
            throw new CodigoDeAcessoInvalidoException();
        }
        Entregador entregador = this.entregadorRepository.findById(id)
                .orElseThrow(EntregadorNotFoundException::new);
        this.entregadorRepository.delete(entregador);
    }

    @Override
    public Entregador verificaLogin(Long id, String codigoAcesso) {
        Entregador entregador = this.entregadorRepository.findById(id)
                .orElseThrow(EntregadorNotFoundException::new);
        if(!entregador.getCodigoAcessoEntregador().equals(codigoAcesso)){
            throw new CodigoDeAcessoInvalidoException();
        }
        return entregador;
    }
}
