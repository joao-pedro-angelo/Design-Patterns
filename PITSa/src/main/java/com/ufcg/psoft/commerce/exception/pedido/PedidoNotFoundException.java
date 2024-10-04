package PITSa.src.main.java.com.ufcg.psoft.commerce.exception.pedido;

public class PedidoNotFoundException extends RuntimeException {
    public PedidoNotFoundException() {
        super("O pedido consultado nao existe!");
    }
}
