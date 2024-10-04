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
@DiscriminatorValue("Recebido")
@JsonIdentityInfo(
        scope = PedidoRecebido.class,
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class PedidoRecebido extends StatePedido{

    public PedidoRecebido() {
        super();
        this.setOrderNumber(1);
    }
    @Override
    public void confirmaPagamento(){
        Pedido pedido = super.getPedido();
        StatePedido pedidoEmPreparo = PedidoEmPreparo.builder().build();
        pedidoEmPreparo.setOrderNumber(2);
        pedidoEmPreparo.setPedido(pedido);
        pedido.setStatus(pedidoEmPreparo);
    }
}
