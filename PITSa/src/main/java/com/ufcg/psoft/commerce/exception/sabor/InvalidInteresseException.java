package PITSa.src.main.java.com.ufcg.psoft.commerce.exception.sabor;

public class InvalidInteresseException extends RuntimeException {
  public InvalidInteresseException(){
    super("O sabor consultado ja esta disponivel!");
  }
}
