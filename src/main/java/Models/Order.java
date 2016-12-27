package Models;

import Interfaces.IRepoOrder;
import Tools.Tool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static Constants.Constants.*;

/**
 * Created by raxis on 26.12.2016.
 */
public class Order implements IRepoOrder<Order> {
    private static final Logger logger = LoggerFactory.getLogger(Order.class);

    private UUID id;
    private UUID userid;
    private UUID orderstatusid;
    private UUID grocerylistid;
    private BigDecimal price;
    private Date datetime;
    private String address;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getUserid() {
        return userid;
    }

    public void setUserid(UUID userid) {
        this.userid = userid;
    }

    public UUID getOrderstatusid() {
        return orderstatusid;
    }

    public void setOrderstatusid(UUID orderstatusid) {
        this.orderstatusid = orderstatusid;
    }

    public UUID getGrocerylistid() {
        return grocerylistid;
    }

    public void setGrocerylistid(UUID grocerylistid) {
        this.grocerylistid = grocerylistid;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    private void fillOrder(Order order, ResultSet resultSet) throws SQLException {
        order.setId(UUID.fromString(resultSet.getString("ID")));
        order.setUserid(UUID.fromString(resultSet.getString("USERID")));
        order.setOrderstatusid(UUID.fromString(resultSet.getString("STATUSID")));
        order.setGrocerylistid(UUID.fromString(resultSet.getString("GROCERYLISTID")));
        order.setPrice(resultSet.getBigDecimal("PRICE"));
        order.setDatetime(resultSet.getDate("DATETIME"));
        order.setAddress(resultSet.getString("ADDRESS"));
    }

    @Override
    public List<Order> select() {
        List<Order> orderList = new ArrayList<>();
        try(Statement statement = Tool.getConnection().createStatement();) {
            ResultSet resultSet=statement.executeQuery(ORDER_SELECTALL_QUERY);
            while (resultSet.next()){
                Order order = new Order();
                fillOrder(order,resultSet);
                orderList.add(order);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderList;
    }

    @Override
    public Order selectOne(UUID id) {
        Order order = null;
        try(PreparedStatement statement = Tool.getConnection().prepareStatement(ORDER_PREP_SELECTONE_QUERY)) {
            statement.setObject(1,id.toString());
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                order = new Order();
                fillOrder(order,resultSet);
            }
        } catch (SQLException e) {
            logger.error("Cant selectOne Order!", e);
            e.printStackTrace();
        }
        return order;
    }

    @Override
    public void insert() {
        try(PreparedStatement statement = Tool.getConnection().prepareStatement(ORDER_PREP_INSERT_QUERY);) {
            statement.setObject(1,this.id.toString());
            statement.setObject(2,this.userid.toString());
            statement.setObject(3,this.orderstatusid.toString());
            statement.setObject(4,this.price);
            statement.setObject(5,this.datetime);
            statement.setObject(6,this.grocerylistid.toString());
            statement.setObject(7,this.address);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete() {
        try(PreparedStatement statement = Tool.getConnection().prepareStatement(ORDER_PREP_DELETE_QUERY);) {
            statement.setObject(1,this.id.toString());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update() {
        try(PreparedStatement statement=Tool.getConnection().prepareStatement(ORDER_PREP_UPDATE_QUERY);) {
            statement.setObject(1,this.userid.toString());
            statement.setObject(2,this.orderstatusid.toString());
            statement.setObject(3,this.price);
            statement.setObject(4,this.datetime);
            statement.setObject(5,this.grocerylistid.toString());
            statement.setObject(6,this.address);
            statement.setObject(7,this.id.toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Order> selectByUserId(UUID userid) {
        List<Order> orderList = new ArrayList<>();
        try(PreparedStatement statement = Tool.getConnection().prepareStatement(ORDER_PREP_SELECT_BY_USERID_QUERY);) {
            statement.setObject(1,userid.toString());
            ResultSet resultSet=statement.executeQuery();
            while (resultSet.next()){
                Order order = new Order();
                fillOrder(order,resultSet);
                orderList.add(order);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderList;
    }
}
