package Controllers.OrderControllers;

import Domain.Entities.User;
import Services.Abstract.ICartService;
import Services.Abstract.IGroceryListService;
import Services.Abstract.IOrderService;
import Services.Abstract.IUserService;
import Services.Concrete.CartService;
import Services.Concrete.GroceryListService;
import Services.Concrete.OrderService;
import Services.Concrete.UserService;
import Services.Exceptions.NoSavedInDbException;
import Services.Models.Cart;
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

        /*CartService cartService = new CartService();
        UserService userService = new UserService();

        if((cartService.cartFromSession(req)!=null)&&(userService.userFromSession(req)!=null)){
            req.setAttribute("user",userService.userFromSession(req));
            req.setAttribute("cart",cartService.cartFromSession(req));
            req.setAttribute("totalprice",cartService.cartFromSession(req).computeTotalPrice().toString());
            RequestDispatcher rd = req.getRequestDispatcher("WEB-INF/orderadd.jsp");
            rd.forward(req,resp);
        }
        else {
            RequestDispatcher rd = req.getRequestDispatcher("/index.jsp");
            rd.forward(req,resp);
        }*/

        HttpSession session = req.getSession();
        Cart cart = (Cart)session.getAttribute("cart");
        User user = (User)session.getAttribute("user");

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

        /*CartService cartService = new CartService();
        UserService userService = new UserService();
        OrderService orderService = new OrderService();
        GroceryListService groceryListService = new GroceryListService();

        if((cartService.cartFromSession(req)!=null)&&(userService.userFromSession(req)!=null)){
            try {
                userService.updateUser(userService.userFromSession(req),
                        req.getParameter("name"),
                        req.getParameter("lastname"),
                        req.getParameter("surname"),
                        req.getParameter("address"),
                        req.getParameter("phone"));

                groceryListService.createGroceryList(cartService.cartFromSession(req),
                        orderService.createOrder(userService.userFromSession(req),cartService.cartFromSession(req)));

                cartService.clearCart(req);

                RequestDispatcher rd = req.getRequestDispatcher("WEB-INF/ordersuccess.jsp");
                rd.forward(req,resp);
            } catch (NoSavedInDbException e) {
                RequestDispatcher rd = req.getRequestDispatcher("WEB-INF/savetodberror.jsp");
                rd.forward(req,resp);
            }
        }*/

        IOrderService orderService = new OrderService();
        IGroceryListService groceryListService = new GroceryListService();
        IUserService userService = new UserService();

        HttpSession session = req.getSession();
        Cart cart = (Cart)session.getAttribute("cart");
        User user = (User)session.getAttribute("user");

        if((cart!=null)&&(user!=null)){
            try {
                userService.updateUser(user,
                        req.getParameter("name"),
                        req.getParameter("lastname"),
                        req.getParameter("surname"),
                        req.getParameter("address"),
                        req.getParameter("phone"));

                groceryListService.createGroceryList(cart,orderService.createOrder(user,cart));

                cart.clear();

                RequestDispatcher rd = req.getRequestDispatcher("WEB-INF/ordersuccess.jsp");
                rd.forward(req,resp);
            } catch (NoSavedInDbException e) {
                RequestDispatcher rd = req.getRequestDispatcher("WEB-INF/savetodberror.jsp");
                rd.forward(req,resp);
            }
        }

    }
}
