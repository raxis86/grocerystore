package Controllers.OrderControllers;

import Services.Exceptions.NoSavedInDbException;
import Services.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by raxis on 28.12.2016.
 */
public class OrderEdit extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(OrderEdit.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        OrderService orderService = new OrderService();

        req.setAttribute("order",orderService.formOrderView(req));

        RequestDispatcher rd = req.getRequestDispatcher("WEB-INF/orderedit.jsp");
        rd.forward(req,resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        OrderService orderService = new OrderService();

        try {
            orderService.updateOrderAdmin(req.getParameter("orderid"),req.getParameter("statusid"));

            req.setAttribute("orderlist",orderService.formOrderViewListAdmin());

            RequestDispatcher rd = req.getRequestDispatcher("WEB-INF/orderlist_admin.jsp");
            rd.forward(req,resp);
        } catch (NoSavedInDbException e) {
            RequestDispatcher rd = req.getRequestDispatcher("WEB-INF/savetodberror.jsp");
            rd.forward(req,resp);
        }

        /*IRepoOrder order = new Order().selectOne(UUID.fromString(req.getParameter("orderid")));

        order.setOrderstatusid(UUID.fromString("1c8d12cf-6b0a-4168-ae2a-cb416cf30da5"));

        order.update();*/

        RequestDispatcher rd = req.getRequestDispatcher("WEB-INF/orderlist_admin.jsp");
        rd.forward(req,resp);
    }
}
