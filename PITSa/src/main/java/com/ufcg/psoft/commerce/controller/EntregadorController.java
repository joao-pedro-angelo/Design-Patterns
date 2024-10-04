package PITSa.src.main.java.com.ufcg.psoft.commerce.controller;

import com.ufcg.psoft.commerce.dto.entregador.EntregadorPatchDTO;
import com.ufcg.psoft.commerce.dto.entregador.EntregadorPostRequestDTO;
import com.ufcg.psoft.commerce.dto.entregador.EntregadorPutDTO;
import com.ufcg.psoft.commerce.dto.entregador.EntregadorResponseDTO;
import com.ufcg.psoft.commerce.service.entregador.IEntregadorDisponibilidade;
import com.ufcg.psoft.commerce.service.entregador.IEntregadorService;
import com.ufcg.psoft.commerce.service.pedido.IPedidoStatusService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;

import java.util.List;


@RestController
@RequestMapping(
        value = "/entregador",
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class EntregadorController {

    @Autowired
    private IEntregadorService entregadorService;
    @Autowired
    private IEntregadorDisponibilidade entregadorDisponibilidade;

    @Autowired
    IPedidoStatusService pedidoStatusService;

    @PostMapping
    public ResponseEntity<EntregadorResponseDTO> cadastrarEntregador(
            @RequestBody @Valid EntregadorPostRequestDTO entregadorPostRequestDTO)
            throws Exception {
        EntregadorResponseDTO entregadorResponseDTO =
                this.entregadorService
                        .cadastrarEntregador(entregadorPostRequestDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(entregadorResponseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntregadorResponseDTO> getEntregadorById
            (@PathVariable Long id)
            throws Exception{
        EntregadorResponseDTO entregadorResponseDTO =
                this.entregadorService
                    .getEntregadorById(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(entregadorResponseDTO);
    }

    @GetMapping
    public ResponseEntity<List<EntregadorResponseDTO>> listaEntregadores()
            throws Exception{
        List<EntregadorResponseDTO> entregadores =
                this.entregadorService
                    .listaEntregadores();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(entregadores);
    }

    @PutMapping
    public ResponseEntity<EntregadorResponseDTO> atualizaEntregador(
            @RequestBody @Valid EntregadorPutDTO entregadorPutDTO)
            throws Exception{
        EntregadorResponseDTO dto =
                this.entregadorService
                    .atualizaEntregador(entregadorPutDTO);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeEntregador
            (@PathVariable Long id,
            @RequestParam String codigoAcessoEntregador)
            throws Exception{
        this.entregadorService
                .removeEntregador(id, codigoAcessoEntregador);
        return ResponseEntity
                .noContent()
                .build();
    }

    @PatchMapping("/disponibilidade")
    public ResponseEntity<EntregadorResponseDTO> atualizaDisponibilidade(
            @RequestBody @Valid EntregadorPatchDTO entregadorPatchDTO,
            @Param("codigoAcessoEntregador") String codigoAcessoEntregador
    ) throws Exception {
        EntregadorResponseDTO response = this.entregadorDisponibilidade
                .setDisponivel(codigoAcessoEntregador,
                        entregadorPatchDTO.getDisponibilidade());

        if (entregadorPatchDTO.getDisponibilidade()) {
            pedidoStatusService.atribuiPedido(response.getIdEntregador());
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }
}
