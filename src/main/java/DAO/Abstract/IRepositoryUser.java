package DAO.Abstract;

import DAO.Entities.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

/**
 * Created by raxis on 27.12.2016.
 */
public interface IRepositoryUser extends IRepository<User,UUID> {
    public User getOne(String email, String passwordHash);
    public User getOneByEmail(String email);
}
