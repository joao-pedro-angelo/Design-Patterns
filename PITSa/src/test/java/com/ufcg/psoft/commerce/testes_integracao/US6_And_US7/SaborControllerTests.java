package PITSa.src.test.java.com.ufcg.psoft.commerce.testes_integracao.US6_And_US7;

import com.ufcg.psoft.commerce.dto.sabor.SaborPostPutRequestDTO;
import com.ufcg.psoft.commerce.model.pizza.TipoSabor;
import com.ufcg.psoft.commerce.model.estabelecimento.Estabelecimento;
import com.ufcg.psoft.commerce.model.pizza.Sabor;
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

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
@DisplayName("Testes do controlador de Sabores")
public class SaborControllerTests {

    final String URI_SABORES = "/sabor";
    @Autowired
    MockMvc driver;

    @Autowired
    SaborRepository saborRepository;

    @Autowired
    EstabelecimentoRepository estabelecimentoRepository;

    @Autowired
    JacksonTester<SaborPostPutRequestDTO> saborPostPutDTOJacksonTester;
    private Estabelecimento estabelecimento;
    private Sabor sabor;
    @BeforeEach
    void up() {
        estabelecimento = new Estabelecimento();
        estabelecimento.setCodigoAcessoEstabelecimento("123456");
        estabelecimento = estabelecimentoRepository.save(estabelecimento);

        sabor = new Sabor();
        sabor.setNome("Frango com Catupiry");
        sabor.setTipo(TipoSabor.SALGADO);
        sabor.setValorMedia(35.90);
        sabor.setValorGrande(45.90);
        sabor.setDisponivel(true);
        sabor.setEstabelecimento(estabelecimento);
        saborRepository.save(sabor);
    }

    @AfterEach
    void down() {
        saborRepository.deleteAll();
        estabelecimentoRepository.deleteAll();
    }

    @Test
    @DisplayName("Listar todos sabores do estabelecimento")
    void listaSabores() throws Exception {
        Sabor sabor = new Sabor();
        sabor.setNome("Calabresa com Cebola");
        sabor.setTipo(TipoSabor.SALGADO);
        sabor.setValorMedia(40.00);
        sabor.setValorGrande(55.00);
        sabor.setDisponivel(false);
        sabor.setEstabelecimento(estabelecimento);
        saborRepository.save(sabor);

        this.driver.perform(get(URI_SABORES)
                    .contentType(MediaType.APPLICATION_JSON)
                    .param("estabelecimentoId", estabelecimento.getId().toString())
                    .param("estabelecimentoCodigoAcesso", estabelecimento.getCodigoAcessoEstabelecimento()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.length()").value(2))
            .andDo(print())
            .andReturn().getResponse();

    }

    @Test
    @DisplayName("Ler sabor")
    void getSabor() throws Exception {
        this.driver.perform(
                get(URI_SABORES + "/" + sabor.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .param("estabelecimentoId", estabelecimento.getId().toString())
                .param("estabelecimentoCodigoAcesso", estabelecimento.getCodigoAcessoEstabelecimento())
            )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.nome").value(sabor.getNome()))
                .andExpect(jsonPath("$.tipo").value(sabor.getTipo().toValue()))
                .andExpect(jsonPath("$.valorGrande").value(sabor.getValorGrande()))
                .andExpect(jsonPath("$.valorMedia").value(sabor.getValorMedia()))
                .andExpect(jsonPath("$.disponivel").value(sabor.getDisponivel()))
                .andExpect(jsonPath("$.estabelecimentoId").value(estabelecimento.getId()))
                .andDo(print())
            .andReturn().getResponse();

    }

    @Test
    @DisplayName("Criar sabor")
    void criarSabor() throws Exception {
        SaborPostPutRequestDTO payload = SaborPostPutRequestDTO.builder()
                .nome("Calabresa com Cebola")
                .tipo(TipoSabor.SALGADO)
                .valorGrande(40.00)
                .valorMedia(55.00).build();

        this.driver.perform(
                        post(URI_SABORES)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(this.saborPostPutDTOJacksonTester.write(payload).getJson())
                                .param("estabelecimentoId", estabelecimento.getId().toString())
                                .param("estabelecimentoCodigoAcesso", estabelecimento.getCodigoAcessoEstabelecimento())
                )
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.nome").value(payload.getNome()))
                .andExpect(jsonPath("$.tipo").value(payload.getTipo().toValue()))
                .andExpect(jsonPath("$.valorGrande").value(payload.getValorGrande()))
                .andExpect(jsonPath("$.valorMedia").value(payload.getValorMedia()))
                .andExpect(jsonPath("$.disponivel").value(true))
                .andExpect(jsonPath("$.estabelecimentoId").value(estabelecimento.getId()))
                .andDo(print())
                .andReturn().getResponse();

    }

    @Test
    @DisplayName("Editar sabor")
    void editarSabor() throws Exception {
        SaborPostPutRequestDTO payload = SaborPostPutRequestDTO.builder()
                .nome("Nome atualizado")
                .tipo(TipoSabor.SALGADO)
                .valorGrande(40.00)
                .valorMedia(55.00)
                .disponivel(false)
                .build();

        this.driver.perform(
                        put(URI_SABORES + "/{id}", sabor.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(this.saborPostPutDTOJacksonTester.write(payload).getJson())
                                .param("estabelecimentoId", estabelecimento.getId().toString())
                                .param("estabelecimentoCodigoAcesso", estabelecimento.getCodigoAcessoEstabelecimento())
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(sabor.getId()))
                .andExpect(jsonPath("$.nome").value(payload.getNome()))
                .andExpect(jsonPath("$.tipo").value(payload.getTipo().toValue()))
                .andExpect(jsonPath("$.valorGrande").value(payload.getValorGrande()))
                .andExpect(jsonPath("$.valorMedia").value(payload.getValorMedia()))
                .andExpect(jsonPath("$.disponivel").value(false))
                .andExpect(jsonPath("$.estabelecimentoId").value(estabelecimento.getId()))
                .andDo(print())
                .andReturn().getResponse();
    }

    @Test
    @DisplayName("Remover sabor")
    void removerSabor() throws Exception {
        this.driver.perform(
                        delete(URI_SABORES + "/{id}", sabor.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("estabelecimentoId", estabelecimento.getId().toString())
                                .param("estabelecimentoCodigoAcesso", estabelecimento.getCodigoAcessoEstabelecimento())
                )
                .andExpect(status().isNoContent())
                .andDo(print())
                .andReturn().getResponse();

        assertTrue(saborRepository.findById(sabor.getId()).isEmpty());
    }

    @Test
    @DisplayName("Listar todos sabores")
    void listaSaboresCliente() throws Exception {
        Sabor sabor = new Sabor();
        sabor.setNome("Calabresa com Cebola");
        sabor.setTipo(TipoSabor.SALGADO);
        sabor.setValorMedia(40.00);
        sabor.setValorGrande(55.00);
        sabor.setDisponivel(false);
        sabor.setEstabelecimento(estabelecimento);
        saborRepository.save(sabor);

        this.driver.perform(get(URI_SABORES)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("estabelecimentoId", estabelecimento.getId().toString()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2 ))
                .andDo(print())
                .andReturn().getResponse();

    }

    @Test
    @DisplayName("Listar todos sabores filtrado por tipo")
    void listaSaboresPorTipoCliente() throws Exception {
        Sabor sabor = new Sabor();
        sabor.setNome("Chocolate com Morango");
        sabor.setTipo(TipoSabor.DOCE);
        sabor.setValorMedia(32.00);
        sabor.setValorGrande(45.00);
        sabor.setDisponivel(true);
        sabor.setEstabelecimento(estabelecimento);
        saborRepository.save(sabor);

        Sabor sabor2 = new Sabor();
        sabor2.setNome("Chocolate");
        sabor2.setTipo(TipoSabor.DOCE);
        sabor2.setValorMedia(32.00);
        sabor2.setValorGrande(45.00);
        sabor2.setDisponivel(false);
        sabor2.setEstabelecimento(estabelecimento);
        saborRepository.save(sabor2);

        this.driver.perform(get(URI_SABORES)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("estabelecimentoId", estabelecimento.getId().toString())
                        .param("tipo", TipoSabor.DOCE.toValue()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2))
                .andDo(print())
                .andReturn().getResponse();

    }

    @Test
    @DisplayName("Falha na criação de um sabor de pizza sem autenticação")
    void criarSaborEstabelecimentoInvalido() throws Exception {
        SaborPostPutRequestDTO payload = SaborPostPutRequestDTO.builder()
                .nome("Calabresa com Cebola")
                .tipo(TipoSabor.SALGADO)
                .valorGrande(40.00)
                .valorMedia(55.00).build();

        this.driver.perform(
                        post(URI_SABORES)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(this.saborPostPutDTOJacksonTester.write(payload).getJson())
                                .param("estabelecimentoId", estabelecimento.getId().toString())
                                .param("estabelecimentoCodigoAcesso", "123455")
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value("Codigo de acesso invalido!"))
                .andDo(print())
                .andReturn().getResponse();

    }

    @Test
    @DisplayName("Falha na edição de um sabor de pizza sem autenticação")
    void editarSaborEstabelecimentoInvalido() throws Exception {
        SaborPostPutRequestDTO payload = SaborPostPutRequestDTO.builder()
                .nome("Nome atualizado")
                .tipo(TipoSabor.SALGADO)
                .valorGrande(40.00)
                .valorMedia(55.00)
                .disponivel(false)
                .build();

        this.driver.perform(
                        put(URI_SABORES + "/{id}", sabor.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(this.saborPostPutDTOJacksonTester.write(payload).getJson())
                                .param("estabelecimentoId", estabelecimento.getId().toString())
                                .param("estabelecimentoCodigoAcesso", "123455")
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value("Codigo de acesso invalido!"))
                .andDo(print())
                .andReturn().getResponse();
    }

}
