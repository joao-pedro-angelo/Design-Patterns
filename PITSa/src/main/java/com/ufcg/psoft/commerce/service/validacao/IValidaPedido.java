package PITSa.src.main.java.com.ufcg.psoft.commerce.service.validacao;

@FunctionalInterface
public interface IValidaPedido {
    void validaPedido(Long pedidoId, Long clienteId, String codigoAcesso);
}
