package Controllers.OrderControllers;

import Models.Grocery;
import Models.GroceryList;
import Models.Order;
import Models.User;
import Services.Cart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by raxis on 26.12.2016.
 */
public class OrderAdd extends HttpServlet{
    private static final Logger logger = LoggerFactory.getLogger(OrderAdd.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        HttpSession session=req.getSession();

        Cart cart = (Cart) session.getAttribute("cart");
        User user = (User) session.getAttribute("user");

        if((cart!=null)&&(user!=null)){
            req.setAttribute("user",user);
            req.setAttribute("cart",cart);
            req.setAttribute("totalprice",cart.computeTotalPrice().toString());
            RequestDispatcher rd = req.getRequestDispatcher("WEB-INF/orderadd.jsp");
            rd.forward(req,resp);
        }
        else {
            RequestDispatcher rd = req.getRequestDispatcher("/index.jsp");
            rd.forward(req,resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        HttpSession session=req.getSession();
        Cart cart = (Cart) session.getAttribute("cart");
        User user = (User) session.getAttribute("user");

        if((cart!=null)&&(user!=null)){
            user.setName(req.getParameter("name"));
            user.setLastName(req.getParameter("lastname"));
            user.setSurName(req.getParameter("surname"));
            user.setAddress(req.getParameter("address"));
            user.setPhone(req.getParameter("phone"));
            user.update();

            UUID uuid = UUID.randomUUID();

            Order order = new Order();
            order.setId(UUID.randomUUID());
            order.setUserid(user.getId());
            order.setOrderstatusid(UUID.fromString("c24be575-187f-4d41-82ee-ff874764b829"));
            order.setPrice(cart.computeTotalPrice());
            order.setDatetime(new Date());
            order.setGrocerylistid(uuid);
            order.setAddress(user.getAddress());
            order.insert();

            for(Map.Entry entry : cart.getMap().entrySet()){
                GroceryList groceryList = new GroceryList();
                groceryList.setId(uuid);
                groceryList.setGroceryId(((Grocery)entry.getKey()).getId());
                groceryList.setQuantity((int)entry.getValue());
                groceryList.insert();
            }

            RequestDispatcher rd = req.getRequestDispatcher("WEB-INF/ordersuccess.jsp");
            rd.forward(req,resp);
        }
    }
}
