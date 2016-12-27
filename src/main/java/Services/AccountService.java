package Services;

import DAO.Abstract.IRepositoryRole;
import DAO.Abstract.IRepositoryUser;
import DAO.Concrete.RoleSql;
import DAO.Concrete.UserSql;
import DAO.Entities.Role;
import DAO.Entities.User;
import Services.Exceptions.NoSavedInDbException;
import Tools.Tool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.UUID;

/**
 * Created by raxis on 27.12.2016.
 */
public class AccountService {
    private static final Logger logger = LoggerFactory.getLogger(AccountService.class);

    public boolean userLogin(String email, String password, HttpServletRequest req){
        IRepositoryUser userHandler = new UserSql();
        IRepositoryRole roleHandler = new RoleSql();

        User user = (User) userHandler.getOne(email,Tool.computeHash(password));

        if(user!=null){
            Role role = (Role) roleHandler.getOne(user.getRoleID());
            HttpSession session = req.getSession(true);
            session.setAttribute("user",user);
            session.setAttribute("role",role);
            return true;
        }
        else {return false;}
    }

    public void userLogout(HttpServletRequest req){
        HttpSession session=req.getSession();
        session.invalidate();
    }

    public Message signIn(String email, String password, String name, String lastname,
                          String surname, String phone, String address) throws NoSavedInDbException {
        if(email.toLowerCase().matches("^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,6}$")){
            IRepositoryUser userHandler = new UserSql();
            User user = (User) userHandler.getOneByEmail(email.toLowerCase());


            if(user==null){
                user = new User();

                user.setId(UUID.randomUUID());
                user.setRoleID(UUID.fromString("81446dc5-bd04-4d41-bd72-7405effb4716"));
                user.setEmail(email);
                user.setPassword(Tool.computeHash(password));
                user.setName(name);
                user.setLastName(lastname);
                user.setSurName(surname);
                user.setPhone(phone);
                user.setAddress(address);

                if(!userHandler.create(user)){
                    throw new NoSavedInDbException();
                }
            }
            else {
                return new Message("Пользователь с таким email уже существует в базе!", Message.Status.ERROR);
            }
        }
        else {
            return  new Message("email некорректен!", Message.Status.ERROR);
        }

        return new Message("Пользователь " + email + " успешно зарегистрирован", Message.Status.OK);
    }
}
