package DAO.Abstract;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by raxis on 27.12.2016.
 */
public interface IRepositoryUser<T,K> extends IRepository<T,K> {
    public T getOne(String email, String passwordHash);
    public T getOneByEmail(String email);
}
