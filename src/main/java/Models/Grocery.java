package Models;

import Interfaces.IRepo;
import Tools.Tool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import static Constants.Constants.*;

/**
 * Created by raxis on 23.12.2016.
 */
public class Grocery implements IRepo<Grocery> {
    private static final Logger logger = LoggerFactory.getLogger(Grocery.class);

    private UUID id;
    private UUID parentid;
    private boolean iscategory;
    private String name;
    private int quantity;
    private BigDecimal price;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getParentid() {
        return parentid;
    }

    public void setParentid(UUID parentid) {
        this.parentid = parentid;
    }

    public boolean isIscategory() {
        return iscategory;
    }

    public void setIscategory(boolean iscategory) {
        this.iscategory = iscategory;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public List<Grocery> select() {
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
            e.printStackTrace();
        }
        return groceryList;
    }

    @Override
    public Grocery select(UUID id) {
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
            logger.error("Cant select Grocery!", e);
            e.printStackTrace();
        }
        return grocery;
    }

    public void insert() {
        try(PreparedStatement statement = Tool.getConnection().prepareStatement(GROCERY_PREP_INSERT_QUERY);) {
            statement.setObject(1,this.id.toString());
            statement.setObject(2,this.parentid.toString());
            statement.setObject(3,this.iscategory);
            statement.setObject(4,this.name);
            statement.setObject(5,this.quantity);
            statement.setObject(6,this.price);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete() {
        try(PreparedStatement statement = Tool.getConnection().prepareStatement(GROCERY_PREP_DELETE_QUERY);) {
            statement.setObject(1,this.id.toString());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        try(PreparedStatement statement=Tool.getConnection().prepareStatement(GROCERY_PREP_UPDATE_QUERY);) {
            statement.setObject(1,this.parentid.toString());
            statement.setObject(2,this.iscategory);
            statement.setObject(3,this.name);
            statement.setObject(4,this.quantity);
            statement.setObject(5,this.price);
            statement.setObject(6,this.id.toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result=prime*result+(int)id.getLeastSignificantBits();
        result=prime*result+(int)parentid.getMostSignificantBits();
        int i=iscategory==true ? 1 : 0;
        result=prime*result+i;
        result=prime*result+name.hashCode();
        result=prime*result+quantity;
        result=prime*result+price.toBigInteger().intValue();
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Grocery other = (Grocery) obj;
        if (!id.equals(other.id))
            return false;
        if (!parentid.equals(other.parentid))
            return false;
        if (iscategory != other.iscategory)
            return false;
        if (!name.equals(other.name))
            return false;
        if (quantity != other.quantity)
            return false;
        if (!price.equals(other.price))
            return false;
        return true;
    }
}
