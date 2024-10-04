package PITSa.src.main.java.com.ufcg.psoft.commerce.model.pedido.statePedido;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.ufcg.psoft.commerce.exception.pedido.IllegalStateChangeException;
import com.ufcg.psoft.commerce.exception.pedido.InvalidCancellationException;
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
@DiscriminatorValue("Entregue")
@JsonIdentityInfo(
        scope = PedidoEntregue.class,
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class PedidoEntregue extends StatePedido{

    @Override
    public void clienteCancelaPedido(){
        throw new InvalidCancellationException();
    }

}
