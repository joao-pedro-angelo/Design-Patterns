package PITSa.src.main.java.com.ufcg.psoft.commerce.repository;

import com.ufcg.psoft.commerce.model.cliente.Cliente;
import com.ufcg.psoft.commerce.model.estabelecimento.Estabelecimento;
import com.ufcg.psoft.commerce.model.pedido.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    Pedido findPedidoById(Long id);
    List<Pedido> findPedidosByEstabelecimento(Estabelecimento estabelecimento);
    List<Pedido> findPedidosByClienteOrderByTimestampDesc(Cliente cliente);
}
