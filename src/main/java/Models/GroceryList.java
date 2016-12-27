package Models;

import Interfaces.IRepoGroceryList;
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
 * Created by raxis on 26.12.2016.
 */
public class GroceryList implements IRepoGroceryList<GroceryList> {
    private static final Logger logger = LoggerFactory.getLogger(GroceryList.class);

    private UUID id;
    private UUID groceryId;
    private int quantity;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getGroceryId() {
        return groceryId;
    }

    public void setGroceryId(UUID groceryId) {
        this.groceryId = groceryId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    private void fillGroceryList(GroceryList groceryList, ResultSet resultSet) throws SQLException {
        groceryList.setId(UUID.fromString(resultSet.getString("ID")));
        groceryList.setGroceryId(UUID.fromString(resultSet.getString("GROCERYID")));
        groceryList.setQuantity(resultSet.getInt("QUANTITY"));
    }

    @Override
    public List<GroceryList> select() {
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
    public GroceryList selectOne(UUID id) {
        GroceryList groceryList = null;
        try(PreparedStatement statement = Tool.getConnection().prepareStatement(GROCERYLIST_PREP_SELECTONE_QUERY)) {
            statement.setObject(1,id.toString());
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                groceryList = new GroceryList();
                fillGroceryList(groceryList,resultSet);
            }
        } catch (SQLException e) {
            logger.error("Cant selectOne Grocery!", e);
            e.printStackTrace();
        }
        return groceryList;
    }

    @Override
    public void insert() {
        try(PreparedStatement statement = Tool.getConnection().prepareStatement(GROCERYLIST_PREP_INSERT_QUERY);) {
            statement.setObject(1,this.id.toString());
            statement.setObject(2,this.groceryId.toString());
            statement.setObject(3,this.quantity);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete() {
        try(PreparedStatement statement = Tool.getConnection().prepareStatement(GROCERYLIST_PREP_DELETE_QUERY);) {
            statement.setObject(1,this.id.toString());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update() {
        try(PreparedStatement statement=Tool.getConnection().prepareStatement(GROCERYLIST_PREP_UPDATE_QUERY);) {
            statement.setObject(1,this.groceryId.toString());
            statement.setObject(2,this.quantity);
            statement.setObject(3,this.id.toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<GroceryList> select(UUID id) {
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
}
