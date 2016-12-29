package Services.Abstract;

import Domain.Entities.User;
import Services.Exceptions.NoSavedInDbException;
import Services.Models.AuthUser;


/**
 * Created by raxis on 29.12.2016.
 */
public interface IAccountService {
    public AuthUser logIn(String email, String password);
    public AuthUser signIn(User user) throws NoSavedInDbException;
}
