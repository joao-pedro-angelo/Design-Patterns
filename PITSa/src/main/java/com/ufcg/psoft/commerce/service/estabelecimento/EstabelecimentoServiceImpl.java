package PITSa.src.main.java.com.ufcg.psoft.commerce.service.estabelecimento;

import com.ufcg.psoft.commerce.dto.estabelecimento.EstabelecimentoPostRequestDTO;
import com.ufcg.psoft.commerce.dto.estabelecimento.EstabelecimentoPutDTO;
import com.ufcg.psoft.commerce.dto.estabelecimento.EstabelecimentoResponseDTO;
import com.ufcg.psoft.commerce.exception.estabelecimento.EstabelecimentoNotFoundException;
import com.ufcg.psoft.commerce.exception.gerais.CodigoDeAcessoInvalidoException;
import com.ufcg.psoft.commerce.model.estabelecimento.Estabelecimento;
import com.ufcg.psoft.commerce.repository.EstabelecimentoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EstabelecimentoServiceImpl implements IEstabelecimentoService {

    @Autowired
    private EstabelecimentoRepository estabelecimentoRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    @Transactional
    public EstabelecimentoResponseDTO alterar(EstabelecimentoPutDTO dto) {
        Estabelecimento estabelecimento = this.estabelecimentoRepository
                .findById(dto.getId())
                .orElseThrow(EstabelecimentoNotFoundException::new);
        if (!estabelecimento
                .getCodigoAcessoEstabelecimento()
                .equals(dto.getAtualCodigoEstabelecimento())){
            throw new CodigoDeAcessoInvalidoException();
        }
        estabelecimento
                .setCodigoAcessoEstabelecimento(dto.getNovoCodigoEstabelecimento());
        this.estabelecimentoRepository
                .save(estabelecimento);
        return this.modelMapper
                .map(estabelecimento, EstabelecimentoResponseDTO.class);
    }

    @Override
    public List<EstabelecimentoResponseDTO> listar() {
        List<Estabelecimento> estabelecimentos =
                this.estabelecimentoRepository.findAll();
        return estabelecimentos
                .stream()
                .map(EstabelecimentoResponseDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public EstabelecimentoResponseDTO buscar(Long id) {
        Estabelecimento estabelecimento = this.estabelecimentoRepository
                .findById(id)
                .orElseThrow(EstabelecimentoNotFoundException::new);
        return this.modelMapper.map(estabelecimento,
                EstabelecimentoResponseDTO.class);
    }

    @Override
    @Transactional
    public EstabelecimentoResponseDTO criar(EstabelecimentoPostRequestDTO dto) {
        if (this.estabelecimentoRepository.existsByCodigoAcessoEstabelecimento(
                dto.getCodigoAcessoEstabelecimento())){
            throw new CodigoDeAcessoInvalidoException();
        }
        Estabelecimento estabelecimento = modelMapper.map(dto, Estabelecimento.class);
        this.estabelecimentoRepository.save(estabelecimento);
        return this.modelMapper.map(estabelecimento,
                EstabelecimentoResponseDTO.class);
    }

    @Override
    @Transactional
    public void remover(Long id, String codigoAcesso) {
        Estabelecimento estabelecimento = this.estabelecimentoRepository
                .findById(id)
                .orElseThrow(EstabelecimentoNotFoundException::new);
        if (!estabelecimento.getCodigoAcessoEstabelecimento().equals(codigoAcesso)){
            throw new CodigoDeAcessoInvalidoException();
        } this.estabelecimentoRepository.delete(estabelecimento);
    }

    @Override
    public Estabelecimento buscarByCodigoDeAcesso(Long id, String codigoAcesso) {
        if (!this.estabelecimentoRepository
                .existsByCodigoAcessoEstabelecimento(codigoAcesso)){
            throw new CodigoDeAcessoInvalidoException();
        }
        return this.estabelecimentoRepository
                .findById(id)
                .orElseThrow(EstabelecimentoNotFoundException::new);
    }

    @Override
    public Estabelecimento findById(Long id){
        return this.estabelecimentoRepository
                .findById(id)
                .orElseThrow(EstabelecimentoNotFoundException::new);
    }
}
