package PITSa.src.main.java.com.ufcg.psoft.commerce.repository;

import com.ufcg.psoft.commerce.model.pizza.TipoSabor;
import com.ufcg.psoft.commerce.model.pizza.Sabor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SaborRepository extends JpaRepository<Sabor, Long> {
    Sabor findSaborByNome(String nome);
    List<Sabor> findByTipo(TipoSabor tipo);
    List<Sabor> findAllByOrderByDisponivelDesc();
}
