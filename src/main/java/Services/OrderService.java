package Services;

import DAO.Abstract.IRepositoryGrocery;
import DAO.Abstract.IRepositoryGroceryList;
import DAO.Abstract.IRepositoryOrder;
import DAO.Abstract.IRepositoryOrderStatus;
import DAO.Concrete.GroceryListSql;
import DAO.Concrete.GrocerySql;
import DAO.Concrete.OrderSql;
import DAO.Concrete.OrderStatusSql;
import DAO.Entities.*;
import Services.Exceptions.NoSavedInDbException;
import Services.ViewModels.OrderView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
import java.util.*;

/**
 * Created by raxis on 27.12.2016.
 */
public class OrderService {
    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    public Order createOrder(User user, Cart cart) throws NoSavedInDbException {
        IRepositoryOrder orderHandler = new OrderSql();

        Order order = new Order();
        order.setId(UUID.randomUUID());
        order.setUserid(user.getId());
        order.setOrderstatusid(UUID.fromString("c24be575-187f-4d41-82ee-ff874764b829"));
        order.setPrice(cart.computeTotalPrice());
        order.setDatetime(new Date());
        order.setGrocerylistid(UUID.randomUUID());
        order.setAddress(user.getAddress());

        if(!orderHandler.create(order)){
            throw new NoSavedInDbException();
        }

        return order;
    }

    public List<OrderView> formOrderViewList(User user){

        List<OrderView> orderViewList = new ArrayList<>();
        IRepositoryOrder orderHandler = new OrderSql();
        IRepositoryGroceryList groceryListHandler = new GroceryListSql();
        IRepositoryOrderStatus orderStatusHandler = new OrderStatusSql();
        IRepositoryGrocery groceryHandler = new GrocerySql();

        Order order = new Order();
        List<Order> orderList=orderHandler.getByUserId(user.getId());

        for(Order repoOrder : orderList){
            OrderView orderView = new OrderView();
            Map<String,Integer> map = new HashMap<>();
            GroceryList groceryList = new GroceryList();
            List<GroceryList> groceryLists = groceryListHandler.getListById(repoOrder.getGrocerylistid());
            String orderStatus = ((OrderStatus)orderStatusHandler.getOne(repoOrder.getOrderstatusid())).getStatus();

            orderView.setId(repoOrder.getId().toString());
            orderView.setFullName(String.format("%s %s %s",user.getLastName(),user.getName(),user.getSurName()));
            orderView.setStatus(orderStatus);
            orderView.setDate(repoOrder.getDatetime().toString());
            orderView.setAddress(repoOrder.getAddress());
            orderView.setPrice(repoOrder.getPrice().toString());
            for(GroceryList list:groceryLists){
                String str=((Grocery)groceryHandler.getOne(list.getGroceryId())).getName();
                map.put(str,Integer.valueOf(list.getQuantity()));
            }
            orderView.setGroceries(map);

            orderViewList.add(orderView);
        }

        return orderViewList;
    }

    public void updateOrder(String orderid) throws NoSavedInDbException {
        IRepositoryOrder orderHandler = new OrderSql();

        Order order = (Order)orderHandler.getOne(UUID.fromString(orderid));

        order.setOrderstatusid(UUID.fromString("1c8d12cf-6b0a-4168-ae2a-cb416cf30da5"));

        if(!orderHandler.update(order)){
            throw new NoSavedInDbException();
        }
    }
}
