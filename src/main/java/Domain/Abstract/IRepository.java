package Domain.Abstract;

import java.util.List;

/**
 * Created by raxis on 27.12.2016.
 * DAO
 */
public interface IRepository<T,K> {
    public List<T> getAll();
    public T getOne(K id);
    public boolean create(T entity);
    public boolean delete(K id);
    public boolean update(T entity);
}
