package PITSa.src.test.java.com.ufcg.psoft.commerce.testes_integracao.US3;

import com.ufcg.psoft.commerce.dto.entregador.EntregadorPostRequestDTO;
import com.ufcg.psoft.commerce.dto.entregador.EntregadorPutDTO;
import com.ufcg.psoft.commerce.model.entregador.Entregador;
import com.ufcg.psoft.commerce.model.entregador.TipoVeiculo;
import com.ufcg.psoft.commerce.model.entregador.Veiculo;
import com.ufcg.psoft.commerce.repository.EntregadorRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
@DisplayName("Testes do controlador de Entregadores")
public class EntregadorTestsCRUD {

    //--------------------------------------------------------------
    // Configurações básicas de testes
    final String URI_ENTREGADORES = "/entregador";

    @Autowired
    MockMvc driver;

    @Autowired
    EntregadorRepository entregadorRepository;

    @Autowired
    JacksonTester<EntregadorPostRequestDTO> entregadorPostRequestJacksonTester;

    @Autowired
    JacksonTester<EntregadorPutDTO> entregadorPutDTOJacksonTester;
    Entregador entregador;

    @BeforeEach
    void setUp() {
        Veiculo veiculo = Veiculo.builder()
                .placaVeiculo("ABC1234")
                .corVeiculo("Preto")
                .tipoVeiculo(TipoVeiculo.MOTO)
                .build();
        this.entregador = this.entregadorRepository.save(Entregador.builder()
                .nomeEntregador("Nome do Entregador")
                .codigoAcessoEntregador("123456")
                .veiculoEntregador(veiculo)
                .build());
    }

    @AfterEach
    void tearDown() {
        this.entregadorRepository.deleteAll();
    }

    @Test
    @DisplayName("Criar um entregador com dados válidos e verificar a criação bem-sucedida")
    void criarEntregadorValido() throws Exception {
        EntregadorPostRequestDTO entregadorTeste = EntregadorPostRequestDTO
                .builder()
                .nomeEntregador("Nome")
                .codigoAcessoEntregador("654321")
                .veiculoEntregador(Veiculo.builder()
                        .placaVeiculo("XYZ9876")
                        .corVeiculo("Azul")
                        .tipoVeiculo(TipoVeiculo.CARRO)
                        .build())
                .build();

        var response = this.driver
                .perform(post(this.URI_ENTREGADORES)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.entregadorPostRequestJacksonTester
                                .write(entregadorTeste)
                                .getJson()))
                .andReturn().getResponse();

        assertThat(response.getStatus())
                .isEqualTo(HttpStatus.CREATED.value());
    }

    @Test
    @DisplayName("Tentar criar um entregador com um código de acesso" +
            " já existente e verificar se retorna erro 400")
    void criaEntregadorComCodigoJaExistente() throws Exception {
        EntregadorPostRequestDTO dto = EntregadorPostRequestDTO
                .builder()
                .nomeEntregador("Nome")
                .codigoAcessoEntregador("123456")
                .veiculoEntregador(Veiculo.builder()
                        .placaVeiculo("XYZ9876")
                        .corVeiculo("Azul")
                        .tipoVeiculo(TipoVeiculo.CARRO)
                        .build())
                .build();

        var response = this.driver
                .perform(post(URI_ENTREGADORES)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.entregadorPostRequestJacksonTester
                                .write(dto)
                                .getJson()))
                .andReturn().getResponse();

        assertThat(response.getStatus())
                .isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Atualizar um entregador existente com dados válidos e" +
            " verificar atualização bem-sucedida")
    void atualizaEntregadorDeFormaValida() throws Exception {
        EntregadorPutDTO dto = EntregadorPutDTO
                .builder()
                .idEntregador(this.entregador.getIdEntregador())
                .atualCodigoEntregador(this.entregador.getCodigoAcessoEntregador())
                .novoCodigoEntregador("980777")
                .build();

        var response = this.driver
                .perform(put(this.URI_ENTREGADORES)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.entregadorPutDTOJacksonTester
                                .write(dto)
                                .getJson()))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("Recuperar um entregador pelo ID e verificar se os dados estão corretos")
    void recuperaEntregadorPorId() throws Exception {
        var response = this.driver
                .perform(get(this.URI_ENTREGADORES + "/{id}",
                        this.entregador.getIdEntregador())
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("Recuperar a lista de todos os entregadores e verificar o status 200 OK")
    void recuperaTodosOsEntregadores() throws Exception {
        var response = this.driver
                .perform(get(URI_ENTREGADORES)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("Excluir um entregador existente com código válido e" +
            " verificar se a exclusão é bem-sucedida")
    void deletaEntregadorValido() throws Exception {
        var response = this.driver
                .perform(delete(this.URI_ENTREGADORES +
                                "/{id}?codigoAcessoEntregador={codigoAcessoEntregador}",
                        this.entregador.getIdEntregador(),
                        this.entregador.getCodigoAcessoEntregador())
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @Test
    @DisplayName("Tentar criar um entregador com código de acesso menor que 6 dígitos " +
            "e verificar se retorna erro 400")
    void criaEntregadorComCodigoMenorQue6Digitos() throws Exception {
        EntregadorPostRequestDTO dto = EntregadorPostRequestDTO
                .builder()
                .nomeEntregador("Nome")
                .codigoAcessoEntregador("12345")
                .veiculoEntregador(Veiculo.builder()
                        .placaVeiculo("XYZ9876")
                        .corVeiculo("Azul")
                        .tipoVeiculo(TipoVeiculo.CARRO)
                        .build())
                .build();

        var response = this.driver
                .perform(post(this.URI_ENTREGADORES)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.entregadorPostRequestJacksonTester
                                .write(dto)
                                .getJson()))
                .andReturn().getResponse();

        assertThat(response.getStatus())
                .isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Tentar criar um entregador com código de acesso " +
            "maior que 6 dígitos e verificar se retorna erro 400")
    void criaEntregadorComCodigoMaiorQue6Digitos() throws Exception {
        EntregadorPostRequestDTO dto = EntregadorPostRequestDTO
                .builder()
                .nomeEntregador("Nome")
                .codigoAcessoEntregador("1234567")
                .veiculoEntregador(Veiculo.builder()
                        .placaVeiculo("XYZ9876")
                        .corVeiculo("Azul")
                        .tipoVeiculo(TipoVeiculo.CARRO)
                        .build())
                .build();

        var response = this.driver
                .perform(post(this.URI_ENTREGADORES)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.entregadorPostRequestJacksonTester
                                .write(dto)
                                .getJson()))
                .andReturn().getResponse();

        assertThat(response.getStatus())
                .isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Tentar criar um entregador com código " +
            "de acesso inválido (caracteres especiais) e verificar se retorna erro 400")
    void criaEntregadorComCodigoInvalido() throws Exception {
        EntregadorPostRequestDTO dto = EntregadorPostRequestDTO
                .builder()
                .nomeEntregador("Nome")
                .codigoAcessoEntregador("1!@#%&")
                .veiculoEntregador(Veiculo.builder()
                        .placaVeiculo("XYZ9876")
                        .corVeiculo("Azul")
                        .tipoVeiculo(TipoVeiculo.CARRO)
                        .build())
                .build();

        var response = this.driver
                .perform(post(this.URI_ENTREGADORES)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.entregadorPostRequestJacksonTester
                                .write(dto)
                                .getJson()))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Tentar excluir um entregador com um código " +
            "de acesso inválido e verificar se retorna erro 400")
    void deletaEntregadorComCodigoInvalido() throws Exception {
        var response = this.driver
                .perform(delete(this.URI_ENTREGADORES +
                                "/{id}?codigoAcessoEntregador={codigoAcessoEntregador}",
                        this.entregador.getIdEntregador(), "invalid_code")
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Tentar recuperar um entregador com ID inexistente e" +
            " verificar se retorna erro 404")
    void recuperaEntregadorBaseadoEmIdInexistente() throws Exception {
        var response = this.driver
                .perform(get(this.URI_ENTREGADORES + "/{id}",
                        999999)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }
}
