package PITSa.src.test.java.com.ufcg.psoft.commerce.testes_integracao.US14;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufcg.psoft.commerce.model.cliente.Cliente;
import com.ufcg.psoft.commerce.model.cliente.Endereco;
import com.ufcg.psoft.commerce.model.entregador.Entregador;
import com.ufcg.psoft.commerce.model.entregador.TipoVeiculo;
import com.ufcg.psoft.commerce.model.entregador.Veiculo;
import com.ufcg.psoft.commerce.model.estabelecimento.Estabelecimento;
import com.ufcg.psoft.commerce.model.pedido.Pedido;
import com.ufcg.psoft.commerce.model.pedido.statePedido.PedidoEmPreparo;
import com.ufcg.psoft.commerce.model.pedido.statePedido.PedidoPronto;
import com.ufcg.psoft.commerce.repository.ClienteRepository;
import com.ufcg.psoft.commerce.repository.EntregadorRepository;
import com.ufcg.psoft.commerce.repository.EstabelecimentoRepository;
import com.ufcg.psoft.commerce.repository.PedidoRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
@DisplayName("Testes para Cliente confirmar entrega")
public class ClienteConfirmarEntregaTests {

    // URI base para confirmar entrega
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

    // Objetos de teste
    private Cliente cliente;
    private Pedido pedido;

    @BeforeEach
    void setup() {
        // Cria um endereço para o cliente
        Endereco newEndereco = Endereco.builder()
                .numero(55)
                .cidade("Campina Grande")
                .bairro("Universitario")
                .estado("Paraiba")
                .logradouro("Rua Central")
                .build();

        // Cria e salva um cliente no repositório
        Cliente cliente = Cliente.builder()
                .nome("Nome do Cliente")
                .codigoAcesso("123436")
                .endereco(newEndereco)
                .build();
        this.cliente = this.clienteRepository.save(cliente);

        // Cria e salva um estabelecimento no repositório
        Estabelecimento estabelecimento = Estabelecimento.builder()
                .codigoAcessoEstabelecimento("123456")
                .build();
        Estabelecimento estabelecimento1 = this.estabelecimentoRepository.save(estabelecimento);

        // Cria e salva um veículo e entregador no repositório
        Veiculo veiculo = Veiculo.builder()
                .placaVeiculo("ABC1234")
                .corVeiculo("Preto")
                .tipoVeiculo(TipoVeiculo.MOTO)
                .build();
        Entregador entregador = this.entregadorRepository.save(Entregador.builder()
                .nomeEntregador("Nome do Entregador")
                .codigoAcessoEntregador("123459")
                .veiculoEntregador(veiculo)
                .build());

        // Cria um pedido com status inicial
        Pedido pedido = Pedido.builder()
                .itens(new ArrayList<>())
                .cliente(this.cliente)
                .estabelecimento(estabelecimento1)
                .entregador(entregador)
                .status(new PedidoEmPreparo())
                .build();
        this.pedido = this.pedidoRepository.save(pedido);
    }

    @AfterEach
    void deleteAll(){
        // Limpa os dados após cada teste
        this.pedidoRepository.deleteAll();
    }

    @Test
    @DisplayName("Não deve permitir que o cliente confirme a entrega" +
            " de um pedido que não está com status 'EM_ROTA'")
    void naoDeveConfirmarEntregaDePedidoQueNaoEstaEmRota() throws Exception {
        // O status do pedido permanece como "PRONTO", que não é permitido para confirmar a entrega.
        mockMvc.perform(MockMvcRequestBuilders
                        .patch(URI_PEDIDOS.replace("{id}", String.valueOf(this.pedido.getId())))
                        .param("clienteId", String.valueOf(this.cliente.getId()))
                        .param("codigoAcesso", this.cliente.getCodigoAcesso())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError());
    }

    @Test
    @DisplayName("Deve impedir que um cliente diferente do responsável" +
            " pelo pedido confirme a entrega")
    void naoDevePermitirClienteDiferenteConfirmarEntrega() throws Exception {
        // Cria um novo cliente diferente do responsável pelo pedido
        Cliente outroCliente = this.clienteRepository.save(Cliente.builder()
                .nome("Outro Cliente")
                .codigoAcesso("654321")
                .endereco(this.cliente.getEndereco())
                .build());

        // Tenta confirmar a entrega com um cliente que não é o dono do pedido
        mockMvc.perform(MockMvcRequestBuilders
                        .patch(URI_PEDIDOS.replace("{id}", String.valueOf(this.pedido.getId())))
                        .param("clienteId", String.valueOf(outroCliente.getId()))
                        .param("codigoAcesso", outroCliente.getCodigoAcesso())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError());
    }
}
