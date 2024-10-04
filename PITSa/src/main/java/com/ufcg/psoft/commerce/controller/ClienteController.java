package PITSa.src.main.java.com.ufcg.psoft.commerce.controller;

import com.ufcg.psoft.commerce.dto.cliente.ClienteInteresseRequestDTO;
import com.ufcg.psoft.commerce.dto.cliente.ClientePostPutRequestDTO;
import com.ufcg.psoft.commerce.service.cliente.IClienteInteresseService;
import com.ufcg.psoft.commerce.service.cliente.IClienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(
        value = "/cliente",
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class ClienteController {

    @Autowired
    private IClienteService IClienteService;
    @Autowired
    private IClienteInteresseService clienteInteresseService;


    @GetMapping("/{id}")
    public ResponseEntity<?> recuperarCliente(
            @PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.IClienteService
                        .recuperar(id));
    }

    @GetMapping
    public ResponseEntity<?> listarClientes() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.IClienteService
                        .listar());
    }

    @PostMapping
    public ResponseEntity<?> criarCliente(
            @RequestBody @Valid ClientePostPutRequestDTO clientePostPutRequestDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(this.IClienteService
                        .criar(clientePostPutRequestDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarCliente(
            @PathVariable Long id,
            @RequestParam String codigoAcesso,
            @RequestBody @Valid ClientePostPutRequestDTO clientePostPutRequestDto) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.IClienteService
                        .alterar(id,
                                codigoAcesso,
                                clientePostPutRequestDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluirCliente(
            @PathVariable Long id,
            @RequestParam String codigoAcesso) {
        this.IClienteService.remover(id, codigoAcesso);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @PatchMapping("/interesse")
    public ResponseEntity<?> addSaborInteresse(
            @RequestBody @Valid ClienteInteresseRequestDTO clienteInteresseRequestDTO
    ){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.clienteInteresseService
                        .addInteresse(clienteInteresseRequestDTO));
    }

}
