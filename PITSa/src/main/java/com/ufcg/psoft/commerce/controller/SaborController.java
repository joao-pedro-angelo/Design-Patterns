package PITSa.src.main.java.com.ufcg.psoft.commerce.controller;

import com.ufcg.psoft.commerce.dto.sabor.SaborPatchStatusRequestDTO;
import com.ufcg.psoft.commerce.dto.sabor.SaborPostPutRequestDTO;
import com.ufcg.psoft.commerce.dto.sabor.SaborResponseDTO;
import com.ufcg.psoft.commerce.service.sabor.ISaborService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sabor")
public class SaborController {

    @Autowired
    private ISaborService saborService;

    @PostMapping
    public ResponseEntity<SaborResponseDTO> criarSabor
            (@RequestBody SaborPostPutRequestDTO payload,
             @RequestParam(name = "estabelecimentoId") Long estabelecimentoId,
             @RequestParam(name = "estabelecimentoCodigoAcesso") String codigoAcesso ) {
        SaborResponseDTO novoSabor = this.saborService
                .cadastrarSabor(estabelecimentoId, codigoAcesso, payload);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoSabor);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SaborResponseDTO> editarSabor
            (@PathVariable Long id,
             @RequestBody SaborPostPutRequestDTO payload,
             @RequestParam(name = "estabelecimentoId") Long estabelecimentoId,
             @RequestParam(name = "estabelecimentoCodigoAcesso") String codigoAcesso) {
        SaborResponseDTO saborAtualizado = this.saborService
                .editarSabor(id, estabelecimentoId,codigoAcesso, payload);
        return ResponseEntity.ok(saborAtualizado);
    }

    @GetMapping
    public ResponseEntity<List<SaborResponseDTO>> buscarSabores(
            @RequestParam(name = "estabelecimentoId") Long estabelecimentoId,
            @RequestParam(name = "tipo", required = false) String tipoSabor
    ) {
        return ResponseEntity.ok(this.saborService
                .buscarSabores(estabelecimentoId, tipoSabor));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SaborResponseDTO> buscarSabor(
            @PathVariable Long id,
            @RequestParam(name = "estabelecimentoId") Long estabelecimentoId,
            @RequestParam(name = "estabelecimentoCodigoAcesso") String codigoAcesso
    ) {
        return ResponseEntity.ok(this.saborService
                .buscarSabor(id, estabelecimentoId, codigoAcesso));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarSabor
            (@PathVariable Long id,
             @RequestParam(name = "estabelecimentoId") Long estabelecimentoId,
             @RequestParam(name = "estabelecimentoCodigoAcesso") String codigoAcesso) {
        this.saborService
                .removerSabor(id,estabelecimentoId, codigoAcesso);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/status")
    public ResponseEntity<SaborResponseDTO> editarStatus
            (@RequestBody SaborPatchStatusRequestDTO payload) {
        return ResponseEntity.ok(this.saborService.editarStatus(payload));
    }
}