package criacionais.factoryMethod.impl;

import criacionais.factoryMethod.impl.products.DAO;
import criacionais.factoryMethod.impl.products.ProductDAO;

public class ServicoConcreto extends ServicoAbstrato<ProductDAO> {

    private DAO<ProductDAO> dao;

    public ServicoConcreto(String gerador) {
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
