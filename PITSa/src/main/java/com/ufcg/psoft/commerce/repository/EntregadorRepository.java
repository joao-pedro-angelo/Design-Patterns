package PITSa.src.main.java.com.ufcg.psoft.commerce.repository;

import com.ufcg.psoft.commerce.model.entregador.Entregador;
import com.ufcg.psoft.commerce.model.pizza.Sabor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EntregadorRepository extends JpaRepository<Entregador, Long> {
    Boolean existsByCodigoAcessoEntregador(String codigoAcessoEntregador);
    Entregador findEntregadorByCodigoAcessoEntregador(String codigoAcessoEntregador);
}
