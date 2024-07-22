package services.pagamento;

import entidades.CarrinhoDeCompras;

public interface InterfacePagamento {
    //O retorno é o troco
    //Caso o dinheiro passado seja insuficiente, o retorno será -1
    public double pagar(double valor, double totalDaCompra);
}
