package PITSa.src.main.java.com.ufcg.psoft.commerce.dto.cliente;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ufcg.psoft.commerce.model.cliente.Cliente;
import com.ufcg.psoft.commerce.model.cliente.Endereco;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClienteResponseDTO {
    @JsonProperty("id")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @JsonProperty("nome")
    @NotBlank(message = "Nome obrigatorio")
    private String nome;

    @JsonProperty("endereco")
    @NotBlank(message = "Endereco obrigatorio")
    private Endereco endereco;

    public ClienteResponseDTO(Cliente cliente) {
        this.id = cliente.getId()   ;
        this.nome = cliente.getNome();
        this.endereco = Endereco.builder()
                .cidade(cliente.getEndereco().getCidade())
                .bairro(cliente.getEndereco().getBairro())
                .numero(cliente.getEndereco().getNumero())
                .estado(cliente.getEndereco().getEstado())
                .logradouro(cliente.getEndereco().getLogradouro())
                .build();
    }
}
