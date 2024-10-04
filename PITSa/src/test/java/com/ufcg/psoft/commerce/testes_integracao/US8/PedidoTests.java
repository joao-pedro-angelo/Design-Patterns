package PITSa.src.test.java.com.ufcg.psoft.commerce.testes_integracao.US8;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.ufcg.psoft.commerce.dto.pedido.PedidoPatchRequestDTO;
import com.ufcg.psoft.commerce.dto.pedido.PedidoPostPutRequestDTO;
import com.ufcg.psoft.commerce.dto.pedido.PedidoResponseDTO;
import com.ufcg.psoft.commerce.model.pedido.statePedido.PedidoPronto;
import com.ufcg.psoft.commerce.model.pedido.statePedido.PedidoRecebido;
import com.ufcg.psoft.commerce.model.pizza.TipoSabor;
import com.ufcg.psoft.commerce.model.cliente.Cliente;
import com.ufcg.psoft.commerce.model.cliente.Endereco;
import com.ufcg.psoft.commerce.model.entregador.Entregador;
import com.ufcg.psoft.commerce.model.entregador.TipoVeiculo;
import com.ufcg.psoft.commerce.model.entregador.Veiculo;
import com.ufcg.psoft.commerce.model.estabelecimento.Estabelecimento;
import com.ufcg.psoft.commerce.model.pagamento.CartaoCredito;
import com.ufcg.psoft.commerce.model.pagamento.CartaoDebito;
import com.ufcg.psoft.commerce.model.pagamento.Pix;
import com.ufcg.psoft.commerce.model.pedido.ItemVenda;
import com.ufcg.psoft.commerce.model.pedido.Pedido;
import com.ufcg.psoft.commerce.model.pizza.Pizza;
import com.ufcg.psoft.commerce.model.associacao.Associacao;
import com.ufcg.psoft.commerce.model.pizza.PizzaG;
import com.ufcg.psoft.commerce.model.pizza.PizzaM;
import com.ufcg.psoft.commerce.model.pizza.Sabor;
import com.ufcg.psoft.commerce.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Transactional
@AutoConfigureMockMvc
@DisplayName("Testes do controlador de pedidos")
public class PedidoTests {
    final String URI_PEDIDOS = "/pedidos";

    final String URI_ESTABELECIMENTO = "/estabelecimentos";

    final String URI_ENTREGADORES = "/entregadores";

    @Autowired
    MockMvc driver;

    @Autowired
    PedidoRepository pedidoRepository;

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    EstabelecimentoRepository estabelecimentoRepository;

    @Autowired
    SaborRepository saborRepository;

    @Autowired
    EntregadorRepository entregadorRepository;

    @Autowired
    AssociacaoRepository associacaoRepository;

    @Autowired
    ModelMapper modelMapper;

    ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS, true);;
    Cliente cliente;
    Cliente cliente1;
    Cliente cliente2;
    Entregador entregador;
    Entregador entregador2;
    Sabor sabor1;
    Sabor sabor2;
    Sabor sabor3;

    Pizza pizzaM;
    Pizza pizzaM1;

    Pizza pizzaG;
    Estabelecimento estabelecimento;
    Estabelecimento estabelecimento2;

    Pedido pedido;
    Pedido pedido1;
    Pedido pedido2;
    Pedido pedido3;
    Pedido pedido4;
    Pedido pedido5;
    ItemVenda itemVenda1;
    ItemVenda itemVenda2;
    PedidoPostPutRequestDTO pedidoPostPutRequestDTO;


    @BeforeEach
    void setup() {
        pedidoRepository.deleteAll();
        saborRepository.deleteAll();
        estabelecimentoRepository.deleteAll();
        clienteRepository.deleteAll();
        entregadorRepository.deleteAll();

        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        estabelecimento = estabelecimentoRepository.save(Estabelecimento.builder()
                .codigoAcessoEstabelecimento("654321")
                .build());

        estabelecimento2 = estabelecimentoRepository.save(Estabelecimento.builder()
                .codigoAcessoEstabelecimento("123456")
                .build());

        sabor1 = saborRepository.save(Sabor.builder()
                .nome("Sabor Um")
                .estabelecimento(estabelecimento)
                .tipo(TipoSabor.valueOf("SALGADO"))
                .valorMedia(10D)
                .valorGrande(20D)
                .disponivel(true)
                .build());
        sabor2 = saborRepository.save(Sabor.builder()
                .nome("Sabor Dois")
                .estabelecimento(estabelecimento)
                .tipo(TipoSabor.valueOf("DOCE"))
                .valorMedia(15D)
                .valorGrande(30D)
                .disponivel(true)
                .build());

        sabor3 = saborRepository.save(Sabor.builder()
                .nome("Sabor Três")
                .estabelecimento(estabelecimento2)
                .tipo(TipoSabor.valueOf("DOCE"))
                .valorMedia(15D)
                .valorGrande(30D)
                .disponivel(true)
                .build());

        Endereco end1 = Endereco.builder()
                .cidade("Campina Grande")
                .estado("Paraiba")
                .bairro("Centro")
                .numero(52)
                .logradouro("Rua De Dentro")
                .build();

        Endereco end2 = Endereco.builder()
                .cidade("Campina Grande")
                .estado("Paraiba")
                .bairro("Centro")
                .numero(77)
                .logradouro("Rua De Fora")
                .build();


        cliente = clienteRepository.save(Cliente.builder()
                .nome("Anton Ego")
                .endereco(end1)
                .codigoAcesso("123456")
                .build());

        cliente1 = clienteRepository.save(Cliente.builder()
                .nome("Fulano")
                .endereco(end1)
                .codigoAcesso("654321")
                .build());

        cliente2 = clienteRepository.save(Cliente.builder()
                .nome("Vitao")
                .endereco(end2)
                .codigoAcesso("777777")
                .build());


        Veiculo veiculo = Veiculo.builder()
                .corVeiculo("Azul")
                .placaVeiculo("ABC-1234")
                .tipoVeiculo(TipoVeiculo.valueOf("MOTO"))
                .build();

        Veiculo veiculo2 = Veiculo.builder()
                .corVeiculo("Laranja")
                .placaVeiculo("OCO-1234")
                .tipoVeiculo(TipoVeiculo.valueOf("CARRO"))
                .build();

        entregador = entregadorRepository.save(Entregador.builder()
                .nomeEntregador("Joãozinho")
                .veiculoEntregador(veiculo)
                .codigoAcessoEntregador("101010")
                .build());

        entregador2 = entregadorRepository.save(Entregador.builder()
                .nomeEntregador("Jailson")
                .veiculoEntregador(veiculo2)
                .codigoAcessoEntregador("202020")
                .build());

        Associacao associacao = Associacao.builder()
                .entregador(entregador)
                .estabelecimento(estabelecimento2)
                .status(true)
                .build();

        associacaoRepository.save(associacao);


        pizzaM = PizzaM.builder()
                .sabor1(sabor1)
                .build();

        pizzaM1 = PizzaM.builder()
                .sabor1(sabor3)
                .build();

        pizzaG = PizzaG.builder()
                .sabor1(sabor1)
                .sabor2(sabor2)
                .build();
        itemVenda1 = ItemVenda.builder()
                .pizza(pizzaM)
                .quantidade(1)
                .build();
        itemVenda2 = ItemVenda.builder()
                .pizza(pizzaG)
                .quantidade(2)
                .build();
        ItemVenda itemVenda3 = ItemVenda.builder()
                .pizza(pizzaM1)
                .quantidade(2)
                .build();

        List<ItemVenda> itensVenda = new ArrayList<>();
        itensVenda.add(itemVenda1);
        itensVenda.add(itemVenda2);

        List<ItemVenda> itensVenda2 = new ArrayList<>();
        itensVenda2.add(itemVenda3);

        pedido = Pedido.builder()
                .cliente(cliente)
                .estabelecimento(estabelecimento)
                .endereco(cliente.getEndereco())
                .itens(itensVenda)
                .status(new PedidoRecebido())
                .build();

        pedido1 = Pedido.builder()
                .cliente(cliente)
                .estabelecimento(estabelecimento)
                .endereco(cliente.getEndereco())
                .itens(itensVenda)
                .status(new PedidoRecebido())
                .build();

        pedido1.setStatus(new PedidoRecebido());

        pedido2 = Pedido.builder()
                .cliente(cliente1)
                .estabelecimento(estabelecimento)
                .endereco(cliente1.getEndereco())
                .entregador(entregador)
                .itens(itensVenda)
                .status(new PedidoRecebido())
                .build();

        pedido2.setStatus(new PedidoRecebido());

        pedido3 = Pedido.builder()
                .cliente(cliente1)
                .estabelecimento(estabelecimento)
                .endereco(cliente1.getEndereco())
                .entregador(entregador)
                .itens(itensVenda)
                .status(new PedidoRecebido())
                .build();

        pedido3.setStatus(new PedidoRecebido());
        pedidoPostPutRequestDTO = PedidoPostPutRequestDTO.builder()
                .endereco(pedido.getEndereco())
                .itens(pedido.getItens())
                .build();

        associacaoRepository.save(Associacao.builder()
                .entregador(entregador)
                .estabelecimento(estabelecimento)
                .status(true)
                .build());

        pedido4 = Pedido.builder()
                .cliente(cliente2)
                .estabelecimento(estabelecimento2)
                .endereco(end2)
                .status(new PedidoRecebido())
                .entregador(entregador2)
                .itens(Arrays.asList(
                        ItemVenda.builder()
                                .pizza(PizzaM.builder()
                                        .sabor1(sabor3)  // Sabor diferente do pedido1 e pedido2
                                        .build())
                                .quantidade(1)
                                .build(),
                        ItemVenda.builder()
                                .pizza(PizzaG.builder()
                                        .sabor1(sabor2)
                                        .sabor2(sabor3)
                                        .build())
                                .quantidade(1)
                                .build()
                ))
                .build();

        pedido4.setStatus(new PedidoRecebido());
        pedidoRepository.save(pedido4);


        pedido5 = Pedido.builder()
                .cliente(cliente2)
                .estabelecimento(estabelecimento2)
                .endereco(end2)
                .status(new PedidoRecebido())
                .entregador(entregador2)
                .itens(Arrays.asList(
                        ItemVenda.builder()
                                .pizza(PizzaM.builder()
                                        .sabor1(sabor3)  // Sabor diferente do pedido1 e pedido2
                                        .build())
                                .quantidade(1)
                                .build(),
                        ItemVenda.builder()
                                .pizza(PizzaG.builder()
                                        .sabor1(sabor2)
                                        .sabor2(sabor3)
                                        .build())
                                .quantidade(1)
                                .build()
                ))
                .build();

        pedido5.setStatus(new PedidoPronto());
        pedidoRepository.save(pedido5);
    }

    @Test
    void testCriarPedidoComSucesso() throws Exception {
        // Configurando o DTO para criação de pedido
        PedidoPostPutRequestDTO pedidoDTO = PedidoPostPutRequestDTO.builder()
                .endereco(cliente.getEndereco())
                .itens(pedido.getItens())
                .build();

        String jsonRequest = objectMapper.writeValueAsString(pedidoDTO);

        // Realizando a requisição POST para criar um novo pedido
        MvcResult result = driver.perform(MockMvcRequestBuilders.post(URI_PEDIDOS)
                        .param("clienteId", cliente.getId().toString())
                        .param("codigoAcesso", cliente.getCodigoAcesso())
                        .param("estabelecimentoId", estabelecimento.getId().toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isCreated())
                .andReturn();

        // Verificando se o pedido foi salvo corretamente
        String responseBody = result.getResponse().getContentAsString();
        PedidoResponseDTO response = objectMapper.readValue(responseBody, PedidoResponseDTO.class);

        // Adicione asserções conforme necessário
        assertEquals(cliente.getNome(), response.getCliente().getNome());
        assertEquals(estabelecimento.getCodigoAcessoEstabelecimento(), response.getEstabelecimento().getCodigoAcessoEstabelecimento());
    }

    @Test
    void testAtualizarPedidoComSucesso() throws Exception {
        // Atualizando o pedido
        PedidoPostPutRequestDTO pedidoDTO = PedidoPostPutRequestDTO.builder()
                .endereco(cliente1.getEndereco())
                .itens(pedido.getItens())
                .build();

        String jsonRequest = objectMapper.writeValueAsString(pedidoDTO);

        MvcResult result = driver.perform(MockMvcRequestBuilders.put(URI_PEDIDOS + "/{id}", pedido4.getId())
                        .param("clienteId", cliente2.getId().toString())
                        .param("codigoAcesso", cliente2.getCodigoAcesso())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        PedidoResponseDTO response = objectMapper.readValue(responseBody, PedidoResponseDTO.class);

        // Adicione asserções conforme necessário
        assertEquals(cliente2.getNome(), response.getCliente().getNome());
        assertEquals(estabelecimento2.getCodigoAcessoEstabelecimento(), response.getEstabelecimento().getCodigoAcessoEstabelecimento());
        assertEquals(pedido.getItens().size(), response.getItens().size());
    }

    @Test
    void testObterPedidosClienteComSucesso() throws Exception {
        MvcResult result = driver.perform(MockMvcRequestBuilders.get(URI_PEDIDOS)
                        .param("clienteId", cliente.getId().toString())
                        .param("codigoAcesso", cliente.getCodigoAcesso()))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        // Verifique se a resposta contém os pedidos esperados
        // Você pode comparar a resposta com um modelo esperado ou verificar conteúdo específico
    }

    @Test
    void testBuscarPedidoComSucesso() throws Exception {
        MvcResult result = driver.perform(MockMvcRequestBuilders.get(URI_PEDIDOS + "/{id}", pedido4.getId())
                        .param("clienteId", cliente2.getId().toString())
                        .param("codigoAcesso", cliente2.getCodigoAcesso()))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        PedidoResponseDTO response = objectMapper.readValue(responseBody, PedidoResponseDTO.class);

        // Adicione asserções conforme necessário
        assertEquals(cliente2.getNome(), response.getCliente().getNome());
    }

    @Test
    void testApagarPedidoEstabelecimentoComSucesso() throws Exception {
        driver.perform(MockMvcRequestBuilders.delete(URI_PEDIDOS + "/{id}/estabelecimento", pedido4.getId())
                        .param("estabelecimentoId", estabelecimento2.getId().toString())
                        .param("codigoAcesso", estabelecimento2.getCodigoAcessoEstabelecimento()))
                .andExpect(status().isNoContent())
                .andReturn();

        // Verifique se o pedido foi realmente removido do repositório
        // Exemplo:
        assertFalse(pedidoRepository.existsById(pedido4.getId()));
    }

    @Test
    void testAtribuirEntregadorPedidoComSucesso() throws Exception {
        driver.perform(MockMvcRequestBuilders.patch(URI_PEDIDOS + "/{pedidoId}/atribuir-entregador", pedido5.getId())
                        .param("estabelecimentoId", estabelecimento2.getId().toString())
                        .param("codigoAcesso", estabelecimento2.getCodigoAcessoEstabelecimento())
                        .param("entregadorId", entregador.getIdEntregador().toString()))
                .andExpect(status().isOk())
                .andReturn();

        // Verifique se o entregador foi realmente atribuído ao pedido
        Pedido pedidoAtualizado = pedidoRepository.findById(pedido5.getId()).orElse(null);
        assertNotNull(pedidoAtualizado);
        assertEquals(entregador.getIdEntregador(), pedidoAtualizado.getEntregador().getIdEntregador());
    }

    @Test
    void testPagamentoCredito() throws Exception {

        PedidoPatchRequestDTO dto = PedidoPatchRequestDTO.builder()
                .tipoPagamento('C')
                .build();


        String responseJsonString = driver.perform(MockMvcRequestBuilders.patch(URI_PEDIDOS)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("clienteId", cliente2.getId().toString())
                        .param("codigoAcesso", cliente2.getCodigoAcesso())
                        .param("pedidoId", pedido4.getId().toString())
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        assertInstanceOf(CartaoCredito.class, this.pedido4.getPagamento().getTipoPagamento());
        assertSame(this.pedido4.getTotal(), this.pedido4.getPagamento().getValorPagamento());
    }

    @Test
    void testPagamentoDebito() throws Exception {

        PedidoPatchRequestDTO dto = PedidoPatchRequestDTO.builder()
                .tipoPagamento('D')
                .build();

        String responseJsonString = driver.perform(MockMvcRequestBuilders.patch(URI_PEDIDOS)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("clienteId", cliente2.getId().toString())
                        .param("codigoAcesso", cliente2.getCodigoAcesso())
                        .param("pedidoId", pedido4.getId().toString())
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        assertInstanceOf(CartaoDebito.class, this.pedido4.getPagamento().getTipoPagamento());
        assertEquals(this.pedido4.getTotal() * 0.975, this.pedido4.getPagamento().getValorPagamento());
    }

    @Test
    void testPagamentoPix() throws Exception {

        PedidoPatchRequestDTO dto = PedidoPatchRequestDTO.builder()
                .tipoPagamento('P')
                .build();


        String responseJsonString = driver.perform(MockMvcRequestBuilders.patch(URI_PEDIDOS)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("clienteId", cliente2.getId().toString())
                        .param("codigoAcesso", cliente2.getCodigoAcesso())
                        .param("pedidoId", pedido4.getId().toString())
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        assertInstanceOf(Pix.class, this.pedido4.getPagamento().getTipoPagamento());
        assertEquals(this.pedido4.getTotal() * 0.95, this.pedido4.getPagamento().getValorPagamento());
    }

    @Test
    void testPagamentoInvalido() throws Exception {

        PedidoPatchRequestDTO dto = PedidoPatchRequestDTO.builder()
                .tipoPagamento('O')
                .build();


        String responseJsonString = driver.perform(MockMvcRequestBuilders.patch(URI_PEDIDOS)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("clienteId", cliente2.getId().toString())
                        .param("codigoAcesso", cliente2.getCodigoAcesso())
                        .param("pedidoId", pedido4.getId().toString())
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isInternalServerError())
                .andReturn().getResponse().getContentAsString();
    }

    @Test
    void testPagamentoClienteErrado() throws Exception {

        PedidoPatchRequestDTO dto = PedidoPatchRequestDTO.builder()
                .tipoPagamento('P')
                .build();


        String responseJsonString = driver.perform(MockMvcRequestBuilders.patch(URI_PEDIDOS)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("clienteId", cliente1.getId().toString())
                        .param("codigoAcesso", cliente1.getCodigoAcesso())
                        .param("pedidoId", pedido4.getId().toString())
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isInternalServerError())
                .andReturn().getResponse().getContentAsString();



    }


}
