package PITSa.src.main.java.com.ufcg.psoft.commerce.service.entregador;

import com.ufcg.psoft.commerce.dto.entregador.EntregadorResponseDTO;
import com.ufcg.psoft.commerce.model.entregador.Entregador;

import java.util.List;

public interface IEntregadorDisponibilidade {
    Boolean isDisponivel(String codigoAcesso);
    EntregadorResponseDTO setDisponivel(String codigoAcesso, Boolean disponivel);

    List<Entregador> getEntregadoresDisponiveis(Long estabelecimentoId);
}
