package PITSa.src.test.java.com.ufcg.psoft.commerce.testes_integracao.US4_US5;

import com.ufcg.psoft.commerce.dto.associacao.AssociacaoPostDTO;
import com.ufcg.psoft.commerce.dto.associacao.AssociacaoPutDTO;
import com.ufcg.psoft.commerce.model.associacao.Associacao;
import com.ufcg.psoft.commerce.model.entregador.Entregador;
import com.ufcg.psoft.commerce.model.estabelecimento.Estabelecimento;
import com.ufcg.psoft.commerce.repository.AssociacaoRepository;
import com.ufcg.psoft.commerce.repository.EntregadorRepository;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
@DisplayName("Testes do controlador de Associação")
public class AssociacaoTests {

    //--------------------------------------------------------------
    // Configurações básicas de testes
    final String URI_ASSOCIACAO = "/associacao";
    @Autowired
    MockMvc driver;
    @Autowired
    AssociacaoRepository associacaoRepository;
    @Autowired
    EntregadorRepository entregadorRepository;
    @Autowired
    EstabelecimentoRepository estabelecimentoRepository;
    @Autowired
    JacksonTester<AssociacaoPostDTO> associacaoPostDTOJacksonTester;
    @Autowired
    JacksonTester<AssociacaoPutDTO> associacaoPutDTOJacksonTester;
    Entregador entregador;
    Estabelecimento estabelecimento;
    Associacao associacao;

    @BeforeEach
    void setUp() {
        this.entregador = Entregador.builder()
                .nomeEntregador("Nome do Entregador")
                .codigoAcessoEntregador("123456")
                .build();
        this.entregador = this.entregadorRepository.save(this.entregador);
        this.estabelecimento = Estabelecimento.builder()
                .codigoAcessoEstabelecimento("987654")
                .build();
        this.estabelecimento = this.estabelecimentoRepository
                .save(this.estabelecimento);

        // Criando uma associação inicial para teste
        AssociacaoPostDTO associacaoDTO = AssociacaoPostDTO.builder()
                .idEntregador(this.entregador.getIdEntregador())
                .codigoAcessoEntregador(this.entregador.getCodigoAcessoEntregador())
                .estabelecimentoId(this.estabelecimento.getId())
                .build();
        this.associacao = this.associacaoRepository.save(Associacao.builder()
                .entregador(this.entregador)
                .estabelecimento(this.estabelecimento)
                .status(false) // Inicialmente não aprovado
                .build());

        //estabelecimento.getAssociacoes().add(associacao);
    }

    @AfterEach
    void tearDown() {
        this.associacaoRepository.deleteAll();
        this.entregadorRepository.deleteAll();
        this.estabelecimentoRepository.deleteAll();
    }

    @Test
    @DisplayName("Criar uma associação com dados válidos" +
            " e verificar a criação bem-sucedida")
    void criarAssociacaoValida() throws Exception {
        AssociacaoPostDTO dto = AssociacaoPostDTO.builder()
                .idEntregador(this.entregador.getIdEntregador())
                .codigoAcessoEntregador(this.entregador
                        .getCodigoAcessoEntregador())
                .estabelecimentoId(this.estabelecimento.getId())
                .build();

        var response = this.driver
                .perform(post(this.URI_ASSOCIACAO)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.associacaoPostDTOJacksonTester
                                .write(dto)
                                .getJson()))
                .andReturn().getResponse();

        assertThat(response.getStatus())
                .isEqualTo(HttpStatus.CREATED.value());
    }

    @Test
    @DisplayName("Tentar criar uma associação com um código" +
            " de acesso inválido e verificar se retorna erro 400")
    void criarAssociacaoComCodigoInvalido() throws Exception {
        AssociacaoPostDTO dto = AssociacaoPostDTO.builder()
                .idEntregador(this.entregador.getIdEntregador())
                .codigoAcessoEntregador("12345") // Código com menos de 6 dígitos
                .estabelecimentoId(this.estabelecimento.getId())
                .build();

        var response = this.driver
                .perform(post(this.URI_ASSOCIACAO)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.associacaoPostDTOJacksonTester
                                .write(dto)
                                .getJson()))
                .andExpect(status().isBadRequest())
                .andReturn().getResponse();

        assertThat(response.getStatus())
                .isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Tentar criar uma associação com um entregador" +
            " que não existe e verificar se retorna erro 404")
    void criarAssociacaoComEntregadorInexistente() throws Exception {
        AssociacaoPostDTO dto = AssociacaoPostDTO.builder()
                .idEntregador(999999L) // ID inexistente
                .codigoAcessoEntregador("123456")
                .estabelecimentoId(this.estabelecimento.getId())
                .build();

        var response = this.driver
                .perform(post(this.URI_ASSOCIACAO)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.associacaoPostDTOJacksonTester
                                .write(dto)
                                .getJson()))
                .andExpect(status().isNotFound())
                .andReturn().getResponse();

        assertThat(response.getStatus())
                .isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @Test
    @DisplayName("Tentar criar uma associação com um" +
            " estabelecimento que não existe e verificar se retorna erro 404")
    void criarAssociacaoComEstabelecimentoInexistente() throws Exception {
        AssociacaoPostDTO dto = AssociacaoPostDTO.builder()
                .idEntregador(this.entregador.getIdEntregador())
                .codigoAcessoEntregador
                        (this.entregador.getCodigoAcessoEntregador())
                .estabelecimentoId(999999L) // ID inexistente
                .build();

        var response = this.driver
                .perform(post(this.URI_ASSOCIACAO)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.associacaoPostDTOJacksonTester
                                .write(dto)
                                .getJson()))
                .andExpect(status().isNotFound())
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @Test
    @DisplayName("Atualizar uma associação existente com dados válidos" +
            " e verificar a atualização bem-sucedida")
    void atualizaAssociacaoValida() throws Exception {
        AssociacaoPutDTO dto = AssociacaoPutDTO.builder()
                .idAssociacao(this.associacao.getId())
                .estabelecimentoId(this.estabelecimento.getId())
                .codigoAcessoEstabelecimento
                        (this.estabelecimento
                                .getCodigoAcessoEstabelecimento())
                .status(true) // Aprovação
                .build();

        var response = this.driver
                .perform(put(this.URI_ASSOCIACAO)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.associacaoPutDTOJacksonTester
                                .write(dto)
                                .getJson()))
                .andReturn().getResponse();

        assertThat(response.getStatus())
                .isEqualTo(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("Tentar atualizar uma associação com um código" +
            " de acesso inválido e verificar se retorna erro 400")
    void atualizaAssociacaoComCodigoInvalido() throws Exception {
        AssociacaoPutDTO dto = AssociacaoPutDTO.builder()
                .idAssociacao(this.associacao.getId())
                .estabelecimentoId(this.estabelecimento.getId())
                .codigoAcessoEstabelecimento("12345") // Código com menos de 6 dígitos
                .status(true)
                .build();

        var response = this.driver
                .perform(put(this.URI_ASSOCIACAO)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.associacaoPutDTOJacksonTester
                                .write(dto)
                                .getJson()))
                .andExpect(status().isBadRequest())
                .andReturn().getResponse();

        assertThat(response.getStatus())
                .isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Tentar atualizar uma associação com ID inválido " +
            "e verificar se retorna erro 404")
    void atualizaAssociacaoInexistente() throws Exception {
        AssociacaoPutDTO dto = AssociacaoPutDTO.builder()
                .idAssociacao(999999L) //Código inexistente
                .estabelecimentoId(this.estabelecimento.getId())
                .codigoAcessoEstabelecimento
                        (this.estabelecimento
                                .getCodigoAcessoEstabelecimento())
                .status(true)
                .build();

        var response = this.driver
                .perform(put(this.URI_ASSOCIACAO)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.associacaoPutDTOJacksonTester
                                .write(dto)
                                .getJson()))
                .andReturn().getResponse();

        assertThat(response.getStatus())
                .isEqualTo(HttpStatus.NOT_FOUND.value());
    }
    @Test
    @DisplayName("Estabelecimento aprova um entregador associado com código de acesso válido")
    void estabelecimentoAprovaEntregadorComSucesso() throws Exception {
        AssociacaoPutDTO dto = AssociacaoPutDTO.builder()
                .idAssociacao(this.associacao.getId())
                .estabelecimentoId(this.estabelecimento.getId())
                .codigoAcessoEstabelecimento(this.estabelecimento.getCodigoAcessoEstabelecimento())
                .status(true) // Aprovando a associação
                .build();

        var response = this.driver
                .perform(put(this.URI_ASSOCIACAO)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.associacaoPutDTOJacksonTester.write(dto).getJson()))
                .andReturn().getResponse();

        assertThat(response.getStatus())
                .isEqualTo(HttpStatus.OK.value());

        Associacao associacaoAtualizada = this.associacaoRepository.findById(this.associacao.getId()).get();
        assertThat(associacaoAtualizada.getStatus()).isTrue(); // Verifica se o status foi atualizado para true (aprovado)
    }

    @Test
    @DisplayName("Estabelecimento rejeita um entregador associado com código de acesso válido")
    void estabelecimentoRejeitaEntregadorComSucesso() throws Exception {
        AssociacaoPutDTO dto = AssociacaoPutDTO.builder()
                .idAssociacao(this.associacao.getId())
                .estabelecimentoId(this.estabelecimento.getId())
                .codigoAcessoEstabelecimento(this.estabelecimento.getCodigoAcessoEstabelecimento())
                .status(false) // Rejeitando a associação
                .build();

        var response = this.driver
                .perform(put(this.URI_ASSOCIACAO)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.associacaoPutDTOJacksonTester.write(dto).getJson()))
                .andReturn().getResponse();

        assertThat(response.getStatus())
                .isEqualTo(HttpStatus.OK.value());

        Associacao associacaoAtualizada = this.associacaoRepository.findById(this.associacao.getId()).get();
        assertThat(associacaoAtualizada.getStatus()).isFalse(); // Verifica se o status foi atualizado para false (rejeitado)
    }

    @Test
    @DisplayName("Tentativa de aprovação por entregador com código de acesso inválido")
    void tentativaDeAprovacaoPorEntregadorSemSucesso() throws Exception {
        AssociacaoPutDTO dto = AssociacaoPutDTO.builder()
                .idAssociacao(this.associacao.getId())
                .estabelecimentoId(this.estabelecimento.getId())
                .codigoAcessoEstabelecimento("codigo-invalido") // Código de acesso inválido
                .status(true) // Tentando aprovar
                .build();

        var response = this.driver
                .perform(put(this.URI_ASSOCIACAO)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.associacaoPutDTOJacksonTester.write(dto).getJson()))
                .andReturn().getResponse();

        assertThat(response.getStatus())
                .isEqualTo(HttpStatus.BAD_REQUEST.value());

        Associacao associacaoAtualizada = this.associacaoRepository.findById(this.associacao.getId()).get();
        assertThat(associacaoAtualizada.getStatus()).isFalse(); // Verifica se o status permaneceu inalterado
    }

    @Test
    @DisplayName("Tentativa de rejeição por entregador com código de acesso inválido")
    void tentativaDeRejeicaoPorEntregadorSemSucesso() throws Exception {
        AssociacaoPutDTO dto = AssociacaoPutDTO.builder()
                .idAssociacao(this.associacao.getId())
                .estabelecimentoId(this.estabelecimento.getId())
                .codigoAcessoEstabelecimento("codigo-invalido") // Código de acesso inválido
                .status(false) // Tentando rejeitar
                .build();

        var response = this.driver
                .perform(put(this.URI_ASSOCIACAO)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.associacaoPutDTOJacksonTester.write(dto).getJson()))
                .andReturn().getResponse();

        assertThat(response.getStatus())
                .isEqualTo(HttpStatus.BAD_REQUEST.value());

        Associacao associacaoAtualizada = this.associacaoRepository.findById(this.associacao.getId()).get();
        assertThat(associacaoAtualizada.getStatus()).isFalse(); // Verifica se o status permaneceu inalterado
    }
}
