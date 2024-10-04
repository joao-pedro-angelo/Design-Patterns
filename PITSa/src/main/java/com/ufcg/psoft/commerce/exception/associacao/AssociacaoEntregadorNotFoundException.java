package PITSa.src.main.java.com.ufcg.psoft.commerce.exception.associacao;

public class AssociacaoEntregadorNotFoundException extends RuntimeException {
    public AssociacaoEntregadorNotFoundException() {
        super("Associação inválida: o entregador informado" +
                " não possui nenhuma associação aprovada!");
    }
}
