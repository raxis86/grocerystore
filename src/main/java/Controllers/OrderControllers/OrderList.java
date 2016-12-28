package Controllers.OrderControllers;

import Services.Exceptions.NoSavedInDbException;
import Services.OrderService;
import Services.UserService;
import Services.ViewModels.OrderView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * Created by raxis on 26.12.2016.
 */
public class OrderList extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(OrderList.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        UserService userService = new UserService();
        OrderService orderService = new OrderService();

        if(userService.userFromSession(req)!=null){

            req.setAttribute("orderlist",orderService.formOrderViewList(userService.userFromSession(req)));

            RequestDispatcher rd = req.getRequestDispatcher("WEB-INF/orderlist.jsp");
            rd.forward(req,resp);

        }
        else {
            RequestDispatcher rd = req.getRequestDispatcher("WEB-INF/deadend.jsp");
            rd.forward(req,resp);
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        OrderService orderService = new OrderService();

        try {
            orderService.updateOrder(req.getParameter("orderid"));
            RequestDispatcher rd = req.getRequestDispatcher("WEB-INF/grocerylist.jsp");
            rd.forward(req,resp);
        } catch (NoSavedInDbException e) {
            RequestDispatcher rd = req.getRequestDispatcher("WEB-INF/savetodberror.jsp");
            rd.forward(req,resp);
        }

        RequestDispatcher rd = req.getRequestDispatcher("WEB-INF/grocerylist.jsp");
        rd.forward(req,resp);
    }
}
