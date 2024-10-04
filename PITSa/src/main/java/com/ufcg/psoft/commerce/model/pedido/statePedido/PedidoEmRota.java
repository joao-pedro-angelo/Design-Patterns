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
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@DiscriminatorValue("Rota")
@JsonIdentityInfo(
        scope = PedidoEmRota.class,
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class PedidoEmRota extends StatePedido{


    @Override
    public void clienteCancelaPedido(){
        throw new InvalidCancellationException();
    }

    @Override
    public void clienteConfirmaEntrega(){
        Pedido pedido = super.getPedido();
        StatePedido entrege = PedidoEntregue.builder().build();
        entrege.setOrderNumber(5);
        entrege.setPedido(pedido);
        pedido.setStatus(entrege);
    }
}
