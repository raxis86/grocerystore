package Services;

import DAO.Abstract.*;
import DAO.Concrete.*;
import DAO.Entities.*;
import Services.Exceptions.NoSavedInDbException;
import Services.ViewModels.OrderView;
import org.mockito.internal.matchers.Or;
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

    private IRepositoryOrder orderHandler;
    private IRepositoryOrderStatus orderStatusHandler;
    private IRepositoryGroceryList groceryListHandler;
    private IRepositoryGrocery groceryHandler;
    private IRepositoryUser userHandler;

    public OrderService(){
        this.orderHandler = new OrderSql();
        this.orderStatusHandler = new OrderStatusSql();
        this.groceryListHandler = new GroceryListSql();
        this.groceryHandler = new GrocerySql();
        this.userHandler = new UserSql();
    }

    public Order createOrder(User user, Cart cart) throws NoSavedInDbException {

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

    private OrderView formOrderView(UUID orderid, String userName){
        Map<String,Integer> map = new HashMap<>();
        Map<String,String> statusMap = new HashMap<>();

        Order order = orderHandler.getOne(orderid);
        List<GroceryList> groceryLists = groceryListHandler.getListById(order.getGrocerylistid());
        OrderStatus orderStatus = (OrderStatus) orderStatusHandler.getOne(order.getOrderstatusid());
        List<OrderStatus> orderStatusList = orderStatusHandler.getAll();

        OrderView orderView = new OrderView();

        orderView.setId(order.getId().toString());
        orderView.setAddress(order.getAddress());
        orderView.setStatus(orderStatus.getStatus());
        orderView.setDate(order.getDatetime().toString());
        orderView.setFullName(userName);
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

    public OrderView formOrderView(HttpServletRequest req){
        return formOrderView(UUID.fromString(req.getParameter("orderid")),"");
    }

    public List<OrderView> formOrderViewListAdmin(){
        List<OrderView> orderViewList = new ArrayList<>();
        List<Order> orderList=orderHandler.getAll();

        for(Order repoOrder : orderList){
            orderViewList.add(formOrderView(repoOrder.getId(),((User)userHandler.getOne(repoOrder.getUserid())).getEmail()));
        }

        return orderViewList;
    }

    public List<OrderView> formOrderViewList(User user){
        List<OrderView> orderViewList = new ArrayList<>();
        List<Order> orderList=orderHandler.getByUserId(user.getId());

        for(Order repoOrder : orderList){
            if(!repoOrder.getOrderstatusid().toString().equals("1c8d12cf-6b0a-4168-ae2a-cb416cf30da5")){
                orderViewList.add(formOrderView(repoOrder.getId(),String.format("%s %s %s",user.getLastName(),user.getName(),user.getSurName())));
            }
        }

        return orderViewList;
    }

    public void updateOrder(String orderid) throws NoSavedInDbException {

        Order order = (Order)orderHandler.getOne(UUID.fromString(orderid));

        order.setOrderstatusid(UUID.fromString("1c8d12cf-6b0a-4168-ae2a-cb416cf30da5"));

        if(!orderHandler.update(order)){
            throw new NoSavedInDbException();
        }
    }

    public void updateOrderAdmin(String orderid, String statusid) throws NoSavedInDbException {

        Order order = (Order)orderHandler.getOne(UUID.fromString(orderid));

        order.setOrderstatusid(UUID.fromString(statusid));

        if(!orderHandler.update(order)){
            throw new NoSavedInDbException();
        }
    }

    public Order getOrder(HttpServletRequest req){

        UUID uuid = UUID.fromString(req.getParameter("orderid"));

        Order order = (Order) orderHandler.getOne(uuid);

        return order;
    }
}
