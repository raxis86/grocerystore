package Services.Abstract;

import Domain.Entities.Order;
import Domain.Entities.User;
import Services.Exceptions.NoSavedInDbException;
import Services.Models.Cart;
import Services.ViewModels.OrderView;

import java.util.List;

/**
 * Created by raxis on 29.12.2016.
 */
public interface IOrderService {
    public Order createOrder(User user, Cart cart) throws NoSavedInDbException;
    public OrderView formOrderView(String orderid);
    public List<OrderView> formOrderViewListAdmin();
    public List<OrderView> formOrderViewList(User user);
    public void updateOrder(String orderid) throws NoSavedInDbException;
    public void updateOrderAdmin(String orderid, String statusid) throws NoSavedInDbException;
    public Order getOrder(String orderid);
}
