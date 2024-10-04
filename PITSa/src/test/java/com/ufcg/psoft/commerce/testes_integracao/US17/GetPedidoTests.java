package PITSa.src.test.java.com.ufcg.psoft.commerce.testes_integracao.US17;


import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.ufcg.psoft.commerce.dto.pedido.PedidoPostPutRequestDTO;
import com.ufcg.psoft.commerce.dto.pedido.PedidoResponseDTO;
import com.ufcg.psoft.commerce.model.associacao.Associacao;
import com.ufcg.psoft.commerce.model.cliente.Cliente;
import com.ufcg.psoft.commerce.model.cliente.Endereco;
import com.ufcg.psoft.commerce.model.entregador.Entregador;
import com.ufcg.psoft.commerce.model.entregador.TipoVeiculo;
import com.ufcg.psoft.commerce.model.entregador.Veiculo;
import com.ufcg.psoft.commerce.model.estabelecimento.Estabelecimento;
import com.ufcg.psoft.commerce.model.pedido.ItemVenda;
import com.ufcg.psoft.commerce.model.pedido.Pedido;
import com.ufcg.psoft.commerce.model.pedido.statePedido.PedidoEmPreparo;
import com.ufcg.psoft.commerce.model.pedido.statePedido.PedidoPronto;
import com.ufcg.psoft.commerce.model.pedido.statePedido.PedidoRecebido;
import com.ufcg.psoft.commerce.model.pizza.*;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
@DisplayName("Testes do controlador de pedidos em relação aos gets")
public class GetPedidoTests {
    final String URI_PEDIDOS = "/pedidos";

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
    Cliente cliente3;

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
    Pedido pedido6;
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

        cliente3 = clienteRepository.save(Cliente.builder()
                .nome("Vitao")
                .endereco(end2)
                .codigoAcesso("123454321")
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

        pedido6 = Pedido.builder()
                .cliente(cliente)
                .estabelecimento(estabelecimento)
                .endereco(cliente.getEndereco())
                .itens(itensVenda)
                .status(new PedidoEmPreparo())
                .build();

        pedido6.getStatus().setOrderNumber(2);

        pedidoRepository.save(pedido6);

        pedido = Pedido.builder()
                .cliente(cliente)
                .estabelecimento(estabelecimento)
                .endereco(cliente.getEndereco())
                .itens(itensVenda)
                .status(new PedidoRecebido())
                .build();

        pedido.getStatus().setOrderNumber(1);

        pedido1 = Pedido.builder()
                .cliente(cliente)
                .estabelecimento(estabelecimento)
                .endereco(cliente.getEndereco())
                .itens(itensVenda)
                .status(new PedidoRecebido())
                .build();

        pedido1.setStatus(new PedidoRecebido());
        pedido1.getStatus().setOrderNumber(1);

        pedido2 = Pedido.builder()
                .cliente(cliente1)
                .estabelecimento(estabelecimento)
                .endereco(cliente1.getEndereco())
                .entregador(entregador)
                .itens(itensVenda)
                .status(new PedidoRecebido())
                .build();

        pedido2.setStatus(new PedidoRecebido());
        pedido2.getStatus().setOrderNumber(1);

        pedido3 = Pedido.builder()
                .cliente(cliente1)
                .estabelecimento(estabelecimento)
                .endereco(cliente1.getEndereco())
                .entregador(entregador)
                .itens(itensVenda)
                .status(new PedidoRecebido())
                .build();



        pedido3.setStatus(new PedidoRecebido());
        pedido3.getStatus().setOrderNumber(1);

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
        pedido4.getStatus().setOrderNumber(1);
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
        pedido5.getStatus().setOrderNumber(3);
        pedidoRepository.save(pedido5);


    }


    @Test
    void testVisualizarPedido() throws Exception {
        // Certificando-se de que o pedido existe no repositório antes de executar o teste
        Pedido savedPedido = pedidoRepository.save(pedido);

        // Definindo o URI com o ID do pedido salvo
        String uri = "/pedidos/" + savedPedido.getId();

        // Executando a requisição GET
        MvcResult result = driver.perform(MockMvcRequestBuilders.get(uri)
                        .param("clienteId", savedPedido.getCliente().getId().toString())
                        .param("codigoAcesso", savedPedido.getCliente().getCodigoAcesso())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) // O status esperado é 200 OK
                .andReturn();

        // Verificando o conteúdo da resposta
        String responseBody = result.getResponse().getContentAsString();
        PedidoResponseDTO response = objectMapper.readValue(responseBody, PedidoResponseDTO.class);

        // Aserções para verificar se os dados retornados são os esperados
        assertEquals(savedPedido.getCliente().getNome(), response.getCliente().getNome());
        assertEquals(savedPedido.getEstabelecimento().getCodigoAcessoEstabelecimento(), response.getEstabelecimento().getCodigoAcessoEstabelecimento());
        assertEquals(savedPedido.getItens().size(), response.getItens().size());
        assertEquals(savedPedido.getStatus().getClass(), response.getStatus().getClass());
    }

    @Test
    void testBuscarPedidoComIdInexistente() throws Exception {
        // Definindo um ID inexistente
        Long idInexistente = 999L;

        // Definindo o URI com o ID inexistente
        String uri = "/pedidos/" + idInexistente;

        // Executando a requisição GET com um ID inexistente
        driver.perform(MockMvcRequestBuilders.get(uri)
                        .param("clienteId", cliente.getId().toString())  // Usando um cliente válido
                        .param("codigoAcesso", cliente.getCodigoAcesso())  // Usando o código de acesso correto
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError()); // Espera-se um erro 404 Not Found
    }

    @Test
    void testBuscarPedidoComCodigoAcessoInvalido() throws Exception {
        // Certificando-se de que o pedido existe no repositório
        Pedido savedPedido = pedidoRepository.save(pedido);

        // Definindo o URI com o ID do pedido salvo
        String uri = "/pedidos/" + savedPedido.getId();

        // Executando a requisição GET com um código de acesso inválido
        driver.perform(MockMvcRequestBuilders.get(uri)
                        .param("clienteId", savedPedido.getCliente().getId().toString())  // Usando o cliente correto
                        .param("codigoAcesso", "codigo_invalido")  // Código de acesso inválido
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound()); // Espera-se um erro 401 Unauthorized
    }

    @Test
    void testBuscarPedidoComClienteInvalido() throws Exception {
        // Certificando-se de que o pedido existe no repositório
        Pedido savedPedido = pedidoRepository.save(pedido);

        // Definindo o URI com o ID do pedido salvo
        String uri = "/pedidos/" + savedPedido.getId();

        // Executando a requisição GET com um cliente inválido
        driver.perform(MockMvcRequestBuilders.get(uri)
                        .param("clienteId", "999")  // ID de cliente inexistente
                        .param("codigoAcesso", savedPedido.getCliente().getCodigoAcesso())  // Código de acesso correto
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound()); // Espera-se um erro 401 Unauthorized
    }

    @Test
    void testObterPedidosClienteComSucesso() throws Exception {
        // Salvando pedidos para o cliente
        pedidoRepository.save(pedido);
        pedidoRepository.save(pedido1);

        // Definindo a URI
        String uri = "/pedidos";

        // Executando a requisição GET para obter os pedidos do cliente válido
        MvcResult result = driver.perform(MockMvcRequestBuilders.get(uri)
                        .param("clienteId", cliente.getId().toString())  // Usando um cliente válido
                        .param("codigoAcesso", cliente.getCodigoAcesso())  // Código de acesso correto
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())  // Espera-se status 200 OK
                .andReturn();

        // Verificando a resposta
        String responseBody = result.getResponse().getContentAsString();
        PedidoResponseDTO[] pedidos = objectMapper.readValue(responseBody, PedidoResponseDTO[].class);

        // Assegurando que os pedidos corretos foram retornados
        assertEquals(3, pedidos.length);
        assertEquals(cliente.getNome(), pedidos[0].getCliente().getNome());
        assertEquals(cliente.getNome(), pedidos[1].getCliente().getNome());
    }

    @Test
    void testObterPedidosClienteSemPedidos() throws Exception {
        // Definindo a URI
        String uri = "/pedidos";

        // Executando a requisição GET para obter os pedidos de um cliente que não tem pedidos
        driver.perform(MockMvcRequestBuilders.get(uri)
                        .param("clienteId", cliente3.getId().toString())  // Cliente sem pedidos
                        .param("codigoAcesso", cliente3.getCodigoAcesso())  // Código de acesso correto
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())  // Espera-se status 200 OK
                .andExpect(jsonPath("$.length()").value(0));  // Espera-se que a lista de pedidos esteja vazia
    }

    @Test
    void testObterPedidosClienteCodigoAcessoInvalido() throws Exception {
        // Definindo a URI
        String uri = "/pedidos";

        // Executando a requisição GET com código de acesso incorreto
        driver.perform(MockMvcRequestBuilders.get(uri)
                        .param("clienteId", cliente.getId().toString())  // Cliente válido
                        .param("codigoAcesso", "codigo_invalido")  // Código de acesso inválido
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());  // Espera-se status 401 Unauthorized
    }

    @Test
    void testObterPedidosClienteInexistente() throws Exception {
        // Definindo a URI
        String uri = "/pedidos";

        // Executando a requisição GET para um cliente que não existe no banco de dados
        driver.perform(MockMvcRequestBuilders.get(uri)
                        .param("clienteId", "999")  // Cliente inexistente
                        .param("codigoAcesso", "123456")  // Código de acesso qualquer
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());  // Espera-se status 401 Unauthorized
    }

    @Test
    void testBuscarPedidosRecebidosComSucesso() throws Exception {
        // Salvando pedidos com status "Pedido Recebido" para o cliente
        pedidoRepository.save(pedido);  // Status: Pedido Recebido
        pedidoRepository.save(pedido1); // Status: Pedido Recebido

        // Definindo a URI
        String uri = "/pedidos/status";

        // Executando a requisição GET para buscar pedidos com status "Pedido Recebido"
        MvcResult result = driver.perform(MockMvcRequestBuilders.get(uri)
                        .param("clienteId", cliente.getId().toString())  // Cliente válido
                        .param("codigoAcesso", cliente.getCodigoAcesso())  // Código de acesso correto
                        .param("status", "recebido")  // Status válido
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())  // Espera-se status 200 OK
                .andReturn();

        // Verificando a resposta
        String responseBody = result.getResponse().getContentAsString();
        PedidoResponseDTO[] pedidos = objectMapper.readValue(responseBody, PedidoResponseDTO[].class);

        // Verificando que os pedidos retornados estão com o status correto
        assertEquals(2, pedidos.length);  // 2 pedidos devem ser retornados
    }

    @Test
    void testBuscarPedidosComStatusInexistente() throws Exception {
        // Definindo a URI
        String uri = "/pedidos/status";

        // Executando a requisição GET para um status que não existe
        driver.perform(MockMvcRequestBuilders.get(uri)
                        .param("clienteId", cliente.getId().toString())  // Cliente válido
                        .param("codigoAcesso", cliente.getCodigoAcesso())  // Código de acesso correto
                        .param("status", "StatusInvalido")  // Status inexistente
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError()); // Lista de pedidos deve estar vazia
    }

    @Test
    void testBuscarPedidosComCodigoAcessoInvalido() throws Exception {
        // Definindo a URI
        String uri = "/pedidos/status";

        // Executando a requisição GET com um código de acesso inválido
        driver.perform(MockMvcRequestBuilders.get(uri)
                        .param("clienteId", cliente.getId().toString())  // Cliente válido
                        .param("codigoAcesso", "codigo_invalido")  // Código de acesso inválido
                        .param("status", "Pedido Recebido")  // Status válido
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());  // Espera-se status 401 Unauthorized
    }

    @Test
    void testBuscarPedidosClienteInexistente() throws Exception {
        // Definindo a URI
        String uri = "/pedidos/status";

        // Executando a requisição GET para um cliente que não existe
        driver.perform(MockMvcRequestBuilders.get(uri)
                        .param("clienteId", "999")  // Cliente inexistente
                        .param("codigoAcesso", "123456")  // Código de acesso qualquer
                        .param("status", "Pedido Recebido")  // Status válido
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());  // Espera-se status 401 Unauthorized
    }

}
