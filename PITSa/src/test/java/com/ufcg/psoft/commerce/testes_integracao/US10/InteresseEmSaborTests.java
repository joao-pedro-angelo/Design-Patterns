package PITSa.src.test.java.com.ufcg.psoft.commerce.testes_integracao.US10;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufcg.psoft.commerce.dto.cliente.ClienteInteresseRequestDTO;
import com.ufcg.psoft.commerce.dto.cliente.ClienteInteresseResponseDTO;
import com.ufcg.psoft.commerce.model.pizza.TipoSabor;
import com.ufcg.psoft.commerce.model.cliente.Cliente;
import com.ufcg.psoft.commerce.model.cliente.Endereco;
import com.ufcg.psoft.commerce.model.estabelecimento.Estabelecimento;
import com.ufcg.psoft.commerce.model.pizza.Sabor;
import com.ufcg.psoft.commerce.repository.ClienteRepository;
import com.ufcg.psoft.commerce.repository.EstabelecimentoRepository;
import com.ufcg.psoft.commerce.repository.SaborRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
@DisplayName("Testes do controlador de Clientes")
public class InteresseEmSaborTests {

    //--------------------------------------------------------------
    // Configurações básicas de testes
    final String URI_CLIENTES = "/cliente/interesse";

    @Autowired
    MockMvc driver;
    ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    ClienteRepository clienteRepository;
    @Autowired
    EstabelecimentoRepository estabelecimentoRepository;
    @Autowired
    SaborRepository saborRepository;
    Sabor sabor;
    Cliente cliente;
    Estabelecimento estabelecimento;
    JacksonTester<ClienteInteresseResponseDTO> dtoResponse;

    @BeforeEach
    void setUp() {
        Endereco newEndereco = Endereco.builder()
                .numero(55)
                .cidade("Campina Grande")
                .bairro("Universitario")
                .estado("Paraiba")
                .logradouro("Rua Central")
                .build();

        this.cliente = Cliente.builder()
                .nome("Nome do Cliente")
                .codigoAcesso("123456")
                .endereco(newEndereco)
                .build();
        this.clienteRepository.save(this.cliente);

        this.sabor = Sabor.builder()
                .nome("Nome do Sabor")
                .tipo(TipoSabor.SALGADO)
                .valorMedia(35.90)
                .valorGrande(45.90)
                .disponivel(false)
                .build();
        this.saborRepository.save(this.sabor);

        this.estabelecimento = Estabelecimento.builder()
                .codigoAcessoEstabelecimento("098765")
                .build();
        this.estabelecimentoRepository.save(this.estabelecimento);
    }

    @AfterEach
    void tearDown() {
        this.saborRepository.deleteAll();
        this.clienteRepository.deleteAll();
        this.estabelecimentoRepository.deleteAll();
    }

    @Test
    @DisplayName("Demonstrar interesse em sabor - teste deve passar - OK")
    void testeComTudoCerto() throws Exception{
        // Arrange
        ClienteInteresseRequestDTO clienteInteresseRequestDTO = ClienteInteresseRequestDTO.builder()
                .idCliente(this.cliente.getId())
                .codigoAcesso(this.cliente.getCodigoAcesso())
                .idEstabelecimento(this.estabelecimento.getId())
                .sabor(this.sabor.getNome())
                .build();

        // Act
        String responseJsonString = driver.perform(patch(URI_CLIENTES)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clienteInteresseRequestDTO)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
    }

    @Test
    @DisplayName("Demonstrar interesse em sabor com código inválido")
    void ClienteCodigoInvalido() throws Exception{
        // Arrange
        ClienteInteresseRequestDTO clienteInteresseRequestDTO = ClienteInteresseRequestDTO.builder()
                .idCliente(this.cliente.getId())
                .codigoAcesso("invalido")
                .idEstabelecimento(this.estabelecimento.getId())
                .sabor(this.sabor.getNome())
                .build();

        // Act
        String responseJsonString = driver.perform(patch(URI_CLIENTES)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(clienteInteresseRequestDTO)))
                .andExpect(status().isBadRequest()) // Codigo 400
                .andReturn().getResponse().getContentAsString();
    }

    @Test
    @DisplayName("Demonstrar interesse em sabor com nome inválido")
    void SaborComNomeInexistente() throws Exception{
        // Arrange
        ClienteInteresseRequestDTO clienteInteresseRequestDTO = ClienteInteresseRequestDTO.builder()
                .idCliente(this.cliente.getId())
                .codigoAcesso(this.cliente.getCodigoAcesso())
                .idEstabelecimento(this.estabelecimento.getId())
                .sabor("Não existo")
                .build();

        // Act
        String responseJsonString = driver.perform(patch(URI_CLIENTES)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clienteInteresseRequestDTO)))
                .andExpect(status().isNotFound()) // Codigo 404
                .andReturn().getResponse().getContentAsString();
    }

    @Test
    @DisplayName("Demonstrar interesse, mas cliente não existe")
    void ClienteInexistente() throws Exception{
        // Arrange
        long idCliente = 0L;
        ClienteInteresseRequestDTO clienteInteresseRequestDTO = ClienteInteresseRequestDTO.builder()
                .idCliente(idCliente)
                .codigoAcesso(this.cliente.getCodigoAcesso())
                .idEstabelecimento(this.estabelecimento.getId())
                .sabor(this.sabor.getNome())
                .build();

        // Act
        String responseJsonString = driver.perform(patch(URI_CLIENTES)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clienteInteresseRequestDTO)))
                .andExpect(status().isNotFound()) // Codigo 404
                .andReturn().getResponse().getContentAsString();
    }

    @Test
    @DisplayName("Demonstrar interesse em sabor, mas estabelecimento tá inválido")
    void SaborComEstabelecimentoInvalido() throws Exception{
        // Arrange
        ClienteInteresseRequestDTO clienteInteresseRequestDTO = ClienteInteresseRequestDTO.builder()
                .idCliente(this.cliente.getId())
                .codigoAcesso(this.cliente.getCodigoAcesso())
                .idEstabelecimento(0L)
                .sabor(this.sabor.getNome())
                .build();

        // Act
        String responseJsonString = driver.perform(patch(URI_CLIENTES)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clienteInteresseRequestDTO)))
                .andExpect(status().isNotFound()) // Codigo 404
                .andReturn().getResponse().getContentAsString();
    }

    @Test
    @DisplayName("Demonstrar interesse em sabor DISPONÍVEL")
    void SaborDisponivel() throws Exception{
        this.sabor.setDisponivel(true);
        this.saborRepository.save(this.sabor);
        // Arrange
        ClienteInteresseRequestDTO clienteInteresseRequestDTO = ClienteInteresseRequestDTO.builder()
                .idCliente(this.cliente.getId())
                .codigoAcesso(this.cliente.getCodigoAcesso())
                .idEstabelecimento(this.estabelecimento.getId())
                .sabor(this.sabor.getNome())
                .build();

        // Act
        String responseJsonString = driver.perform(patch(URI_CLIENTES)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clienteInteresseRequestDTO)))
                .andExpect(status().isBadRequest()) // Codigo 400
                .andReturn().getResponse().getContentAsString();
    }

}
