package PITSa.src.test.java.com.ufcg.psoft.commerce.testes_integracao.US2;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufcg.psoft.commerce.dto.cliente.ClientePostPutRequestDTO;
import com.ufcg.psoft.commerce.model.cliente.Cliente;
import com.ufcg.psoft.commerce.model.cliente.Endereco;
import com.ufcg.psoft.commerce.repository.ClienteRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
@DisplayName("Testes do controlador de Clientes")
public class ClienteTestsCRUD {

    //--------------------------------------------------------------
    // Configurações básicas de testes
    final String URI_CLIENTES = "/cliente";
    @Autowired
    MockMvc driver;
    ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    ClienteRepository clienteRepository;
    @Autowired
    JacksonTester<ClientePostPutRequestDTO> clienteJacksonTester;
    Cliente cliente;

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
    }

    @AfterEach
    void tearDown() {
        this.clienteRepository.deleteAll();
    }

    @Test
    @DisplayName("Criar um cliente com dados válidos e verificar a criação bem-sucedida")
    void criarClienteValido() throws Exception {
        ClientePostPutRequestDTO clienteTeste = ClientePostPutRequestDTO
                .builder()
                .nome("Nome")
                .endereco(Endereco.builder()
                        .numero(55)
                        .cidade("Campina Grande")
                        .bairro("Universitario")
                        .estado("Paraiba")
                        .logradouro("Rua Central")
                        .build())
                .codigoAcesso("980765")
                .build();
        var response = this.driver
                .perform(post(this.URI_CLIENTES)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.clienteJacksonTester
                                .write(clienteTeste)
                                .getJson()))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.nome").value(clienteTeste.getNome()))
                .andReturn().getResponse().getContentAsString();

        Cliente resultado = objectMapper.readValue(response, Cliente.class);
        // Assert
        assertAll(
                () -> assertEquals(resultado.getNome(), clienteTeste.getNome()),
                () -> assertEquals(resultado.getEndereco().getLogradouro(), clienteTeste.getEndereco().getLogradouro()),
                () -> assertEquals(resultado.getEndereco().getNumero(), clienteTeste.getEndereco().getNumero())
        );
    }

    @Test
    @DisplayName("Tentar criar um cliente com um código de acesso já existente e" +
            " verificar se retorna erro 400")
    void criaClienteComCodigoJaExistente() throws Exception {
        ClientePostPutRequestDTO dto = ClientePostPutRequestDTO
                .builder()
                .nome("Qualquer")
                .endereco(Endereco.builder()
                        .numero(55)
                        .cidade("Campina Grande")
                        .bairro("Universitario")
                        .estado("Paraiba")
                        .logradouro("Rua Central")
                        .build())
                .codigoAcesso("123456")
                .build();
        var response = this.driver
                .perform(post(URI_CLIENTES)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.clienteJacksonTester
                                .write(dto)
                                .getJson()))
                .andExpect(status().isBadRequest())
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Atualizar um cliente existente com dados válidos e" +
            " verificar atualização bem-sucedida")
    void atualizaClienteDeFormaValida() throws Exception {
        ClientePostPutRequestDTO dto = ClientePostPutRequestDTO
                .builder()
                .nome("Atualizado")
                .endereco(Endereco.builder()
                        .numero(10)
                        .cidade("Campina Grande")
                        .bairro("Universitario")
                        .estado("Paraiba")
                        .logradouro("Rua Central")
                        .build())
                .codigoAcesso("980765")
                .build();
        var response = this.driver
                .perform(put(this.URI_CLIENTES + "/{id}?codigoAcesso={codigoAcesso}",
                        this.cliente.getId(), this.cliente.getCodigoAcesso())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.clienteJacksonTester
                                .write(dto)
                                .getJson()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.nome").value(dto.getNome()))
                .andReturn().getResponse().getContentAsString();

        Cliente resultado = objectMapper.readValue(response, Cliente.class);
        // Assert
        assertAll(
                () -> assertEquals(resultado.getNome(), dto.getNome()),
                () -> assertEquals(resultado.getEndereco().getLogradouro(), dto.getEndereco().getLogradouro()),
                () -> assertEquals(resultado.getEndereco().getNumero(), dto.getEndereco().getNumero())
        );

    }

    @Test
    @DisplayName("Recuperar um cliente pelo ID e verificar se os dados estão corretos")
    void recuperaClientePorId() throws Exception {
        var response = this.driver
                .perform(get(this.URI_CLIENTES + "/{id}",
                        this.cliente.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.nome").value(this.cliente.getNome()))
                .andReturn().getResponse().getContentAsString();

        Cliente resultado = objectMapper.readValue(response, Cliente.class);
        // Assert
        assertAll(
                () -> assertEquals(resultado.getNome(), this.cliente.getNome()),
                () -> assertEquals(resultado.getEndereco().getLogradouro(), this.cliente.getEndereco().getLogradouro()),
                () -> assertEquals(resultado.getEndereco().getNumero(), this.cliente.getEndereco().getNumero())
        );
    }

    @Test
    @DisplayName("Recuperar a lista de todos os clientes e verificar o status 200 OK")
    void recuperaTodosOsClientes() throws Exception {
        var response = this.driver
                .perform(get(URI_CLIENTES)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].nome").value(this.cliente.getNome()))
                .andReturn().getResponse();
    }

    @Test
    @DisplayName("Excluir um cliente existente com código válido e" +
            " verificar se a exclusão é bem-sucedida")
    void deletaClienteValido() throws Exception {
        var response = this.driver
                .perform(delete(this.URI_CLIENTES + "/{id}?codigoAcesso={codigoAcesso}",
                        this.cliente.getId(), this.cliente.getCodigoAcesso())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @Test
    @DisplayName("Tentar criar um cliente com código de acesso menor que 6 dígitos e" +
            " verificar se retorna erro 400")
    void criaClienteComCodigoMenorQue6Digitos() throws Exception {
        ClientePostPutRequestDTO dto = ClientePostPutRequestDTO
                .builder()
                .nome("Nome")
                .endereco(Endereco.builder()
                        .numero(9)
                        .cidade("Campina Grande")
                        .bairro("Universitario")
                        .estado("Paraiba")
                        .logradouro("Rua Central")
                        .build())
                .codigoAcesso("12345")
                .build();
        var response = this.driver
                .perform(post(this.URI_CLIENTES)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.clienteJacksonTester
                                .write(dto)
                                .getJson()))
                .andExpect(status().isBadRequest())
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Tentar criar um cliente com código de acesso maior que 6 dígitos e" +
            " verificar se retorna erro 400")
    void criaClienteComCodigoMaiorQue6Digitos() throws Exception {
        ClientePostPutRequestDTO dto = ClientePostPutRequestDTO
                .builder()
                .nome("Nome")
                .endereco(Endereco.builder()
                        .numero(5)
                        .cidade("Campina Grande")
                        .bairro("Universitario")
                        .estado("Paraiba")
                        .logradouro("Rua Central")
                        .build())
                .codigoAcesso("1234567")
                .build();
        var response = this.driver
                .perform(post(this.URI_CLIENTES)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.clienteJacksonTester
                                .write(dto)
                                .getJson()))
                .andExpect(status().isBadRequest())
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Tentar criar um cliente com código de acesso inválido (caracteres especiais) e" +
            " verificar se retorna erro 400")
    void criaClienteComCodigoInvalido() throws Exception {
        ClientePostPutRequestDTO dto = ClientePostPutRequestDTO
                .builder()
                .nome("Nome")
                .endereco(Endereco.builder()
                        .numero(7)
                        .cidade("Campina Grande")
                        .bairro("Universitario")
                        .estado("Paraiba")
                        .logradouro("Rua Central")
                        .build())
                .codigoAcesso("1!@#%&")
                .build();
        var response = this.driver
                .perform(post(this.URI_CLIENTES)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.clienteJacksonTester
                                .write(dto)
                                .getJson()))
                .andExpect(status().isBadRequest())
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Tentar excluir um cliente com um código de acesso inválido e" +
            " verificar se retorna erro 400")
    void deletaClienteComCodigoInvalido() throws Exception {
        var response = this.driver
                .perform(delete(this.URI_CLIENTES +
                                "/{id}?codigoAcesso={codigoAcesso}",
                        this.cliente.getId(), "invalid_code")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Tentar recuperar um cliente com ID inexistente e verificar se retorna erro 404")
    void recuperaClienteBaseadoEmIdInexistente() throws Exception {
        var response = this.driver
                .perform(get(this.URI_CLIENTES + "/{id}",
                        999999)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }
}
