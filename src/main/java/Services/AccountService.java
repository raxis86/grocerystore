package Services;

import Domain.Abstract.IRepositoryRole;
import Domain.Abstract.IRepositoryUser;
import Domain.Concrete.RoleSql;
import Domain.Concrete.UserSql;
import Domain.Entities.Role;
import Domain.Entities.User;
import Services.Exceptions.NoSavedInDbException;
import Services.Models.Message;
import Tools.Tool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.UUID;

/**
 * Created by raxis on 27.12.2016.
 */
@Deprecated
public class AccountService {
    private static final Logger logger = LoggerFactory.getLogger(AccountService.class);

    private IRepositoryUser userHandler;
    private IRepositoryRole roleHandler;

    public AccountService(){
        this.userHandler=new UserSql();
        this.roleHandler=new RoleSql();
    }

    public Message userLogin(String email, String password, HttpServletRequest req){

        //Ищем, что существует юзер с таким email
        User userByEmail = userHandler.getOneByEmail(email.toLowerCase());

        if(userByEmail==null){
            return new Message("Пользователь с таким email не найден!", Message.Status.ERROR);
        }

        User user = userHandler.getOne(email.toLowerCase(),Tool.computeHash(Tool.computeHash(password) + userByEmail.getSalt()));

        if(user!=null){
            Role role = (Role) roleHandler.getOne(user.getRoleID());
            HttpSession session = req.getSession(true);
            session.setAttribute("user",user);
            session.setAttribute("role",role);
            return new Message("Ok", Message.Status.OK);
        }
        else {return new Message("Неверный пароль", Message.Status.ERROR);}
    }

    public void userLogout(HttpServletRequest req){
        HttpSession session=req.getSession();
        session.invalidate();
    }

    public Message signIn(String email, String password, String name, String lastname,
                          String surname, String phone, String address) throws NoSavedInDbException {
        if(email.toLowerCase().matches("^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,6}$")){
            User user = userHandler.getOneByEmail(email.toLowerCase());


            if(user==null){
                user = new User();

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
