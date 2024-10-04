package PITSa.src.test.java.com.ufcg.psoft.commerce.testes_integracao.US1;

import com.ufcg.psoft.commerce.dto.estabelecimento.EstabelecimentoPostRequestDTO;
import com.ufcg.psoft.commerce.dto.estabelecimento.EstabelecimentoPutDTO;
import com.ufcg.psoft.commerce.model.estabelecimento.Estabelecimento;
import com.ufcg.psoft.commerce.repository.EstabelecimentoRepository;
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
@DisplayName("Testes do controlador de Estabelecimentos")
public class EstabelecimentoControllerTestsCRUD {

    //--------------------------------------------------------------
    // Configurações básicas de testes
    final String URI_ESTABELECIMENTOS = "/estabelecimento";

    @Autowired
    MockMvc driver;

    @Autowired
    EstabelecimentoRepository estabelecimentoRepository;

    @Autowired
    JacksonTester<EstabelecimentoPostRequestDTO> estabelecimentoJson;

    @Autowired
    JacksonTester<EstabelecimentoPutDTO> estabelecimentoPutJson;

    Estabelecimento estabelecimento;

    @BeforeEach
    void setUp() {
        this.estabelecimento = Estabelecimento.builder()
                .codigoAcessoEstabelecimento("123456").build();
        this.estabelecimentoRepository.save(estabelecimento);
    }

    @AfterEach
    void tearDown() {
        estabelecimentoRepository.deleteAll();
    }

    @Test
    @DisplayName("Criar estabelecimento com código válido - Retorno 201 Created")
    void criaEstabelecimentoComDadosValidos() throws Exception {
        EstabelecimentoPostRequestDTO dto
                = new EstabelecimentoPostRequestDTO("654321");
        var response = this.driver
                .perform(post(URI_ESTABELECIMENTOS)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.estabelecimentoJson.write(dto).getJson())
                ).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.getContentAsString()).contains("id");
        assertThat(response.getContentAsString())
                .contains(dto.getCodigoAcessoEstabelecimento());
    }

    @Test
    @DisplayName("Criar estabelecimento com código de acesso já existente" +
            " - Retorno 400 Bad Request")
    void criaEstabelecimentoComCodigoJaExistente() throws Exception {
        EstabelecimentoPostRequestDTO dto =
                new EstabelecimentoPostRequestDTO("123456");
        var response = this.driver
                .perform(post(URI_ESTABELECIMENTOS)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.estabelecimentoJson.write(dto).getJson())
                ).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Atualizar estabelecimento válido - Retorno 200 OK")
    void atualizaEstabelecimentoDeFormaValida() throws Exception {
        EstabelecimentoPutDTO dto = EstabelecimentoPutDTO.builder()
                .id(this.estabelecimento.getId())
                .atualCodigoEstabelecimento
                        (this.estabelecimento.getCodigoAcessoEstabelecimento())
                .novoCodigoEstabelecimento("980765")
                .build();
        var response = this.driver
                .perform(put(URI_ESTABELECIMENTOS)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.estabelecimentoPutJson.write(dto).getJson())
                ).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString())
                .contains(dto.getNovoCodigoEstabelecimento());
    }

    @Test
    @DisplayName("Recuperar todos os estabelecimentos - Retorno 200 OK")
    void recuperaTodosOsEstabelecimentos() throws Exception {
        var response = this.driver
                .perform(get(URI_ESTABELECIMENTOS)
                        .contentType(MediaType.APPLICATION_JSON)
                ).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).contains("123456");
    }

    @Test
    @DisplayName("Recuperar estabelecimento por código e ID - Retorno 200 OK")
    void recuperaEstabelecimentoUnico() throws Exception {
        var response = this.driver
                .perform(get(URI_ESTABELECIMENTOS + "/{id}",
                        estabelecimento.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                ).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).contains("123456");
    }

    @Test
    @DisplayName("Excluir estabelecimento com código válido - Retorno 204 No Content")
    void deletaEstabelecimentoValido() throws Exception {
        var response = this.driver
                .perform(delete(URI_ESTABELECIMENTOS + "/{id}?codigo={codigo}",
                        estabelecimento.getId(),
                        estabelecimento.getCodigoAcessoEstabelecimento())
                        .contentType(MediaType.APPLICATION_JSON)
                ).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @Test
    @DisplayName("Criar estabelecimento com código inválido (menos de 6 dígitos)" +
            " - Retorno 400 Bad Request")
    void criaEstabelecimentoComCodigoMenorQue6Digitos() throws Exception {
        EstabelecimentoPostRequestDTO dto
                = new EstabelecimentoPostRequestDTO("12345");
        var response = this.driver
                .perform(post(URI_ESTABELECIMENTOS)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.estabelecimentoJson.write(dto).getJson())
                ).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Criar estabelecimento com código inválido (mais de 6 dígitos) " +
            "- Retorno 400 Bad Request")
    void criaEstabelecimentoComCodigoMaiorQue6Digitos() throws Exception {
        EstabelecimentoPostRequestDTO dto
                = new EstabelecimentoPostRequestDTO("12345678");
        var response = this.driver
                .perform(post(URI_ESTABELECIMENTOS)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.estabelecimentoJson.write(dto).getJson())
                ).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Criar estabelecimento com código inválido (caracteres não numéricos) " +
            "- Retorno 400 Bad Request")
    void criaEstabelecimentoComCaracteresInvalidos() throws Exception {
        EstabelecimentoPostRequestDTO dto
                = new EstabelecimentoPostRequestDTO("!@#$%*");
        var response = this.driver
                .perform(post(URI_ESTABELECIMENTOS)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.estabelecimentoJson.write(dto).getJson())
                ).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Excluir estabelecimento com código inválido - Retorno 400 Bad Request")
    void deletaEstabelecimentoComCodigoInvalido() throws Exception {
        var response = this.driver
                .perform(delete(URI_ESTABELECIMENTOS + "/{id}?codigo={codigo}",
                        estabelecimento.getId(), "invalid_code")
                        .contentType(MediaType.APPLICATION_JSON)
                ).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Recuperar estabelecimento com ID inválido - Retorno 404 Not Found")
    void recuperaEstabelecimentoBaseadoEmCodigoInexistente() throws Exception {
        var response = this.driver
                .perform(get(URI_ESTABELECIMENTOS + "/{id}",
                        999999)
                        .contentType(MediaType.APPLICATION_JSON)
                ).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }
}
