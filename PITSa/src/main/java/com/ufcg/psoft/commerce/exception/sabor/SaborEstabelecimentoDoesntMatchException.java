package PITSa.src.main.java.com.ufcg.psoft.commerce.exception.sabor;

public class SaborEstabelecimentoDoesntMatchException extends RuntimeException {
  public SaborEstabelecimentoDoesntMatchException(){
    super("Sabor não pertence a este estabelecimento!");
  }
}
