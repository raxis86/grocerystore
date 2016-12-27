package Models;

import Interfaces.IRepoOrderStatus;
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
public class OrderStatus implements IRepoOrderStatus<OrderStatus> {
    private static final Logger logger = LoggerFactory.getLogger(OrderStatus.class);

    private UUID id;
    private String status;

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public void setId(UUID id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public List<OrderStatus> select() {
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
    public OrderStatus selectOne(UUID id) {
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
            logger.error("Cant selectOne OrderStatus!", e);
            e.printStackTrace();
        }
        return orderStatus;
    }

    @Override
    public void insert() {
        try(PreparedStatement statement = Tool.getConnection().prepareStatement(ORDERSTATUS_PREP_INSERT_QUERY);) {
            statement.setObject(1,this.id.toString());
            statement.setObject(2,this.status);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete() {
        try(PreparedStatement statement = Tool.getConnection().prepareStatement(ORDERSTATUS_PREP_DELETE_QUERY);) {
            statement.setObject(1,this.id.toString());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update() {
        try(PreparedStatement statement=Tool.getConnection().prepareStatement(ORDERSTATUS_PREP_UPDATE_QUERY);) {
            statement.setObject(1,this.status);
            statement.setObject(2,this.id.toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
