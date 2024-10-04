package PITSa.src.main.java.com.ufcg.psoft.commerce.exception.estabelecimento;

public class EstabelecimentoNotFoundException extends RuntimeException{
    public EstabelecimentoNotFoundException(){
        super
                ("Departamento não encontrado - Verifique o código de acesso.");
    }
}
