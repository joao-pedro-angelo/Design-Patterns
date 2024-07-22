package entidades;

import java.util.ArrayList;
import java.util.List;

import services.pagamento.InterfacePagamento;
import services.pagamento.Pagamento;

public class CarrinhoDeCompras {
    
    private List<ItemVenda> carrinhoDeCompras;
    private InterfacePagamento pagamento;

    public CarrinhoDeCompras(){
        this.carrinhoDeCompras = new ArrayList<>();
        this.pagamento = new Pagamento();
    }

    public double total(){
        double total = 0;
        for (ItemVenda item : this.carrinhoDeCompras){
            total += item.calculaSubTotal();
        } return total;
    }

    public void adicionaItem(Produto p, int quantidade){
        this.carrinhoDeCompras.add(new ItemVenda(p, quantidade));
    }

    public void removeItem(Produto p){
        this.carrinhoDeCompras.remove(p);
    }

    public double pagar(double valorDadoCliente){
        return this.pagamento.pagar(valorDadoCliente, this.total());
    }
}
