package PITSa.src.main.java.com.ufcg.psoft.commerce.controller;

import com.ufcg.psoft.commerce.dto.pedido.PedidoPatchRequestDTO;
import com.ufcg.psoft.commerce.dto.pedido.PedidoPostPutRequestDTO;
import com.ufcg.psoft.commerce.service.pagamento.IConfirmarPagamentoDePedido;
import com.ufcg.psoft.commerce.service.pedido.IPedidoGetService;
import com.ufcg.psoft.commerce.service.pedido.IPedidoService;
import com.ufcg.psoft.commerce.service.pedido.IPedidoStatusService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(
        value = "/pedidos",
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class PedidoController {

    @Autowired
    private IPedidoService pedidoService;
    @Autowired
    private IPedidoStatusService pedidoStatusService;
    @Autowired
    private IConfirmarPagamentoDePedido confirmarPagamentoDePedido;
    @Autowired
    private IPedidoGetService pedidoGetService;

    @PostMapping
    public ResponseEntity<?> cadastrarPedido(
            @RequestParam("clienteId") Long clienteId,
            @RequestParam("codigoAcesso") @Valid String codigoAcesso,
            @RequestParam("estabelecimentoId") Long estabelecimentoId,
            @RequestBody @Valid PedidoPostPutRequestDTO pedidoPostPutDTO) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(this.pedidoService
                        .cadastraPedido
                                (clienteId,
                                        codigoAcesso,
                                        estabelecimentoId,
                                        pedidoPostPutDTO));
    }


    @PatchMapping
    public ResponseEntity<?> confirmarPagamento(
            @RequestParam("clienteId") Long clienteId,
            @RequestParam("codigoAcesso") String codigoAcesso,
            @RequestParam("pedidoId") Long pedidoID,
            @RequestBody @Valid PedidoPatchRequestDTO pedidoPatchRequestDTO
    ){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.confirmarPagamentoDePedido
                        .confirmarPagamento
                                (clienteId,
                                        codigoAcesso,
                                        pedidoID,
                                        pedidoPatchRequestDTO));
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarPedido(
            @PathVariable("id") Long pedidoId,
            @RequestParam Long clienteId,
            @RequestParam @Valid String codigoAcesso,
            @RequestBody PedidoPostPutRequestDTO pedidoPostPutRequestDTO
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.pedidoService
                        .atualizaPedido
                                (pedidoId,
                                        clienteId,
                                        codigoAcesso,
                                        pedidoPostPutRequestDTO));
    }

    @GetMapping
    public ResponseEntity<?> obterPedidosCliente(
            @RequestParam Long clienteId,
            @RequestParam String codigoAcesso
    )
    {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.pedidoGetService
                        .obterPedidosCliente(clienteId, codigoAcesso));
    }

    @DeleteMapping("/{id}/estabelecimento")
    public ResponseEntity<?> apagarPedidoEstabelecimento(
            @PathVariable Long id,
            @RequestParam("estabelecimentoId") Long estabelecimentoId,
            @RequestParam("codigoAcesso") String codigoAcesso
    ) {
        this.pedidoService
                .apagarPedidoEstabelecimento(id, estabelecimentoId, codigoAcesso);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPedido(
            @PathVariable Long id,
            @Param("clienteId") Long clienteId,
            @Param("codigoAcesso") String codigoAcesso
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.pedidoGetService
                        .obterPedidoCliente(id, clienteId, codigoAcesso));
    }

    @PatchMapping("/{pedidoId}/atribuir-entregador")
    public ResponseEntity<?> atribuirEntregadorPedido(
            @RequestParam("estabelecimentoId") Long estabelecimentoId,
            @RequestParam("codigoAcesso") String codigoAcesso,
            @PathVariable("pedidoId") Long pedidoId,
            @RequestParam("entregadorId") Long entregadorId) throws Exception {



        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.pedidoStatusService
                        .atribuirEntregador(estabelecimentoId,
                                codigoAcesso, pedidoId, entregadorId));
    }

    @PatchMapping("/{pedidoId}/entrega")
    public ResponseEntity<?> confirmarRecebimentoPedido(
            @RequestParam("clienteId") Long clienteId,
            @RequestParam("codigoAcesso") String codigoAcesso,
            @PathVariable("pedidoId") Long pedidoID
    ){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.pedidoStatusService.clienteRecebePedido(clienteId, codigoAcesso, pedidoID));
    }

    @PatchMapping("/{id}/status-pronto")
    public ResponseEntity<?> terminoPreparo(
            @PathVariable("id") Long pedidoId,
            @RequestParam("estabelecimentoId") Long estabelecimentoId,
            @RequestParam("codigoAcesso") String codigoAcesso
    ){

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(pedidoStatusService.disparaPronto(estabelecimentoId, codigoAcesso, pedidoId));
    }

    @DeleteMapping("/{id}/cliente")
    public ResponseEntity<?> cancelarPedidoCliente(
            @PathVariable Long id,
            @RequestParam("clienteId") Long clienteId,
            @RequestParam("codigoAcesso") String codigoAcesso
    ) {
        this.pedidoStatusService.cancelarPedidoCliente(id, clienteId, codigoAcesso);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @GetMapping("/status")
    public ResponseEntity<?> buscarPedidosRecebidos(
            @Param("clienteId") Long clienteId,
            @Param("codigoAcesso") String codigoAcesso,
            @Param("status") String status
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(pedidoGetService.buscarPedidosClienteFiltradosPorStatus(status, clienteId, codigoAcesso));
    }
}
