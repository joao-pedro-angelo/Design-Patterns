package PITSa.src.main.java.com.ufcg.psoft.commerce.service.cliente;

@FunctionalInterface
public interface IClienteConfirmarEntrega {
    void confirmarEntrega(Long idPedido, Long clienteId, String codigoAcesso);
}
