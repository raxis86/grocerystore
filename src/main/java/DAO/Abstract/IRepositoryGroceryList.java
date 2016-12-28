package DAO.Abstract;

import java.util.List;

/**
 * Created by raxis on 27.12.2016.
 */
public interface IRepositoryGroceryList<T,K> extends IRepository<T,K> {
    public List<T> getListById(K id);
    public List<T> getListByGroceryId(K id);
}
