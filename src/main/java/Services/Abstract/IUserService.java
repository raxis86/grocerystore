package Services.Abstract;

import Domain.Entities.User;
import Services.Exceptions.FormUserException;
import Services.Exceptions.NoSavedInDbException;

/**
 * Created by raxis on 29.12.2016.
 */
public interface IUserService {
    public User formUser(String email, String password, String name,
                         String lastname, String surname,
                         String address, String phone, String role) throws FormUserException;
    public User formUserFromRepo(String email, String password) throws FormUserException;
    public void updateUser(User user, String name, String lastname,
                           String surname, String address, String phone) throws NoSavedInDbException;
}
