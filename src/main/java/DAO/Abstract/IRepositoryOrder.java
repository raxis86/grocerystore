package DAO.Abstract;

import java.util.List;
import java.util.UUID;

/**
 * Created by raxis on 27.12.2016.
 */
public interface IRepositoryOrder<T,K> extends IRepository<T,K> {
    public List<T> getByUserId(K userid);
}
