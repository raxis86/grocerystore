package DAO.Abstract;

import DAO.Entities.Order;

import java.util.List;
import java.util.UUID;

/**
 * Created by raxis on 27.12.2016.
 */
/*public interface IRepositoryOrder<T,K> extends IRepository<T,K> {
    public List<T> getByUserId(K userid);
}*/

public interface IRepositoryOrder extends IRepository<Order,UUID> {
    public List<Order> getByUserId(UUID userid);
}
