package Services.Concrete;

import Domain.Abstract.IRepositoryRole;
import Domain.Abstract.IRepositoryUser;
import Domain.Concrete.RoleSql;
import Domain.Concrete.UserSql;
import Domain.Entities.Role;
import Domain.Entities.User;
import Services.Abstract.IAccountService;
import Services.Exceptions.NoSavedInDbException;
import Services.Models.AuthUser;
import Services.Models.Message;
import Tools.Tool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpSession;
import java.util.UUID;

/**
 * Created by raxis on 29.12.2016.
 */
public class AccountService implements IAccountService {
    private static final Logger logger = LoggerFactory.getLogger(AccountService.class);

    private IRepositoryUser userHandler;
    private IRepositoryRole roleHandler;

    public AccountService(){
        this.userHandler=new UserSql();
        this.roleHandler=new RoleSql();
    }

    @Override
    public AuthUser logIn(String email, String password) {
        //Ищем, что существует юзер с таким email
        User userByEmail = userHandler.getOneByEmail(email.toLowerCase());

        if(userByEmail==null){
            return new AuthUser(new Message("Пользователь с таким email не найден!", Message.Status.ERROR));
        }

        User user = userHandler.getOne(email.toLowerCase(), Tool.computeHash(Tool.computeHash(password) + userByEmail.getSalt()));

        if(user!=null){
            Role role = (Role) roleHandler.getOne(user.getRoleID());
             return new AuthUser(user,role);
        }
        else {return new AuthUser(new Message("Неверный пароль", Message.Status.ERROR));}
    }

    @Override
    public AuthUser signIn(User user) throws NoSavedInDbException {
        Role role=null;
        if(user.getEmail().toLowerCase().matches("^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,6}$")){
            User userByEmail = userHandler.getOneByEmail(user.getEmail().toLowerCase());


            if(userByEmail==null){
                if(!userHandler.create(user)){
                    throw new NoSavedInDbException();
                }
                role = roleHandler.getOne(user.getRoleID());
            }
            else {
                return new AuthUser(new Message("Пользователь с таким email уже существует в базе!", Message.Status.ERROR));
            }
        }
        else {
            return new AuthUser(new Message("email некорректен!", Message.Status.ERROR));
        }

        return new AuthUser(user,role,new Message("Пользователь " + user.getEmail() + " успешно зарегистрирован", Message.Status.OK));
    }

}
