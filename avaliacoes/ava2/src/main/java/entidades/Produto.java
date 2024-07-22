package entidades;

import java.util.Random;

public class Produto {
    private String descricao;
    private double preco;
    private long id;

    public Produto(String descricao, double preco){
        this.descricao = descricao;
        this.preco = preco;
        this.id = this.gerarId();
    }

    private long gerarId(){
        Random random = new Random();
        return random.nextLong();
    }

    public String getDescricao() {
        return descricao;
    }

    public double getPreco() {
        return preco;
    }

    public long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Produto [descricao=" + descricao + ", preco=" + preco + ", id=" + id + "]";
    }


    //Equals e HashCode baseados no ID
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (id ^ (id >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Produto other = (Produto) obj;
        if (id != other.id)
            return false;
        return true;
    }
    

}
