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
@DiscriminatorValue("M")
public class PizzaM extends Pizza {

    @NotNull(message = "É necessário um sabor para a pizza")
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonProperty("sabor1")
    private Sabor sabor1;

    @Override
    public Double getPreco() {
        return sabor1.getValorMedia();
    }

    @Override
    public Collection<Sabor> acessaSabores() {
        List<Sabor> sabores = new ArrayList<>();
        sabores.add(sabor1);
        return sabores;
    }
}