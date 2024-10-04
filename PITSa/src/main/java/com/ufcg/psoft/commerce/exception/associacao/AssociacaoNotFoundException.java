package PITSa.src.main.java.com.ufcg.psoft.commerce.exception.associacao;

public class AssociacaoNotFoundException extends RuntimeException {
    public AssociacaoNotFoundException() {
        super("Associação inválida: associação informada não existe!");
    }
}
