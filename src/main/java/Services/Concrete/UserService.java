package Services.Concrete;

import Domain.Abstract.IRepositoryUser;
import Domain.Concrete.UserSql;
import Domain.Entities.User;
import Services.Abstract.IUserService;
import Services.Exceptions.NoSavedInDbException;
import Tools.Tool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

/**
 * Created by raxis on 29.12.2016.
 */
public class UserService implements IUserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private IRepositoryUser userHandler;

    public UserService(){
        this.userHandler = new UserSql();
    }

    @Override
    public User formUser(String email, String password, String name, String lastname, String surname, String address, String phone) {
        User user = new User();

        user.setId(UUID.randomUUID());
        user.setRoleID(UUID.fromString("81446dc5-bd04-4d41-bd72-7405effb4716"));
        user.setEmail(email.toLowerCase());
        user.setSalt(Tool.generateSalt());
        user.setPassword(Tool.computeHash(Tool.computeHash(password) + user.getSalt()));
        user.setName(name);
        user.setLastName(lastname);
        user.setSurName(surname);
        user.setPhone(phone);
        user.setAddress(address);

        return user;
    }

    @Override
    public void updateUser(User user, String name, String lastname, String surname, String address, String phone) throws NoSavedInDbException {
        user.setName(name);
        user.setLastName(lastname);
        user.setSurName(surname);
        user.setAddress(address);
        user.setPhone(phone);

        if(!userHandler.update(user)){
            throw new NoSavedInDbException();
        }
    }
}
