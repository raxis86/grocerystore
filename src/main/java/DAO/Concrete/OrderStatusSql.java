package DAO.Concrete;

import DAO.Abstract.IRepositoryOrderStatus;
import DAO.Entities.OrderStatus;
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
public class OrderStatusSql implements IRepositoryOrderStatus {
    private static final Logger logger = LoggerFactory.getLogger(OrderStatusSql.class);

    @Override
    public List<OrderStatus> getAll() {
        List<OrderStatus> orderStatusList = new ArrayList<>();
        try(Statement statement = Tool.getConnection().createStatement();) {
            ResultSet resultSet=statement.executeQuery(ORDERSTATUS_SELECTALL_QUERY);
            while (resultSet.next()){
                OrderStatus orderStatus = new OrderStatus();
                orderStatus.setId(UUID.fromString(resultSet.getString("ID")));
                orderStatus.setStatus(resultSet.getString("STATUS"));
                orderStatusList.add(orderStatus);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderStatusList;
    }

    @Override
    public OrderStatus getOne(UUID id) {
        OrderStatus orderStatus = null;
        try(PreparedStatement statement = Tool.getConnection().prepareStatement(ORDERSTATUS_PREP_SELECTONE_QUERY)) {
            statement.setObject(1,id.toString());
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                orderStatus = new OrderStatus();
                orderStatus.setId(UUID.fromString(resultSet.getString("ID")));
                orderStatus.setStatus(resultSet.getString("STATUS"));
            }
        } catch (SQLException e) {
            logger.error("Cant getOne OrderStatusSql!", e);
            e.printStackTrace();
        }
        return orderStatus;
    }

    @Override
    public boolean create(OrderStatus entity) {
        try(PreparedStatement statement = Tool.getConnection().prepareStatement(ORDERSTATUS_PREP_INSERT_QUERY);) {
            statement.setObject(1,entity.getId().toString());
            statement.setObject(2,entity.getStatus());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean delete(UUID id) {
        try(PreparedStatement statement = Tool.getConnection().prepareStatement(ORDERSTATUS_PREP_DELETE_QUERY);) {
            statement.setObject(1,id.toString());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean update(OrderStatus entity) {
        try(PreparedStatement statement=Tool.getConnection().prepareStatement(ORDERSTATUS_PREP_UPDATE_QUERY);) {
            statement.setObject(1,entity.getStatus());
            statement.setObject(2,entity.getId().toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
