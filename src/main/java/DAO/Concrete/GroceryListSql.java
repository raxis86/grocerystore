package DAO.Concrete;

import DAO.Abstract.IRepositoryGroceryList;
import DAO.Entities.GroceryList;
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
public class GroceryListSql implements IRepositoryGroceryList<GroceryList,UUID> {
    private static final Logger logger = LoggerFactory.getLogger(GroceryListSql.class);

    private void fillGroceryList(GroceryList groceryList, ResultSet resultSet) throws SQLException {
        groceryList.setId(UUID.fromString(resultSet.getString("ID")));
        groceryList.setGroceryId(UUID.fromString(resultSet.getString("GROCERYID")));
        groceryList.setQuantity(resultSet.getInt("QUANTITY"));
    }

    @Override
    public List<GroceryList> getAll() {
        List<GroceryList> groceryLists = new ArrayList<>();
        try(Statement statement = Tool.getConnection().createStatement();) {
            ResultSet resultSet=statement.executeQuery(GROCERYLIST_SELECTALL_QUERY);
            while (resultSet.next()){
                GroceryList groceryList = new GroceryList();
                fillGroceryList(groceryList,resultSet);
                groceryLists.add(groceryList);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return groceryLists;
    }

    @Override
    public GroceryList getOne(UUID id) {
        GroceryList groceryList = null;
        try(PreparedStatement statement = Tool.getConnection().prepareStatement(GROCERYLIST_PREP_SELECTONE_QUERY)) {
            statement.setObject(1,id.toString());
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                groceryList = new GroceryList();
                fillGroceryList(groceryList,resultSet);
            }
        } catch (SQLException e) {
            logger.error("Cant getOne Grocery!", e);
            e.printStackTrace();
        }
        return groceryList;
    }

    @Override
    public boolean create(GroceryList entity) {
        try(PreparedStatement statement = Tool.getConnection().prepareStatement(GROCERYLIST_PREP_INSERT_QUERY);) {
            statement.setObject(1,entity.getId().toString());
            statement.setObject(2,entity.getGroceryId().toString());
            statement.setObject(3,entity.getQuantity());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean delete(UUID id) {
        try(PreparedStatement statement = Tool.getConnection().prepareStatement(GROCERYLIST_PREP_DELETE_QUERY);) {
            statement.setObject(1,id.toString());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean update(GroceryList entity) {
        try(PreparedStatement statement=Tool.getConnection().prepareStatement(GROCERYLIST_PREP_UPDATE_QUERY);) {
            statement.setObject(1,entity.getGroceryId().toString());
            statement.setObject(2,entity.getQuantity());
            statement.setObject(3,entity.getId().toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public List<GroceryList> getListById(UUID id) {
        List<GroceryList> groceryLists = new ArrayList<>();
        try(PreparedStatement statement = Tool.getConnection().prepareStatement(GROCERYLIST_PREP_SELECTONE_QUERY);) {
            statement.setObject(1,id.toString());
            ResultSet resultSet=statement.executeQuery();
            while (resultSet.next()){
                GroceryList groceryList = new GroceryList();
                fillGroceryList(groceryList,resultSet);
                groceryLists.add(groceryList);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return groceryLists;
    }

    @Override
    public List<GroceryList> getListByGroceryId(UUID id) {
        List<GroceryList> groceryLists = new ArrayList<>();
        try(PreparedStatement statement = Tool.getConnection().prepareStatement(GROCERYLIST_PREP_SELECT_BY_GROCERYID_QUERY);) {
            statement.setObject(1,id.toString());
            ResultSet resultSet=statement.executeQuery();
            while (resultSet.next()){
                GroceryList groceryList = new GroceryList();
                fillGroceryList(groceryList,resultSet);
                groceryLists.add(groceryList);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return groceryLists;
    }
}
