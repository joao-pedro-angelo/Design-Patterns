package PITSa.src.test.java.com.ufcg.psoft.commerce.testes_integracao.US15;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufcg.psoft.commerce.model.associacao.Associacao;
import com.ufcg.psoft.commerce.model.cliente.Cliente;
import com.ufcg.psoft.commerce.model.cliente.Endereco;
import com.ufcg.psoft.commerce.model.entregador.Entregador;
import com.ufcg.psoft.commerce.model.entregador.TipoVeiculo;
import com.ufcg.psoft.commerce.model.entregador.Veiculo;
import com.ufcg.psoft.commerce.model.estabelecimento.Estabelecimento;
import com.ufcg.psoft.commerce.model.pedido.Pedido;
import com.ufcg.psoft.commerce.model.pedido.statePedido.PedidoEmRota;
import com.ufcg.psoft.commerce.model.pedido.statePedido.PedidoEntregue;
import com.ufcg.psoft.commerce.model.pedido.statePedido.PedidoPronto;
import com.ufcg.psoft.commerce.repository.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



@SpringBootTest
@Transactional
@AutoConfigureMockMvc
@DisplayName("Testes para funcionalidade de notificação de pedido entregue")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class NotificacaoPedidoEntregueTests {

    // URI base para alterar o status do pedido
    final String URI_PEDIDOS = "/pedidos/{id}/status";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private AssociacaoRepository associacaoRepository;

    @Autowired
    private EstabelecimentoRepository estabelecimentoRepository;

    @Autowired
    private EntregadorRepository entregadorRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Pedido pedido;

    private Estabelecimento estabelecimento;

    private Entregador entregador;
    Cliente cliente;

    @BeforeAll
    void beforeAll() {
        // Criação de um endereço para o cliente
        Endereco newEndereco = Endereco.builder()
                .numero(55)
                .cidade("Campina Grande")
                .bairro("Universitario")
                .estado("Paraiba")
                .logradouro("Rua Central")
                .build();

        // Criação do cliente e salvamento no repositório
        cliente = Cliente.builder()
                .nome("Nome do Cliente")
                .codigoAcesso("123436")
                .endereco(newEndereco)
                .build();
        cliente = this.clienteRepository.save(cliente);

        // Criação do estabelecimento e salvamento no repositório
        estabelecimento = Estabelecimento.builder()
                .codigoAcessoEstabelecimento("123456")
                .build();
        estabelecimento = this.estabelecimentoRepository.save(estabelecimento);

        // Criação do veículo e do entregador
        Veiculo veiculo = Veiculo.builder()
                .placaVeiculo("ABC1234")
                .corVeiculo("Preto")
                .tipoVeiculo(TipoVeiculo.MOTO)
                .build();
        entregador = this.entregadorRepository.save(Entregador.builder()
                .nomeEntregador("Nome do Entregador")
                .codigoAcessoEntregador("123459")
                .veiculoEntregador(veiculo)
                .build());
    }

    @AfterAll
    void afterAll() {
        clienteRepository.deleteAll();
        estabelecimentoRepository.deleteAll();
        entregadorRepository.deleteAll();
    }

    @BeforeEach
    void setup() {
        // Criação do pedido com status inicial PRONTO
        Pedido pedido = Pedido.builder()
                .itens(new ArrayList<>())
                .cliente(cliente)
                .estabelecimento(estabelecimento)
                .entregador(entregador)
                .status(new PedidoPronto())
                .build();

        pedido.setStatus(new PedidoEmRota());
        this.pedido = this.pedidoRepository.save(pedido);

        Associacao associacao = Associacao.builder()
                .entregador(entregador)
                .estabelecimento(estabelecimento)
                .status(true)
                .build();

        associacaoRepository.save(associacao);
    }

    @AfterEach
    void deleteAll() {
        pedidoRepository.deleteAll();
        associacaoRepository.deleteAll();
    }

    @Test
    @DisplayName("deve notificar o estabelecimento quando pedido entregue")
    void deveNotificarEstabelecimentoQuandoPedidoEntregue() throws Exception {
        String expectedMessage = String.format(
                "-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*\n" +
                        "Notificação para o estabelecimento %s:\n" +
                        "O pedido foi entregue: id: %s:\n" +
                        "-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*",
                estabelecimento.getId(),
                pedido.getId()
        );
        // Redireciona a saída do console para verificar a notificação
        PrintStream originalOut = System.out;
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // Realiza a requisição PATCH para mudar o status do pedido para "Pedido Entregue"
        this.mockMvc.perform(patch("/pedidos/{id}/entrega", this.pedido.getId())
                        .param("clienteId", String.valueOf(cliente.getId()))
                        .param("codigoAcesso", String.valueOf(cliente.getCodigoAcesso()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());  // Verifica se a resposta HTTP foi 200 (OK)
        // Verifica que a mensagem de notificação foi exibida no console
        assertTrue(outContent.toString().contains(expectedMessage),
                "A mensagem esperada não foi encontrada no console");
        // Restaura a saída original do console
        System.setOut(originalOut);
    }
}

