package criacionais.factoryMethod.impl.products;

import java.util.List;

public interface DAO<E> {
    public E recuperaPorID(Object id);
    public void salvar(E element);
    public void excluir(Object id);
    public List<E> listaTodos();
}
