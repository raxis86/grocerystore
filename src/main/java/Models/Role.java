package Models;

import Interfaces.IRepo;
import Interfaces.IRepoRole;
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
 * Created by raxis on 25.12.2016.
 */
@Deprecated
public class Role implements IRepoRole<Role> {
    private static final Logger logger = LoggerFactory.getLogger(Role.class);

    private UUID id;
    private String name;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public List<Role> select() {
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
    public Role selectOne(UUID id) {
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
    public void insert() {
        try(PreparedStatement statement = Tool.getConnection().prepareStatement(ROLE_PREP_INSERT_QUERY);) {
            statement.setObject(1,this.id.toString());
            statement.setObject(2,this.name);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete() {
        try(PreparedStatement statement = Tool.getConnection().prepareStatement(ROLE_PREP_DELETE_QUERY);) {
            statement.setObject(1,this.id.toString());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update() {
        try(PreparedStatement statement=Tool.getConnection().prepareStatement(ROLE_PREP_UPDATE_QUERY);) {
            statement.setObject(1,this.name);
            statement.setObject(2,this.id.toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
