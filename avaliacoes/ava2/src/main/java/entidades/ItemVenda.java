package entidades;

public class ItemVenda {
    private Produto produto;
    private int quantidade;

    public ItemVenda(Produto produto, int quantidade) {
        this.produto = produto;
        this.quantidade = quantidade;
    }

    public double calculaSubTotal(){
        if (quantidade >= 100){
            double preco = this.produto.getPreco() * 0.90;
            return this.quantidade * preco;
        } return this.produto.getPreco() * this.quantidade;
    }

    public Produto geProduto(){
        return this.produto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    
}
