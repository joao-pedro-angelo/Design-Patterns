package PITSa.src.test.java.com.ufcg.psoft.commerce.testes_integracao.US9;

import com.ufcg.psoft.commerce.dto.sabor.SaborPatchStatusRequestDTO;
import com.ufcg.psoft.commerce.dto.sabor.SaborPostPutRequestDTO;
import com.ufcg.psoft.commerce.model.pizza.TipoSabor;
import com.ufcg.psoft.commerce.model.cliente.Cliente;
import com.ufcg.psoft.commerce.model.estabelecimento.Estabelecimento;
import com.ufcg.psoft.commerce.model.pizza.Sabor;
import com.ufcg.psoft.commerce.repository.ClienteRepository;
import com.ufcg.psoft.commerce.repository.EstabelecimentoRepository;
import com.ufcg.psoft.commerce.repository.SaborRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.hamcrest.Matchers.contains;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Classe de testes de integração para as funcionalidades da US9.
 */
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
@DisplayName("Testes US9 - Funcionalidades de Sabor")
public class SaborUS9ControllerTests {

    final String URI_SABORES = "/sabor";

    @Autowired
    MockMvc driver;

    @Autowired
    SaborRepository saborRepository;

    @Autowired
    EstabelecimentoRepository estabelecimentoRepository;

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    JacksonTester<SaborPostPutRequestDTO> saborPostPutDTOJacksonTester;

    @Autowired
    JacksonTester<SaborPatchStatusRequestDTO> saborPatchStatusDTOJacksonTester;

    private Estabelecimento estabelecimento;
    private Sabor sabor;

    /**
     * Méto
     * do que configura o estado inicial antes de cada teste.
     * Cria um estabelecimento e um sabor padrão no banco de dados.
     */
    @BeforeEach
    void up() {
        this.estabelecimento = new Estabelecimento();
        this.estabelecimento.setCodigoAcessoEstabelecimento("123456");
        this.estabelecimento = this.estabelecimentoRepository.save(this.estabelecimento);

        this.sabor = new Sabor();
        this.sabor.setNome("Frango com Catupiry");
        this.sabor.setTipo(TipoSabor.SALGADO);
        this.sabor.setValorMedia(35.90);
        this.sabor.setValorGrande(45.90);
        this.sabor.setDisponivel(false);
        this.sabor.setEstabelecimento(this.estabelecimento);
        this.saborRepository.save(this.sabor);
    }

    /**
     * Méto
     * que limpa o estado do banco de dados após cada teste.
     */
    @AfterEach
    void down() {
        this.saborRepository.deleteAll();
        this.estabelecimentoRepository.deleteAll();
    }

    /**
     * Teste para verificar a listagem de todos os sabores de um estabelecimento,
     * garantindo que sabores indisponíveis sejam exibidos no final da lista.
     */
    @Test
    @DisplayName("Listar todos sabores do estabelecimento (sabores indisponíveis no final)")
    void listaSabores() throws Exception {
        // Cria novos sabores adicionais
        Sabor sabor1 = new Sabor();
        sabor1.setNome("Calabresa com Cebola");
        sabor1.setTipo(TipoSabor.SALGADO);
        sabor1.setValorMedia(40.00);
        sabor1.setValorGrande(55.00);
        sabor1.setDisponivel(true);
        sabor1.setEstabelecimento(this.estabelecimento);
        this.saborRepository.save(sabor1);

        Sabor sabor2 = new Sabor();
        sabor2.setNome("Chocolate");
        sabor2.setTipo(TipoSabor.DOCE);
        sabor2.setValorMedia(32.00);
        sabor2.setValorGrande(45.00);
        sabor2.setDisponivel(false);
        sabor2.setEstabelecimento(this.estabelecimento);
        this.saborRepository.save(sabor2);

        Sabor sabor3 = new Sabor();
        sabor3.setNome("Chocolate com Morango");
        sabor3.setTipo(TipoSabor.DOCE);
        sabor3.setValorMedia(32.00);
        sabor3.setValorGrande(45.00);
        sabor3.setDisponivel(true);
        sabor3.setEstabelecimento(this.estabelecimento);
        this.saborRepository.save(sabor3);

        // Realiza a requisição para listar os sabores
        this.driver.perform(get(this.URI_SABORES)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("estabelecimentoId", this.estabelecimento.getId().toString()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(4))  // Verifica que 4 sabores foram listados
                .andExpect(jsonPath("$[2:].disponivel", contains(false, false)))  // Verifica que os sabores indisponíveis estão no final
                .andDo(print())
                .andReturn().getResponse();
    }

    /**
     * Teste para verificar a modificação da disponibilidade de um sabor.
     */
    @Test
    @DisplayName("Modificar a disponibilidade de um sabor")
    void modificarDisponibilidadeSabor() throws Exception {
        // Cria o payload de modificação de disponibilidade
        SaborPatchStatusRequestDTO payload = SaborPatchStatusRequestDTO.builder()
                .estabelecimentoId(this.estabelecimento.getId())
                .saborId(this.sabor.getId())
                .codigoAcesso(this.estabelecimento.getCodigoAcessoEstabelecimento())
                .disponivel(true)
                .build();

        // Realiza a requisição para modificar o status do sabor
        this.driver.perform(patch(this.URI_SABORES + "/status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.saborPatchStatusDTOJacksonTester.write(payload).getJson()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(this.sabor.getId()))
                .andExpect(jsonPath("$.disponivel").value(true))
                .andDo(print())
                .andReturn().getResponse();

        // Verifica que o sabor foi atualizado corretamente
        this.sabor = this.saborRepository.findById(this.sabor.getId()).get();
        assertAll(
                () -> assertNotNull(this.sabor),
                () -> assertEquals(true, this.sabor.getDisponivel())
        );
    }

    /**
     * Teste para verificar que os clientes interessados em um sabor são notificados
     * quando esse sabor volta a ficar disponível.
     */
    @Test
    @DisplayName("Notificar clientes interessados quando o sabor ficar disponível")
    void aoModificarDisponibilidadeClientesDevemSerNotificados() throws Exception {
        // Cria um cliente interessado no sabor
        Cliente cliente = Cliente.builder()
                .nome("Nome do Cliente")
                .codigoAcesso("123456")
                .build();
        this.clienteRepository.save(cliente);

        // Adiciona o cliente à lista de interessados no sabor
        this.sabor.getClientesInteressados().add(cliente);
        this.saborRepository.save(this.sabor);

        // Cria o payload de modificação de disponibilidade
        SaborPatchStatusRequestDTO payload = SaborPatchStatusRequestDTO.builder()
                .estabelecimentoId(this.estabelecimento.getId())
                .saborId(this.sabor.getId())
                .codigoAcesso(this.estabelecimento.getCodigoAcessoEstabelecimento())
                .disponivel(true)
                .build();

        // Redireciona a saída do console para verificar a notificação
        PrintStream originalOut = System.out;
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // Realiza a requisição para modificar o status do sabor
        this.driver.perform(patch(this.URI_SABORES + "/status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.saborPatchStatusDTOJacksonTester.write(payload).getJson()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(this.sabor.getId()))
                .andExpect(jsonPath("$.disponivel").value(true))
                .andDo(print())
                .andReturn().getResponse();

        // Verifica que a mensagem de notificação foi exibida no console
        assertTrue(outContent.toString().contains("O sabor " + this.sabor.getNome() + " está disponível para " + cliente.getNome()),
                "A mensagem esperada não foi encontrada no console");

        // Restaura a saída original do console
        System.setOut(originalOut);
    }
}
