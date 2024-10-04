package PITSa.src.main.java.com.ufcg.psoft.commerce.exception.handler;

import com.ufcg.psoft.commerce.exception.associacao.AssociacaoEntregadorNotFoundException;
import com.ufcg.psoft.commerce.exception.associacao.AssociacaoNotFoundException;
import com.ufcg.psoft.commerce.exception.entregador.EntregadorIndisponivelException;
import com.ufcg.psoft.commerce.exception.entregador.EntregadorNotFoundException;
import com.ufcg.psoft.commerce.exception.estabelecimento.EstabelecimentoNotFoundException;
import com.ufcg.psoft.commerce.exception.gerais.CodigoDeAcessoInvalidoException;
import com.ufcg.psoft.commerce.exception.cliente.ClienteNaoExisteException;
import com.ufcg.psoft.commerce.exception.sabor.InvalidInteresseException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EstabelecimentoNotFoundException.class)
    @ResponseBody
    public ResponseEntity<String> handleEstabelecimentoNotFoundException
            (EstabelecimentoNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CodigoDeAcessoInvalidoException.class)
    @ResponseBody
    public ResponseEntity<String> handleCodigoDeAcessoInvalidoException
            (CodigoDeAcessoInvalidoException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ClienteNaoExisteException.class)
    @ResponseBody
    public ResponseEntity<String> handleClienteNaoExisteException
            (ClienteNaoExisteException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EntregadorNotFoundException.class)
    @ResponseBody
    public ResponseEntity<String> handleEntregadorNaoExisteException
            (EntregadorNotFoundException ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AssociacaoNotFoundException.class)
    @ResponseBody
    public ResponseEntity<String> handleAssociacaoNaoExisteException
            (AssociacaoNotFoundException ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AssociacaoEntregadorNotFoundException.class)
    @ResponseBody
    public ResponseEntity<String> handleAssociacaoEntregadorNaoExisteException(
            AssociacaoEntregadorNotFoundException ex
    ){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<String> handleGenericException
            (Exception ex) {
        return new ResponseEntity<>
                (ex.getMessage() + ex.getCause() + ex.getLocalizedMessage(),
                        HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> handleDataIntegrityViolationException
            (DataIntegrityViolationException ex) {
        // Você pode adicionar uma mensagem mais detalhada aqui se necessário
        return new ResponseEntity<>(
                "Erro de integridade de dados: " + ex.getMessage(),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidInteresseException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleInvalidInteresseException(InvalidInteresseException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> handleEntityNotFoundException(EntityNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Entidade não encontrada: " + ex.getMessage());
    }

    @ExceptionHandler(IllegalStateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleIllegalState(){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Status ilegal.");
    }

    @ExceptionHandler(EntregadorIndisponivelException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleIndisponivelEntregador(EntregadorIndisponivelException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Entregador indisponível. "
        + ex.getMessage());
    }
}
