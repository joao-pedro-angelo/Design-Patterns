package PITSa.src.test.java.com.ufcg.psoft.commerce.testes_integracao.US18;

import com.ufcg.psoft.commerce.dto.associacao.AssociacaoPostDTO;
import com.ufcg.psoft.commerce.dto.entregador.EntregadorPatchDTO;
import com.ufcg.psoft.commerce.model.associacao.Associacao;
import com.ufcg.psoft.commerce.model.entregador.Entregador;
import com.ufcg.psoft.commerce.model.entregador.TipoVeiculo;
import com.ufcg.psoft.commerce.model.entregador.Veiculo;
import com.ufcg.psoft.commerce.model.estabelecimento.Estabelecimento;
import com.ufcg.psoft.commerce.repository.AssociacaoRepository;
import com.ufcg.psoft.commerce.repository.EntregadorRepository;
import com.ufcg.psoft.commerce.repository.EstabelecimentoRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
@Transactional
@DisplayName("Testes do controlador de Entregadores - Disponibilidade")
public class DisponibilidadeEntregadorTests {

    //--------------------------------------------------------------
    // Configurações básicas de testes
    final String URI_ENTREGADORES = "/entregador/disponibilidade";
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
    JacksonTester<EntregadorPatchDTO> entregadorPatchDTOJacksonTester;

    Estabelecimento estabelecimento;
    Associacao associacao;
    EntregadorPatchDTO entregadorPatchDTO;
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
        this.estabelecimento = Estabelecimento.builder()
                .codigoAcessoEstabelecimento("987654")
                .build();
        this.estabelecimento = this.estabelecimentoRepository
                .save(this.estabelecimento);


    }

    @AfterEach
    void tearDown() {
        this.associacaoRepository.deleteAll();
        this.entregadorRepository.deleteAll();
        this.estabelecimentoRepository.deleteAll();
    }

    @Test
    @DisplayName("Testa funcionalidade de modificar disponibilidade - true")
    void testaDisponibilidadeTrue() throws Exception {
        this.entregadorPatchDTO = EntregadorPatchDTO.builder()
                .disponibilidade(true).build();

        var response = this.driver
                        .perform(patch(this.URI_ENTREGADORES)
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("codigoAcessoEntregador",
                                        this.entregador.getCodigoAcessoEntregador())
                                .content(this.entregadorPatchDTOJacksonTester
                                        .write(this.entregadorPatchDTO)
                                        .getJson()))
                                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(200);
        // Busca o entregador atualizado
        Entregador entregadorAtualizado = this.entregadorRepository
                .findEntregadorByCodigoAcessoEntregador(this.entregador.getCodigoAcessoEntregador());

        // Verifica se a disponibilidade foi alterada corretamente
        assertThat(entregadorAtualizado.getDisponibilidade()).isTrue();
    }

    @Test
    @DisplayName("Testa funcionalidade de modificar disponibilidade - false")
    void testaDisponibilidadeFalse() throws Exception {
        this.entregadorPatchDTO = EntregadorPatchDTO.builder()
                .disponibilidade(false).build();

        var response = this.driver
                .perform(patch(this.URI_ENTREGADORES)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("codigoAcessoEntregador",
                                this.entregador.getCodigoAcessoEntregador())
                        .content(this.entregadorPatchDTOJacksonTester
                                .write(this.entregadorPatchDTO)
                                .getJson()))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(200);
        // Busca o entregador atualizado
        Entregador entregadorAtualizado = this.entregadorRepository
                .findEntregadorByCodigoAcessoEntregador(this.entregador.getCodigoAcessoEntregador());

        // Verifica se a disponibilidade foi alterada corretamente
        assertThat(entregadorAtualizado.getDisponibilidade()).isFalse();
    }
}
