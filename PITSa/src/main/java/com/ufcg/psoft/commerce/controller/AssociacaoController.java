package PITSa.src.main.java.com.ufcg.psoft.commerce.controller;

import com.ufcg.psoft.commerce.dto.associacao.AssociacaoPostDTO;
import com.ufcg.psoft.commerce.dto.associacao.AssociacaoPutDTO;
import com.ufcg.psoft.commerce.service.associacao.IAssociacaoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(
        value = "/associacao",
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class AssociacaoController {

    @Autowired
    protected IAssociacaoService associacaoService;

    @PostMapping
    public ResponseEntity<?> cadastrarAssociacao(
            @RequestBody @Valid AssociacaoPostDTO dto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(this.associacaoService
                        .cadastrar(dto));
    }

    @PutMapping
    public ResponseEntity<?> atualizarAssociacao(
            @RequestBody @Valid AssociacaoPutDTO dto) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.associacaoService
                        .atualizarStatus(dto));
    }
}
