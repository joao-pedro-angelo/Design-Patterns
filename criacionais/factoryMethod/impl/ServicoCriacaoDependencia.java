package criacionais.factoryMethod.impl;

import criacionais.factoryMethod.impl.products.DAO;
import criacionais.factoryMethod.impl.products.ProductDAO;

/*
    Classe que implementa a criação do DAO relacionado à classe de serviço
    Separa a lógica de negócio da lógica de criação da dependência
 */
public class ServicoCriacaoDependencia extends ServicoAbstrato<ProductDAO> {

    private DAO<ProductDAO> dao;

    public ServicoCriacaoDependencia(String gerador) {
        super(gerador);
        this.dao = null;
    }

    @Override
    public DAO<ProductDAO> getDAO() {
        if (this.dao == null){
            this.dao = new ProductDAO();
        } return this.dao;
    }
}
