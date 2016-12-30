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
 * Сервис для регистрации и аутентификации пользователя
 */
public class AccountService implements IAccountService {
    private static final Logger logger = LoggerFactory.getLogger(AccountService.class);

    private IRepositoryUser userHandler;
    private IRepositoryRole roleHandler;

    public AccountService(){
        this.userHandler=new UserSql();
        this.roleHandler=new RoleSql();
    }

    /**
     * Метод для аутентификации пользователя
     * @param user
     * @return
     */
    @Override
    public AuthUser logIn(User user) {
        Role role = roleHandler.getOne(user.getRoleID());
        return new AuthUser(user,role);
    }

    /**
     * Метод для регистрации пользователя
     * @param user
     * @return
     * @throws NoSavedInDbException
     */
    @Override
    public AuthUser signIn(User user) throws NoSavedInDbException {
        if(!userHandler.create(user)){
            throw new NoSavedInDbException();
        }
        Role role = roleHandler.getOne(user.getRoleID());

        return new AuthUser(user,role);
    }

}
