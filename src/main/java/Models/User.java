package Models;

import Interfaces.IRepo;
import Interfaces.IRepoUser;
import Tools.Tool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jws.soap.SOAPBinding;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static Constants.Constants.*;

/**
 * Created by raxis on 24.12.2016.
 */
public class User implements IRepoUser<User> {
    private static final Logger logger = LoggerFactory.getLogger(User.class);

    private UUID id;
    private UUID roleID;
    private String name;
    private String email;
    private String password;
    private String lastName;
    private String surName;
    private String address;
    private String phone;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getRoleID() {
        return roleID;
    }

    public void setRoleID(UUID roleID) {
        this.roleID = roleID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    private void fillUser(User usr, ResultSet resultSet) throws SQLException {
        usr.setId(UUID.fromString(resultSet.getString("ID")));
        usr.setRoleID(UUID.fromString(resultSet.getString("ROLEID")));
        usr.setName(resultSet.getString("NAME"));
        usr.setEmail(resultSet.getString("EMAIL"));
        usr.setPassword(resultSet.getString("PASSWORD"));
        usr.setLastName(resultSet.getString("LASTNAME"));
        usr.setSurName(resultSet.getString("SURNAME"));
        usr.setAddress(resultSet.getString("ADDRESS"));
        usr.setPhone(resultSet.getString("PHONE"));
    }

    public User selectOne(String email, String passwordHash){
        User usr = null;
        try(PreparedStatement statement = Tool.getConnection().prepareStatement(USER_PREP_SELECTONE_BY_AUTH_QUERY)) {
            statement.setObject(1,email);
            statement.setObject(2,passwordHash);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                usr = new User();
                fillUser(usr,resultSet);
            }
        } catch (SQLException e) {
            logger.error("Cant selectOne User by auth info",e);
            e.printStackTrace();
        }
        return usr;
    }


    @Override
    public List<User> select() {
        List<User> userList = new ArrayList<>();
        try(Statement statement = Tool.getConnection().createStatement();) {
            ResultSet resultSet=statement.executeQuery(USER_SELECTALL_QUERY);
            while (resultSet.next()){
                User usr = new User();
                fillUser(usr,resultSet);
                userList.add(usr);
            }

        } catch (SQLException e) {
            logger.error("Select Users error!",e);
            e.printStackTrace();
        }
        return userList;
    }
    @Override
    public User selectOne(UUID id) {
        User usr = null;
        try(PreparedStatement statement = Tool.getConnection().prepareStatement(USER_PREP_SELECTONE_QUERY);) {
            statement.setObject(1,id.toString());
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                usr = new User();
                fillUser(usr,resultSet);
            }
        } catch (SQLException e) {
            logger.error("Cant selectOne User!",e);
            e.printStackTrace();
        }
        return usr;
    }

    @Override
    public void insert() {
        try(PreparedStatement statement = Tool.getConnection().prepareStatement(USER_PREP_INSERT_QUERY);) {
            statement.setObject(1,this.id.toString());
            statement.setObject(2,this.roleID.toString());
            statement.setObject(3,this.name);
            statement.setObject(4,this.email);
            statement.setObject(5,this.password);
            statement.setObject(6,this.lastName);
            statement.setObject(7,this.surName);
            statement.setObject(8,this.address);
            statement.setObject(9,this.phone);
            statement.execute();
        } catch (SQLException e) {
            logger.error("Insert user error!", e);
            e.printStackTrace();
        }
    }

    @Override
    public void delete() {
        try(PreparedStatement statement = Tool.getConnection().prepareStatement(USER_PREP_DELETE_QUERY);) {
            statement.setObject(1,this.id.toString());
            statement.execute();
        } catch (SQLException e) {
            logger.error("Cant delete User!",e);
            e.printStackTrace();
        }
    }

    @Override
    public void update() {
        try(PreparedStatement statement=Tool.getConnection().prepareStatement(USER_PREP_UPDATE_QUERY);) {
            statement.setObject(1,this.roleID.toString());
            statement.setObject(2,this.name);
            statement.setObject(3,this.email);
            statement.setObject(4,this.password);
            statement.setObject(5,this.lastName);
            statement.setObject(6,this.surName);
            statement.setObject(7,this.address);
            statement.setObject(8,this.phone);
            statement.setObject(9,this.id.toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Cant update User!",e);
            e.printStackTrace();
        }
    }
}
