package Domain.Concrete;

import Domain.Abstract.IRepositoryListGrocery;
import Domain.Entities.ListGrocery;
import Tools.DatabaseManager;
import Tools.Tool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.crypto.Data;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static Constants.Constants.*;

/**
 * Created by raxis on 27.12.2016.
 * Реализакция DAO для работы с grocerylist в MySQL
 */
public class ListGrocerySql implements IRepositoryListGrocery {
    private static final Logger logger = LoggerFactory.getLogger(ListGrocerySql.class);

    private void fillGroceryList(ListGrocery listGrocery, ResultSet resultSet) throws SQLException {
        listGrocery.setId(UUID.fromString(resultSet.getString("ID")));
        listGrocery.setGroceryId(UUID.fromString(resultSet.getString("GROCERYID")));
        listGrocery.setQuantity(resultSet.getInt("QUANTITY"));
    }

    @Override
    public List<ListGrocery> getAll() {
        List<ListGrocery> listGroceries = new ArrayList<>();
        try(Connection connection = DatabaseManager.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet=statement.executeQuery(GROCERYLIST_SELECTALL_QUERY);) {
            while (resultSet.next()){
                ListGrocery listGrocery = new ListGrocery();
                fillGroceryList(listGrocery,resultSet);
                listGroceries.add(listGrocery);
            }

        } catch (SQLException e) {
            logger.error("Cant getall",e);
        }
        return listGroceries;
    }

    @Override
    public ListGrocery getOne(UUID id) {
        ListGrocery listGrocery = null;
        try(Connection connection = DatabaseManager.getConnection();
            PreparedStatement statement = connection.prepareStatement(GROCERYLIST_PREP_SELECTONE_QUERY)) {
            statement.setObject(1,id.toString());
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                listGrocery = new ListGrocery();
                fillGroceryList(listGrocery,resultSet);
            }
            resultSet.close();
        } catch (SQLException e) {
            logger.error("Cant getOne ListGrocery!", e);
            e.printStackTrace();
        }
        return listGrocery;
    }

    @Override
    public boolean create(ListGrocery entity) {
        try(Connection connection = DatabaseManager.getConnection();
            PreparedStatement statement = connection.prepareStatement(GROCERYLIST_PREP_INSERT_QUERY);) {
            statement.setObject(1,entity.getId().toString());
            statement.setObject(2,entity.getGroceryId().toString());
            statement.setObject(3,entity.getQuantity());
            statement.execute();
        } catch (SQLException e) {
            logger.error("cant create",e);
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean delete(UUID id) {
        try(Connection connection = DatabaseManager.getConnection();
            PreparedStatement statement = connection.prepareStatement(GROCERYLIST_PREP_DELETE_QUERY);) {
            statement.setObject(1,id.toString());
            statement.execute();
        } catch (SQLException e) {
            logger.error("cant delete",e);
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean update(ListGrocery entity) {
        try(Connection connection = DatabaseManager.getConnection();
            PreparedStatement statement=connection.prepareStatement(GROCERYLIST_PREP_UPDATE_QUERY);) {
            statement.setObject(1,entity.getGroceryId().toString());
            statement.setObject(2,entity.getQuantity());
            statement.setObject(3,entity.getId().toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("cant update",e);
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public List<ListGrocery> getListById(UUID id) {
        List<ListGrocery> listGroceries = new ArrayList<>();
        try(Connection connection = DatabaseManager.getConnection();
            PreparedStatement statement = connection.prepareStatement(GROCERYLIST_PREP_SELECTONE_QUERY);) {
            statement.setObject(1,id.toString());
            ResultSet resultSet=statement.executeQuery();
            while (resultSet.next()){
                ListGrocery listGrocery = new ListGrocery();
                fillGroceryList(listGrocery,resultSet);
                listGroceries.add(listGrocery);
            }
            resultSet.close();
        } catch (SQLException e) {
            logger.error("cant getListById",e);
            e.printStackTrace();
        }
        return listGroceries;
    }

    @Override
    public List<ListGrocery> getListByGroceryId(UUID id) {
        List<ListGrocery> listGroceries = new ArrayList<>();
        try(Connection connection = DatabaseManager.getConnection();
            PreparedStatement statement = connection.prepareStatement(GROCERYLIST_PREP_SELECT_BY_GROCERYID_QUERY);) {
            statement.setObject(1,id.toString());
            ResultSet resultSet=statement.executeQuery();
            while (resultSet.next()){
                ListGrocery listGrocery = new ListGrocery();
                fillGroceryList(listGrocery,resultSet);
                listGroceries.add(listGrocery);
            }
            resultSet.close();
        } catch (SQLException e) {
            logger.error("cant getListByGroceryId",e);
            e.printStackTrace();
        }
        return listGroceries;
    }
}
