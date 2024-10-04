package PITSa.src.main.java.com.ufcg.psoft.commerce.controller;

import com.ufcg.psoft.commerce.dto.estabelecimento.EstabelecimentoPostRequestDTO;
import com.ufcg.psoft.commerce.dto.estabelecimento.EstabelecimentoPutDTO;
import com.ufcg.psoft.commerce.service.estabelecimento.IEstabelecimentoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(
        value = "/estabelecimento",
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class EstabelecimentoController {

    @Autowired
    private IEstabelecimentoService estabelecimentoService;

    @PostMapping
    public ResponseEntity<?> criarEstabelecimento
            (@RequestBody @Valid EstabelecimentoPostRequestDTO dto){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(this.estabelecimentoService
                        .criar(dto));
    }

    @GetMapping
    public ResponseEntity<?> recuperarEstabelecimentos(){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.estabelecimentoService
                        .listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> recuperarEstabelecimentoPorCodigo
            (@PathVariable Long id){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.estabelecimentoService
                        .buscar(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarEstabelecimento
            (@PathVariable Long id,
             @RequestParam String codigo){
        this.estabelecimentoService
                .remover(id, codigo);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @PutMapping
    public ResponseEntity<?> atualizarEstabelecimento
            (@RequestBody @Valid EstabelecimentoPutDTO dto){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.estabelecimentoService
                        .alterar(dto));
    }
}
