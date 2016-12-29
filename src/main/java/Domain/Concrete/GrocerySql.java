package Domain.Concrete;

import Domain.Abstract.IRepositoryGrocery;
import Domain.Entities.Grocery;
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
 * Реализакция DAO для работы с grocery в MySQL
 */
public class GrocerySql implements IRepositoryGrocery {
    private static final Logger logger = LoggerFactory.getLogger(GrocerySql.class);

    @Override
    public List<Grocery> getAll() {
        List<Grocery> groceryList = new ArrayList<>();
        try(Statement statement = Tool.getConnection().createStatement();) {
            ResultSet resultSet=statement.executeQuery(GROCERY_SELECTALL_QUERY);
            while (resultSet.next()){
                Grocery grocery = new Grocery();
                grocery.setId(UUID.fromString(resultSet.getString("ID")));
                grocery.setParentid(UUID.fromString(resultSet.getString("PARENTID")));
                grocery.setIscategory(resultSet.getBoolean("ISCATEGORY"));
                grocery.setName(resultSet.getString("NAME"));
                grocery.setQuantity(resultSet.getInt("QUANTITY"));
                grocery.setPrice(resultSet.getBigDecimal("PRICE"));
                groceryList.add(grocery);
            }

        } catch (SQLException e) {
            logger.error("Cant select List of Grocery",e);
            e.printStackTrace();
        }
        return groceryList;
    }

    @Override
    public Grocery getOne(UUID id) {
        Grocery grocery = null;
        try(PreparedStatement statement = Tool.getConnection().prepareStatement(GROCERY_PREP_SELECTONE_QUERY)) {
            statement.setObject(1,id.toString());
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                grocery = new Grocery();
                grocery.setId(UUID.fromString(resultSet.getString("ID")));
                grocery.setParentid(UUID.fromString(resultSet.getString("PARENTID")));
                grocery.setIscategory(resultSet.getBoolean("ISCATEGORY"));
                grocery.setName(resultSet.getString("NAME"));
                grocery.setQuantity(resultSet.getInt("QUANTITY"));
                grocery.setPrice(resultSet.getBigDecimal("PRICE"));
            }
        } catch (SQLException e) {
            logger.error("Cant getOne Grocery!", e);
            e.printStackTrace();
        }
        return grocery;
    }

    @Override
    public boolean create(Grocery entity) {
        try(PreparedStatement statement = Tool.getConnection().prepareStatement(GROCERY_PREP_INSERT_QUERY);) {
            statement.setObject(1,entity.getId().toString());
            statement.setObject(2,entity.getParentid().toString());
            statement.setObject(3,entity.isIscategory());
            statement.setObject(4,entity.getName());
            statement.setObject(5,entity.getQuantity());
            statement.setObject(6,entity.getPrice());
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
        try(PreparedStatement statement = Tool.getConnection().prepareStatement(GROCERY_PREP_DELETE_QUERY);) {
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
    public boolean update(Grocery entity) {
        try(PreparedStatement statement=Tool.getConnection().prepareStatement(GROCERY_PREP_UPDATE_QUERY);) {
            statement.setObject(1,entity.getParentid().toString());
            statement.setObject(2,entity.isIscategory());
            statement.setObject(3,entity.getName());
            statement.setObject(4,entity.getQuantity());
            statement.setObject(5,entity.getPrice());
            statement.setObject(6,entity.getId().toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("cant update",e);
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
