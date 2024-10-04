package PITSa.src.test.java.com.ufcg.psoft.commerce.testes_integracao.US12;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufcg.psoft.commerce.dto.pedido.PedidoPatchRequestDTO;
import com.ufcg.psoft.commerce.model.associacao.Associacao;
import com.ufcg.psoft.commerce.model.cliente.Cliente;
import com.ufcg.psoft.commerce.model.cliente.Endereco;
import com.ufcg.psoft.commerce.model.entregador.Entregador;
import com.ufcg.psoft.commerce.model.entregador.TipoVeiculo;
import com.ufcg.psoft.commerce.model.entregador.Veiculo;
import com.ufcg.psoft.commerce.model.estabelecimento.Estabelecimento;
import com.ufcg.psoft.commerce.model.pedido.Pedido;
import com.ufcg.psoft.commerce.model.pedido.statePedido.PedidoEmPreparo;
import com.ufcg.psoft.commerce.model.pedido.statePedido.PedidoEmRota;
import com.ufcg.psoft.commerce.model.pedido.statePedido.PedidoPronto;
import com.ufcg.psoft.commerce.model.pedido.statePedido.PedidoRecebido;
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

import java.util.ArrayList;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
@DisplayName("Testes de mudança de status no controlador de pedidos")
public class StatusPedidoTests {

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
    void setup() {
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
    @DisplayName("Deve mudar o status do pedido para PedidoEmPreparo após confirmação de pagamento")
    void deveMudarStatusParaEmPreparo() throws Exception {

        PedidoPatchRequestDTO dto = PedidoPatchRequestDTO.builder()
                .tipoPagamento('C')
                .build();

        // Realiza a requisição PATCH para mudar o status do pedido para "Pedido Em Preparo"
        this.mockMvc.perform(patch("/pedidos")
                        .param("pedidoId", String.valueOf(pedido.getId()))
                        .param("clienteId", String.valueOf(cliente.getId()))
                        .param("codigoAcesso", String.valueOf(cliente.getCodigoAcesso()))
                        .content(objectMapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());  // Verifica se a resposta HTTP foi 200 (OK)
        assertEquals(2, this.pedido.getStatus().getOrderNumber());

    }

    @Test
    @DisplayName("Deve mudar o status do pedido para PedidoPronto após o preparo")
    void deveMudarStatusParaPronto() throws Exception {
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
    }

    @Test
    @DisplayName("Deve mudar o status do pedido para PedidoEmRota após atribuir um entregador")
    void deveMudarStatusParaEmRota() throws Exception {
        // Atualiza o status do pedido para "Pedido Pronto" antes de mudar para "Pedido Em Rota"
        this.pedido.setStatus(new PedidoPronto());
        this.pedidoRepository.save(this.pedido);


        // Realiza a requisição PATCH para mudar o status do pedido para "Pedido Em Rota"
        this.mockMvc.perform(patch("/pedidos/{id}/atribuir-entregador", this.pedido.getId())
                        .param("estabelecimentoId", String.valueOf(estabelecimento.getId()))
                        .param("codigoAcesso", String.valueOf(estabelecimento.getCodigoAcessoEstabelecimento()))
                        .param("entregadorId", String.valueOf(entregador.getIdEntregador()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());  // Verifica se a resposta HTTP foi 200 (OK)

        assertEquals(4, this.pedido.getStatus().getOrderNumber());
    }

    @Test
    @DisplayName("Deve mudar o status do pedido para PedidoEntregue após a confirmação da entrega")
    void deveMudarStatusParaEntregue() throws Exception {
        // Atualiza o status do pedido para "Pedido Em Rota" antes de mudar para "Pedido Entregue"
        this.pedido.setStatus(new PedidoEmRota());
        this.pedidoRepository.save(this.pedido);  // Salva a mudança no repositório

        // Realiza a requisição PATCH para mudar o status do pedido para "Pedido Entregue"
        this.mockMvc.perform(patch("/pedidos/{pedidoId}/entrega", this.pedido.getId())
                        .param("clienteId", String.valueOf(cliente.getId()))
                        .param("codigoAcesso", String.valueOf(cliente.getCodigoAcesso()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());  // Verifica se a resposta HTTP foi 200 (OK)

        assertEquals(5, this.pedido.getStatus().getOrderNumber());
    }


}
