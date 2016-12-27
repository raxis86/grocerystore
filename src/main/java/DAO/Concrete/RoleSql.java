package DAO.Concrete;

import DAO.Abstract.IRepositoryRole;
import DAO.Entities.Role;
import Tools.Tool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static Constants.Constants.*;

/**
 * Created by raxis on 27.12.2016.
 */
public class RoleSql implements IRepositoryRole<Role,UUID> {
    private static final Logger logger = LoggerFactory.getLogger(RoleSql.class);

    @Override
    public List<Role> getAll() {
        List<Role> roleList = new ArrayList<>();
        try(Statement statement = Tool.getConnection().createStatement();) {
            ResultSet resultSet=statement.executeQuery(ROLE_SELECTALL_QUERY);
            while (resultSet.next()){
                Role role = new Role();
                role.setId(UUID.fromString(resultSet.getString("ID")));
                role.setName(resultSet.getString("NAME"));
                roleList.add(role);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return roleList;
    }

    @Override
    public Role getOne(UUID id) {
        Role role = null;
        try(PreparedStatement statement = Tool.getConnection().prepareStatement(ROLE_PREP_SELECTONE_QUERY)) {
            statement.setObject(1,id.toString());
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                role = new Role();
                role.setId(UUID.fromString(resultSet.getString("ID")));
                role.setName(resultSet.getString("NAME"));

            }
        } catch (SQLException e) {
            logger.error("Cant getOne Grocery!", e);
            e.printStackTrace();
        }
        return role;
    }

    @Override
    public boolean create(Role entity) {
        try(PreparedStatement statement = Tool.getConnection().prepareStatement(ROLE_PREP_INSERT_QUERY);) {
            statement.setObject(1,entity.getId().toString());
            statement.setObject(2,entity.getName());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean delete(UUID id) {
        try(PreparedStatement statement = Tool.getConnection().prepareStatement(ROLE_PREP_DELETE_QUERY);) {
            statement.setObject(1,id.toString());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean update(Role entity) {
        try(PreparedStatement statement=Tool.getConnection().prepareStatement(ROLE_PREP_UPDATE_QUERY);) {
            statement.setObject(1,entity.getName());
            statement.setObject(2,entity.getId().toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
