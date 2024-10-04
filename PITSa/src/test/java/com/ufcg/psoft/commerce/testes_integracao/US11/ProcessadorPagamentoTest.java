package PITSa.src.test.java.com.ufcg.psoft.commerce.testes_integracao.US11;

import com.ufcg.psoft.commerce.model.pagamento.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
@DisplayName("Testes da implementação dos diferentes tipos de pagamentos")
public class ProcessadorPagamentoTest {

    // Instâncias de pagamento para os diferentes tipos de pagamento
    Pagamento cartaoCredito;
    Pagamento cartaoDebito;
    Pagamento pix;

    @BeforeEach
    public void setUp() {
        // Inicializa os pagamentos para cada tipo de pagamento (Cartão de Crédito, Cartão de Débito, Pix)

        // Pagamento via Cartão de Crédito (sem desconto)
        this.cartaoCredito = Pagamento.builder()
                .pago(false)
                .tipoPagamento(new CartaoCredito())
                .build();
        this.cartaoCredito.setValorPagamento(500.0);

        // Pagamento via Cartão de Débito (com 2.5% de desconto)
        this.cartaoDebito = Pagamento.builder()
                .pago(false)
                .tipoPagamento(new CartaoDebito())
                .build();
        this.cartaoDebito.setValorPagamento(500.0);

        // Pagamento via Pix (com 5% de desconto)
        this.pix = Pagamento.builder()
                .pago(false)
                .tipoPagamento(new Pix())
                .build();
        this.pix.setValorPagamento(500.0);
    }

    @Test
    @DisplayName("Processar pagamento via Pix com desconto de 5%")
    public void testProcessarPagamentoPix() {
        // Configura o tipo de pagamento como Pix
        TipoPagamento tipoPagamento = new Pix();
        Double valorEsperado = 475.0; // 5% de desconto

        // Obtém o valor processado e verifica se corresponde ao esperado
        Double resultado = pix.getValorPagamento();
        assertEquals(valorEsperado, resultado);
    }

    @Test
    @DisplayName("Processar pagamento via Cartão de Débito com desconto de 2.5%")
    public void testProcessarPagamentoCartaoDebito() {
        // Configura o tipo de pagamento como Cartão de Débito
        TipoPagamento tipoPagamento = new CartaoDebito();
        Double valorEsperado = 487.5; // 2.5% de desconto

        // Obtém o valor processado e verifica se corresponde ao esperado
        Double resultado = cartaoDebito.getValorPagamento();
        assertEquals(valorEsperado, resultado);
    }

    @Test
    @DisplayName("Processar pagamento via Cartão de Crédito sem desconto")
    public void testProcessarPagamentoCartaoCredito() {
        // Configura o tipo de pagamento como Cartão de Crédito
        TipoPagamento tipoPagamento = new CartaoCredito();
        Double valorEsperado = 500.0; // Sem desconto

        // Obtém o valor processado e verifica se corresponde ao esperado
        Double resultado = cartaoCredito.getValorPagamento();
        assertEquals(valorEsperado, resultado);
    }
}
