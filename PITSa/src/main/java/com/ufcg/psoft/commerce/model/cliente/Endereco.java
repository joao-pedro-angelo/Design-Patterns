package PITSa.src.main.java.com.ufcg.psoft.commerce.model.cliente;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Endereco {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    private Long id;


    @Column
    @NotNull(message = "Número não pode ser vazio.")
    @JsonProperty("numeroEndereco")
    private Integer numero;

    @Column
    @NotBlank(message = "Cep não pode ser vazio.")
    @JsonProperty("cidade")
    private String cidade;

    @Column
    @NotBlank(message = "Cep não pode ser vazio.")
    @JsonProperty("bairro")
    private String bairro;

    @Column
    @NotBlank(message = "Cep não pode ser vazio.")
    @JsonProperty("estado")
    private String estado;

    @Column
    @NotBlank(message = "Complemento não pode ser vazio.")
    @JsonProperty("logradouro")
    private String logradouro;

    @OneToOne
    @JsonProperty("cliente")
    private Cliente cliente;
}
