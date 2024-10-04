package PITSa.src.main.java.com.ufcg.psoft.commerce.exception.pedido;

public class PedidoEstabelecimentoDoesntMatchException extends RuntimeException {
    public PedidoEstabelecimentoDoesntMatchException() {
        super("Pedido não pertence a este estabelecimento!");
    }
}
