package criacionais.factoryMethod.impl.products;

import java.util.List;

/*
    Um exemplo de entidade concreta que implementa a interface DAO
 */
public class ProductDAO implements DAO<ProductDAO> {
    @Override
    public ProductDAO recuperaPorID(Object id) {
        return null;
    }

    @Override
    public void salvar(ProductDAO element) {

    }

    @Override
    public void excluir(Object id) {

    }

    @Override
    public List<ProductDAO> listaTodos() {
        return null;
    }
}
