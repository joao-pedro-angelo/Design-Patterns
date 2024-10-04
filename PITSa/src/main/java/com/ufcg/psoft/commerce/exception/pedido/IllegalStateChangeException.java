package PITSa.src.main.java.com.ufcg.psoft.commerce.exception.pedido;


public class IllegalStateChangeException extends RuntimeException {
    public IllegalStateChangeException(){
        super("Mudança de estado inválida!");
    }
}