package PITSa.src.test.java.com.ufcg.psoft.commerce.testes_integracao.US16;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufcg.psoft.commerce.model.cliente.Cliente;
import com.ufcg.psoft.commerce.model.cliente.Endereco;
import com.ufcg.psoft.commerce.model.entregador.Entregador;
import com.ufcg.psoft.commerce.model.entregador.TipoVeiculo;
import com.ufcg.psoft.commerce.model.entregador.Veiculo;
import com.ufcg.psoft.commerce.model.estabelecimento.Estabelecimento;
import com.ufcg.psoft.commerce.model.pedido.Pedido;
import com.ufcg.psoft.commerce.model.pedido.statePedido.PedidoEmPreparo;
import com.ufcg.psoft.commerce.model.pedido.statePedido.PedidoEmRota;
import com.ufcg.psoft.commerce.model.pedido.statePedido.PedidoEntregue;
import com.ufcg.psoft.commerce.model.pedido.statePedido.PedidoPronto;
import com.ufcg.psoft.commerce.repository.ClienteRepository;
import com.ufcg.psoft.commerce.repository.EntregadorRepository;
import com.ufcg.psoft.commerce.repository.EstabelecimentoRepository;
import com.ufcg.psoft.commerce.repository.PedidoRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
@DisplayName("Testes para o Cliente cancelar o Pedido")
public class ClienteCancelaPedidoTests {

    // URI base para confirmar entrega
    final String URI_PEDIDOS = "/pedidos/{id}/cliente";
    
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
    @DisplayName("Não deve permitir que o cliente cancele um pedido que está em rota")
    void naoDevePermitirCancelarPedidoEmRota() throws Exception {
        // Muda o status do pedido para "Em Rota"
        this.pedido.setStatus(new PedidoEmRota());
        this.pedidoRepository.save(this.pedido);

        // Tenta cancelar o pedido enquanto ele está em rota
        mockMvc.perform(MockMvcRequestBuilders
                        .delete(URI_PEDIDOS.replace("{id}", String.valueOf(this.pedido.getId())))
                        .param("clienteId", String.valueOf(this.cliente.getId()))
                        .param("codigoAcesso", this.cliente.getCodigoAcesso())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError());
    }

    @Test
    @DisplayName("Não deve permitir que o cliente cancele um pedido que já foi entregue")
    void naoDevePermitirCancelarPedidoEntregue() throws Exception {
        // Muda o status do pedido para "Entregue"
        this.pedido.setStatus(new PedidoEntregue());
        this.pedidoRepository.save(this.pedido);

        // Tenta cancelar o pedido após ter sido entregue
        mockMvc.perform(MockMvcRequestBuilders
                        .delete(URI_PEDIDOS.replace("{id}", String.valueOf(this.pedido.getId())))
                        .param("clienteId", String.valueOf(this.cliente.getId()))
                        .param("codigoAcesso", this.cliente.getCodigoAcesso())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError());
    }

    @Test
    @DisplayName("Deve permitir que o cliente cancele um pedido que está pronto")
    void devePermitirCancelarPedidoPronto() throws Exception {
        // O pedido está em estado "Pronto"
        mockMvc.perform(MockMvcRequestBuilders
                        .delete(URI_PEDIDOS.replace("{id}", String.valueOf(this.pedido.getId())))
                        .param("clienteId", String.valueOf(this.cliente.getId()))
                        .param("codigoAcesso", this.cliente.getCodigoAcesso())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }


}
