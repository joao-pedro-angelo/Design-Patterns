package PITSa.src.main.java.com.ufcg.psoft.commerce.model.pedido.statePedido;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.ufcg.psoft.commerce.exception.pedido.InvalidCancellationException;
import com.ufcg.psoft.commerce.model.pedido.Pedido;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@EqualsAndHashCode(callSuper = true)
@DiscriminatorValue("Pronto")
@JsonIdentityInfo(
        scope = PedidoPronto.class,
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class PedidoPronto extends StatePedido{

    public PedidoPronto() {
        super();
        this.setOrderNumber(2);
    }
    @Override
    public void atribuidoEntregador(){
        Pedido pedido = super.getPedido();
        StatePedido pedidoEmRota = PedidoEmRota.builder().build();
        pedidoEmRota.setOrderNumber(4);
        pedidoEmRota.setPedido(pedido);
        pedido.setStatus(pedidoEmRota);
    }

    @Override
    public void clienteCancelaPedido(){
        throw new InvalidCancellationException();
    }
}
