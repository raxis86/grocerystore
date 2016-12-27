package DAO.Concrete;

import DAO.Abstract.IRepositoryOrder;
import DAO.Entities.Order;
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
public class OrderSql implements IRepositoryOrder<Order,UUID> {
    private static final Logger logger = LoggerFactory.getLogger(OrderSql.class);

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
    public List<Order> getAll() {
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
    public Order getOne(UUID id) {
        Order order = null;
        try(PreparedStatement statement = Tool.getConnection().prepareStatement(ORDER_PREP_SELECTONE_QUERY)) {
            statement.setObject(1,id.toString());
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                order = new Order();
                fillOrder(order,resultSet);
            }
        } catch (SQLException e) {
            logger.error("Cant getOne Order!", e);
            e.printStackTrace();
        }
        return order;
    }

    @Override
    public boolean create(Order entity) {
        try(PreparedStatement statement = Tool.getConnection().prepareStatement(ORDER_PREP_INSERT_QUERY);) {
            statement.setObject(1,entity.getId().toString());
            statement.setObject(2,entity.getUserid().toString());
            statement.setObject(3,entity.getOrderstatusid().toString());
            statement.setObject(4,entity.getPrice());
            statement.setObject(5,entity.getDatetime());
            statement.setObject(6,entity.getGrocerylistid().toString());
            statement.setObject(7,entity.getAddress());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean delete(UUID id) {
        try(PreparedStatement statement = Tool.getConnection().prepareStatement(ORDER_PREP_DELETE_QUERY);) {
            statement.setObject(1,id.toString());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean update(Order entity) {
        try(PreparedStatement statement=Tool.getConnection().prepareStatement(ORDER_PREP_UPDATE_QUERY);) {
            statement.setObject(1,entity.getUserid().toString());
            statement.setObject(2,entity.getOrderstatusid().toString());
            statement.setObject(3,entity.getPrice());
            statement.setObject(4,entity.getDatetime());
            statement.setObject(5,entity.getGrocerylistid().toString());
            statement.setObject(6,entity.getAddress());
            statement.setObject(7,entity.getId().toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public List<Order> getByUserId(UUID userid) {
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
