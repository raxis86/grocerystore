package Controllers.CartController;

import Services.Cart;
import Services.CartService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.UUID;

/**
 * Created by raxis on 25.12.2016.
 */
public class CartRemove extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(CartRemove.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        CartService cartService = new CartService();

        cartService.removeFromCart(req.getParameter("groceryid"),req);

        RequestDispatcher rd=req.getRequestDispatcher("/CartList");
        rd.forward(req,resp);
    }
}
