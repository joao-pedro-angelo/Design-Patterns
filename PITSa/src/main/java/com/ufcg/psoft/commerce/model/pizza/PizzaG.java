package PITSa.src.main.java.com.ufcg.psoft.commerce.model.pizza;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@DiscriminatorValue("G")
public class PizzaG extends Pizza {

    @NotNull(message = "É necessário um sabor para a pizza")
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonProperty("sabor1")
    private Sabor sabor1;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonProperty("sabor2")
    private Sabor sabor2;

    @Override
    public Double getPreco() {
        Double subTotal = sabor1.getValorGrande();
        if (this.sabor2 != null) {
            subTotal += this.sabor2.getValorGrande();
            subTotal /= 2;
        }
        return subTotal;
    }

    @Override
    public Collection<Sabor> acessaSabores() {
        List<Sabor> sabores = new ArrayList<>();
        sabores.add(sabor1);
        sabores.add(sabor2);
        return sabores;
    }
}