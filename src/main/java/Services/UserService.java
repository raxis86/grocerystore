package Services;

import Domain.Abstract.IRepositoryUser;
import Domain.Concrete.UserSql;
import Domain.Entities.User;
import Services.Exceptions.NoSavedInDbException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by raxis on 27.12.2016.
 */
@Deprecated
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private IRepositoryUser userHandler;

    public UserService(){
        this.userHandler = new UserSql();
    }

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

    public User userFromSession(HttpServletRequest req){
        HttpSession session=req.getSession();
        User user = (User) session.getAttribute("user");
        return user;
    }
}
