package PITSa.src.test.java.com.ufcg.psoft.commerce.testes_integracao.US13;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufcg.psoft.commerce.model.associacao.Associacao;
import com.ufcg.psoft.commerce.model.cliente.Cliente;
import com.ufcg.psoft.commerce.model.cliente.Endereco;
import com.ufcg.psoft.commerce.model.entregador.Entregador;
import com.ufcg.psoft.commerce.model.entregador.TipoVeiculo;
import com.ufcg.psoft.commerce.model.entregador.Veiculo;
import com.ufcg.psoft.commerce.model.estabelecimento.Estabelecimento;
import com.ufcg.psoft.commerce.model.pedido.Pedido;
import com.ufcg.psoft.commerce.model.pedido.statePedido.PedidoPronto;
import com.ufcg.psoft.commerce.repository.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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
@DisplayName("Testes para funcionalidade de notificação de pedido em rota")
public class NotificacaoPedidoEmRotaTests {

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

    private Estabelecimento estabelecimento1;

    private Entregador entregador;
    private Cliente cliente;

    @BeforeEach
    void setup() {
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
        Estabelecimento estabelecimento = Estabelecimento.builder()
                .codigoAcessoEstabelecimento("123456")
                .build();
        estabelecimento1 = this.estabelecimentoRepository.save(estabelecimento);

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

        // Criação do pedido com status inicial PRONTO
        Pedido pedido = Pedido.builder()
                .itens(new ArrayList<>())
                .cliente(cliente)
                .estabelecimento(estabelecimento1)
                .entregador(entregador)
                .status(new PedidoPronto())
                .build();

        pedido.setStatus(new PedidoPronto());
        this.pedido = this.pedidoRepository.save(pedido);

        Associacao associacao = Associacao.builder()
                .entregador(entregador)
                .estabelecimento(estabelecimento1)
                .status(true)
                .build();

        associacaoRepository.save(associacao);
    }

    @AfterEach
    void deleteAll() {
        // Limpa todos os registros após cada teste
        this.pedidoRepository.deleteAll();
    }

    @Test
    @DisplayName("Deve mudar o status do pedido para PedidoEmRota após atribuir um entregador")
    void deveMudarStatusParaEmRota() throws Exception {
        // Atualiza o status do pedido para "Pedido Pronto" antes de mudar para "Pedido Em Rota"
        this.pedido.setStatus(new PedidoPronto());
        this.pedidoRepository.save(this.pedido);

        String expectedMessage = String.format(
                "-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*\n" +
                        "Notificação para o cliente %s:\nO seu pedido está em rota. " +
                        "O entregador é %s, com o veículo %s.\n" +
                        "-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*",
                cliente.getNome(),
                entregador.getNomeEntregador(),
                entregador.getVeiculoEntregador()
        );

        // Redireciona a saída do console para verificar a notificação
        PrintStream originalOut = System.out;
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        // Realiza a requisição PATCH para mudar o status do pedido para "Pedido Em Rota"
        this.mockMvc.perform(patch("/pedidos/{id}/atribuir-entregador", this.pedido.getId())
                        .param("estabelecimentoId", String.valueOf(estabelecimento1.getId()))
                        .param("codigoAcesso", String.valueOf(estabelecimento1.getCodigoAcessoEstabelecimento()))
                        .param("entregadorId", String.valueOf(entregador.getIdEntregador()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());  // Verifica se a resposta HTTP foi 200 (OK)
        assertTrue(outContent.toString().contains(expectedMessage),
                "A mensagem esperada não foi encontrada no console");
        // Restaura a saída original do console
        System.setOut(originalOut);
    }
}
