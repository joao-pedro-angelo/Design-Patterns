package criacionais.factoryMethod.impl;

import criacionais.factoryMethod.impl.products.DAO;

/*
    Classe de camada de negócios
    É abstrata pois contém um método abstrato
    Para funcionar, essa classe precisa da colaboração do DAO relacionado
    Porém, foi utilizado o padrão FactoryMethod para separar a lógica de negócio da de criação
 */
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

    //Outras lógicas de negócio
}
