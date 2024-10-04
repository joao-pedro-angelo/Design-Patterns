package PITSa.src.main.java.com.ufcg.psoft.commerce.dto.cliente;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClienteInteresseRequestDTO {
    @NotBlank(message = "Sabor não pode ser vazio.")
    private String sabor;
    @NotNull(message = "Id de estabelecimento não pode ser vazio.")
    private Long idEstabelecimento;
    @NotNull(message = "Id de cliente não pode ser vazio.")
    private Long idCliente;
    @Pattern(regexp = "^\\d{6}$",
            message = "Código de acesso deve ter exatamente 6 digitos numéricos")
    private String codigoAcesso;
}