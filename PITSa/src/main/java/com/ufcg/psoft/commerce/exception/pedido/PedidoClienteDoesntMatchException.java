package PITSa.src.main.java.com.ufcg.psoft.commerce.exception.pedido;

public class PedidoClienteDoesntMatchException extends RuntimeException {
    public PedidoClienteDoesntMatchException() {
        super("Cliente não pertence a esse pedido!");
    }
}
