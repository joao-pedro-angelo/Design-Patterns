package PITSa.src.main.java.com.ufcg.psoft.commerce.model.pizza;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import jakarta.persistence.*;
import lombok.Data;
import java.util.Collection;

@Entity
@Data
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_pizza")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @Type(value = PizzaM.class, name = "PizzaM"),
        @Type(value = PizzaG.class, name = "PizzaG"),

})
public abstract class Pizza {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    private Long id;
    public abstract Double getPreco();
    public abstract Collection<Sabor> acessaSabores();
}