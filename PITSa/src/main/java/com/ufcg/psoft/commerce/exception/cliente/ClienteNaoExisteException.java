package PITSa.src.main.java.com.ufcg.psoft.commerce.exception.cliente;

public class ClienteNaoExisteException extends RuntimeException {
    public ClienteNaoExisteException() {
        super("O cliente consultado nao existe!");
    }
}
