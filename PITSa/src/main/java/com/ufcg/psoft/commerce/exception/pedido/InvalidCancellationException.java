package PITSa.src.main.java.com.ufcg.psoft.commerce.exception.pedido;

public class InvalidCancellationException extends RuntimeException {
    public InvalidCancellationException() {
        super("O pedido já está pronto, não pode ser cancelado!");
    }
}
