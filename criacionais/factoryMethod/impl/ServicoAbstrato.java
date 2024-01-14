package criacionais.factoryMethod.impl;

import criacionais.factoryMethod.impl.products.DAO;

public abstract class ServicoAbstrato<E> {

    private String gerador;

    public ServicoAbstrato(String gerador){
        this.gerador = gerador;
    }

    public abstract DAO<E> getDAO();

    public void gravarEntidadeEmArquivo(Object id){
        E entidade = this.getDAO().recuperaPorID(id);
        //logica aqui
    }
}
