package PITSa.src.main.java.com.ufcg.psoft.commerce.exception.pedido;

public class PedidoStateNotFound extends RuntimeException {
    public PedidoStateNotFound() {
        super("Estado de pedido invalido");
    }
}
