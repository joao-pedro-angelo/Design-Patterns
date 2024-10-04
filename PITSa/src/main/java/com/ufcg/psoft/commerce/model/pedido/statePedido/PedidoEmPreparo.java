package PITSa.src.main.java.com.ufcg.psoft.commerce.model.pedido.statePedido;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
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
@DiscriminatorValue("Preparo")
@JsonIdentityInfo(
        scope = PedidoEmPreparo.class,
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class PedidoEmPreparo extends StatePedido{

    public PedidoEmPreparo() {
        super();
        this.setOrderNumber(2);
    }
    @Override
    public void terminoPreparo(){
        Pedido pedido = super.getPedido();
        StatePedido pedidoPronto = PedidoPronto.builder().build();
        pedidoPronto.setOrderNumber(3);
        pedidoPronto.setPedido(pedido);
        pedido.setStatus(pedidoPronto);
    }
}