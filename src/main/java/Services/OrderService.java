package Services;

import DAO.Abstract.*;
import DAO.Concrete.*;
import DAO.Entities.*;
import Services.Exceptions.NoSavedInDbException;
import Services.ViewModels.OrderView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
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

    public OrderView formOrderView(HttpServletRequest req){
        IRepositoryOrder orderHandler = new OrderSql();
        IRepositoryOrderStatus orderStatusHandler = new OrderStatusSql();
        IRepositoryGroceryList groceryListHandler = new GroceryListSql();
        IRepositoryGrocery groceryHandler = new GrocerySql();
        Map<String,Integer> map = new HashMap<>();
        Map<String,String> statusMap = new HashMap<>();
        GroceryList groceryList = new GroceryList();

        UUID uuid = UUID.fromString(req.getParameter("orderid"));

        Order order = (Order) orderHandler.getOne(uuid);
        List<GroceryList> groceryLists = groceryListHandler.getListById(order.getGrocerylistid());
        OrderStatus orderStatus = (OrderStatus) orderStatusHandler.getOne(order.getOrderstatusid());
        List<OrderStatus> orderStatusList = orderStatusHandler.getAll();

        OrderView orderView = new OrderView();

        orderView.setId(order.getId().toString());
        orderView.setAddress(order.getAddress());
        orderView.setStatus(orderStatus.getStatus());
        orderView.setDate(order.getDatetime().toString());
        orderView.setFullName("");
        orderView.setPrice(order.getPrice().toString());

        for(GroceryList list:groceryLists){
            String str=((Grocery)groceryHandler.getOne(list.getGroceryId())).getName();
            map.put(str,Integer.valueOf(list.getQuantity()));
        }
        orderView.setGroceries(map);

        for(OrderStatus os : orderStatusList){
            statusMap.put(os.getId().toString(),os.getStatus());
        }

        orderView.setStatuses(statusMap);

        return orderView;
    }

    public List<OrderView> formOrderViewListAdmin(){
        List<OrderView> orderViewList = new ArrayList<>();
        IRepositoryOrder orderHandler = new OrderSql();
        IRepositoryGroceryList groceryListHandler = new GroceryListSql();
        IRepositoryOrderStatus orderStatusHandler = new OrderStatusSql();
        IRepositoryGrocery groceryHandler = new GrocerySql();
        IRepositoryUser userHandler = new UserSql();

        Order order = new Order();
        List<Order> orderList=orderHandler.getAll();

        for(Order repoOrder : orderList){
            OrderView orderView = new OrderView();
            Map<String,Integer> map = new HashMap<>();
            GroceryList groceryList = new GroceryList();
            List<GroceryList> groceryLists = groceryListHandler.getListById(repoOrder.getGrocerylistid());
            String orderStatus = ((OrderStatus)orderStatusHandler.getOne(repoOrder.getOrderstatusid())).getStatus();

            orderView.setId(repoOrder.getId().toString());
            orderView.setFullName(((User)userHandler.getOne(repoOrder.getUserid())).getEmail());
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

    public List<OrderView> formOrderViewList(User user){

        List<OrderView> orderViewList = new ArrayList<>();
        IRepositoryOrder orderHandler = new OrderSql();
        IRepositoryGroceryList groceryListHandler = new GroceryListSql();
        IRepositoryOrderStatus orderStatusHandler = new OrderStatusSql();
        IRepositoryGrocery groceryHandler = new GrocerySql();

        Order order = new Order();
        List<Order> orderList=orderHandler.getByUserId(user.getId());

        for(Order repoOrder : orderList){
            if(repoOrder.getOrderstatusid().toString().equals("1c8d12cf-6b0a-4168-ae2a-cb416cf30da5"))continue;
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

    public void updateOrderAdmin(String orderid, String statusid) throws NoSavedInDbException {
        IRepositoryOrder orderHandler = new OrderSql();

        Order order = (Order)orderHandler.getOne(UUID.fromString(orderid));

        order.setOrderstatusid(UUID.fromString(statusid));

        if(!orderHandler.update(order)){
            throw new NoSavedInDbException();
        }
    }

    public Order getOrder(HttpServletRequest req){
        IRepositoryOrder orderHandler = new OrderSql();

        UUID uuid = UUID.fromString(req.getParameter("orderid"));

        Order order = (Order) orderHandler.getOne(uuid);

        return order;
    }
}
