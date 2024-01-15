package criacionais.factoryMethod.impl.products;

import java.util.List;

/*
    Interface que define os métodos comuns a todas as entidades de acesso a dados do sistema
    Essa interface precisa ser implementada por cada classe que represente uma entidade de acesso a dados
    O parâmetro genérico é para determinar a entidade associada ao DAO 
 */
public interface DAO<E> {
    public E recuperaPorID(Object id);
    public void salvar(E element);
    public void excluir(Object id);
    public List<E> listaTodos();
}
