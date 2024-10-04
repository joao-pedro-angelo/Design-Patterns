package PITSa.src.test.java.com.ufcg.psoft.commerce.testes_integracao.US19;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufcg.psoft.commerce.dto.entregador.EntregadorPatchDTO;
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
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
@DisplayName("Testes atribuição de entrega - US19")
public class PedidoAtribuiEntregaTests {

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

    @Autowired
    JacksonTester<EntregadorPatchDTO> entregadorPatchDTOJacksonTester;

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
                .updatedAt(LocalDateTime.now())
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
        entregadorRepository.deleteAll();
        clienteRepository.deleteAll();
        pedidoRepository.deleteAll();
        associacaoRepository.deleteAll();
        estabelecimentoRepository.deleteAll();
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

    @Test
    @DisplayName("Quando pedido estiver pronto, a atribuição das entregas deve priorizar os entregadores que estejam " +
            "aguardando por mais tempo")
    void deveAtribuirAoEntregadorAguardandoPorMaisTempo() throws Exception {

        this.pedido.setStatus(new PedidoEmPreparo());
        this.pedidoRepository.save(this.pedido);

        Entregador entregador2 = entregadorRepository.save(Entregador.builder()
                .nomeEntregador("Carlinhos")
                .veiculoEntregador(new Veiculo())
                .codigoAcessoEntregador("101011")
                .disponibilidade(true)
                .updatedAt(LocalDateTime.now().minusMinutes(5))
                .build());

        Associacao associacao = Associacao.builder()
                .entregador(entregador2)
                .estabelecimento(estabelecimento)
                .status(true)
                .build();

        associacaoRepository.save(associacao);

        this.mockMvc.perform(patch("/pedidos/{id}/status-pronto", this.pedido.getId())
                        .param("estabelecimentoId", String.valueOf(estabelecimento.getId()))
                        .param("codigoAcesso", String.valueOf(estabelecimento.getCodigoAcessoEstabelecimento()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());  // Verifica se a resposta HTTP foi 200 (OK)

        assertEquals(4, this.pedido.getStatus().getOrderNumber());
        assertEquals(entregador2.getIdEntregador(), pedido.getEntregador().getIdEntregador());
        assertFalse(entregador2.getDisponibilidade());
        assertTrue(entregador.getDisponibilidade());
    }

    @Test
    @DisplayName("Quando pedido estiver pronto, mas não há entregadores disponíveis, esse pedido deve ser alocado a um" +
            "(a) entregador(a) tão logo haja algum(a) disponível")
    void alocarPeditoAutomaticamenteQuandoAlgumEntregadorFicarDisponivel() throws Exception {

        entregador.setDisponibilidade(false);
        this.pedido.setStatus(new PedidoPronto());
        pedido.getStatus().setPedido(pedido);
        this.pedidoRepository.save(this.pedido);


        EntregadorPatchDTO entregadorPatchDTO = EntregadorPatchDTO.builder()
                .disponibilidade(true).build();

        this.mockMvc.perform(patch("/entregador/disponibilidade")
                .contentType(MediaType.APPLICATION_JSON)
                .param("codigoAcessoEntregador",
                        this.entregador.getCodigoAcessoEntregador())
                .content(this.entregadorPatchDTOJacksonTester
                        .write(entregadorPatchDTO)
                        .getJson()))
                .andReturn().getResponse();

        assertEquals(4, this.pedido.getStatus().getOrderNumber());
        assertEquals(entregador.getIdEntregador(), pedido.getEntregador().getIdEntregador());
        assertFalse(entregador.getDisponibilidade());
    }


}

