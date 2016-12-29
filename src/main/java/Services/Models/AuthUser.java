package Services.Models;

import Domain.Entities.Role;
import Domain.Entities.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by raxis on 29.12.2016.
 * Класс для хранения пользователя, роли
 * и сообщений об операции с ними
 */
public class AuthUser {
    private static final Logger logger = LoggerFactory.getLogger(AuthUser.class);

    private User user;
    private Role role;
    private Message message;

    public AuthUser(){

    }

    public AuthUser(User user, Role role, Message message){
        this.user=user;
        this.role=role;
        this.message=message;
    }

    public AuthUser(User user, Role role){
        this.user=user;
        this.role=role;
        message = new Message("OK", Message.Status.OK);
    }

    public AuthUser(Message message){
        this.message=message;
        this.user=null;
        this.role=null;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }
}
