package mercado;

import java.util.ArrayList;
import java.util.List;

import entidades.CarrinhoDeCompras;
import entidades.Produto;

public class Mercado {
    private CarrinhoDeCompras carrinho;
    private List<CarrinhoDeCompras> historicoDeVendas;

    public Mercado(){
        this.carrinho = new CarrinhoDeCompras();
        this.historicoDeVendas = new ArrayList<>();
    }

    public void addItem(Produto p, int quantidade){
        this.carrinho.adicionaItem(p, quantidade);
    }

    public void removeItem(Produto p){
        this.carrinho.removeItem(p);
    }

    public double olhaValor(){
        return this.carrinho.total();
    }

    public double realizaPagamento(double valorCliente){
        double result = this.carrinho.pagar(valorCliente);
        if (result >= 0) this.historicoDeVendas.add(carrinho);
        this.resetaCarrinho();
        return result;
    }

    private void resetaCarrinho(){
        this.carrinho = new CarrinhoDeCompras();
    }

    public String verificaHistorico(){
        String result = "";
        for (CarrinhoDeCompras carrinhoDeCompras : this.historicoDeVendas){
            result += carrinhoDeCompras.toString();
        } 
        return result;
    }
}
