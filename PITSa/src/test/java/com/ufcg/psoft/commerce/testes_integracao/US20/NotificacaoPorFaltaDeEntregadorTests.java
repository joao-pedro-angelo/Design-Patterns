package PITSa.src.test.java.com.ufcg.psoft.commerce.testes_integracao.US20;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufcg.psoft.commerce.dto.associacao.AssociacaoPostDTO;
import com.ufcg.psoft.commerce.model.associacao.Associacao;
import com.ufcg.psoft.commerce.model.cliente.Cliente;
import com.ufcg.psoft.commerce.model.cliente.Endereco;
import com.ufcg.psoft.commerce.model.entregador.Entregador;
import com.ufcg.psoft.commerce.model.entregador.TipoVeiculo;
import com.ufcg.psoft.commerce.model.entregador.Veiculo;
import com.ufcg.psoft.commerce.model.estabelecimento.Estabelecimento;
import com.ufcg.psoft.commerce.model.pedido.Pedido;
import com.ufcg.psoft.commerce.model.pedido.statePedido.PedidoEmPreparo;
import com.ufcg.psoft.commerce.model.pedido.statePedido.PedidoRecebido;
import com.ufcg.psoft.commerce.repository.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
@Transactional
@DisplayName("Testes do controlador de Entregadores - Disponibilidade")
public class NotificacaoPorFaltaDeEntregadorTests {

    // URI base para alterar o status de um pedido
    final String URI_PEDIDOS = "/pedidos/{id}/entrega";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private EstabelecimentoRepository estabelecimentoRepository;

    @Autowired
    private EntregadorRepository entregadorRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AssociacaoRepository associacaoRepository;

    private Pedido pedido;

    private Cliente cliente;

    private Estabelecimento estabelecimento;

    private Entregador entregador;

    @BeforeEach
    void setUp() {
        // Cria um pedido com status inicial como RECEBIDO

        estabelecimento = estabelecimentoRepository.save(Estabelecimento.builder()
                .codigoAcessoEstabelecimento("654321")
                .build());

        Endereco end1 = Endereco.builder()
                .cidade("Campina Grande")
                .estado("Paraiba")
                .bairro("Centro")
                .numero(52)
                .logradouro("Rua De Dentro")
                .build();

        Veiculo veiculo = Veiculo.builder()
                .corVeiculo("Azul")
                .placaVeiculo("ABC-1234")
                .tipoVeiculo(TipoVeiculo.valueOf("MOTO"))
                .build();

        entregador = entregadorRepository.save(Entregador.builder()
                .nomeEntregador("Joãozinho")
                .veiculoEntregador(veiculo)
                .codigoAcessoEntregador("101010")
                .build());

        cliente = clienteRepository.save(Cliente.builder()
                .nome("Anton Ego")
                .endereco(end1)
                .codigoAcesso("123456")
                .build());

        Associacao associacao = Associacao.builder()
                .entregador(entregador)
                .estabelecimento(estabelecimento)
                .status(true)
                .build();

        associacaoRepository.save(associacao);


        Pedido pedido = Pedido.builder()
                .cliente(cliente)
                .estabelecimento(estabelecimento)
                .itens(new ArrayList<>())  // Lista de itens vazia para simplificação
                .build();

        pedido.setStatus(new PedidoRecebido());
        this.pedido = this.pedidoRepository.save(pedido);
    }

    @AfterEach
    void deleteAll() {
        // Limpa os pedidos salvos após cada teste
        this.pedidoRepository.deleteAll();
    }

    @Test
    @DisplayName("Verifica que não é possivel entregar pedidos, caso não tenha entregadores disponiveis")
    @ExtendWith(OutputCaptureExtension.class)
    void deveMudarStatusParaPronto(CapturedOutput output) throws Exception {
        // Atualiza o status do pedido para "Pedido Em Preparo" antes de mudar para "Pedido Pronto"
        entregador.setDisponibilidade(false);
        this.pedido.setStatus(new PedidoEmPreparo());
        this.pedidoRepository.save(this.pedido);  // Salva a mudança no repositório

        // Realiza a requisição PATCH para mudar o status do pedido para "Pedido Pronto"
        this.mockMvc.perform(patch("/pedidos/{id}/status-pronto", this.pedido.getId())
                        .param("estabelecimentoId", String.valueOf(estabelecimento.getId()))
                        .param("codigoAcesso", String.valueOf(estabelecimento.getCodigoAcessoEstabelecimento()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());  // Verifica se a resposta HTTP foi 200 (OK)
        assertEquals(3, this.pedido.getStatus().getOrderNumber());
        assertThat(output).contains("Entregadores: []");
        assertThat(output).contains("Você, Anton Ego, será reembolsado. Houve um problema na entrega e não poderemos efetuar teu pedido por falta de entregadores.");
    }
    @Test
    @DisplayName("Quando pedido estiver pronto, atribuir automaticamente a um entregador disponivel")
    void deveAtribuirAoEntregadorDisponivel() throws Exception {

        this.pedido.setStatus(new PedidoEmPreparo());
        this.pedidoRepository.save(this.pedido);

        this.mockMvc.perform(patch("/pedidos/{id}/status-pronto", this.pedido.getId())
                        .param("estabelecimentoId", String.valueOf(estabelecimento.getId()))
                        .param("codigoAcesso", String.valueOf(estabelecimento.getCodigoAcessoEstabelecimento()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        assertEquals(4, this.pedido.getStatus().getOrderNumber());
        assertEquals(entregador.getIdEntregador(), pedido.getEntregador().getIdEntregador());
        assertFalse(entregador.getDisponibilidade());
    }
}
