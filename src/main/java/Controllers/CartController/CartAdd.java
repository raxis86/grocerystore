package Controllers.CartController;

import Models.Grocery;
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
import java.util.UUID;

/**
 * Created by raxis on 25.12.2016.
 */
public class CartAdd extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(CartAdd.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        Grocery grocery = new Grocery().select(UUID.fromString(req.getParameter("groceryid")));

        if(grocery!=null){
            HttpSession session = req.getSession();
            Cart cart = (Cart)session.getAttribute("cart");
            if(cart==null){
                cart=new Cart();
                session.setAttribute("cart",cart);
            }
            cart.addItem(grocery,1);
        }
        //String str=req.getParameter("returnurl");
        //RequestDispatcher rd=req.getRequestDispatcher(req.getParameter("returnurl"));
        RequestDispatcher rd=req.getRequestDispatcher("/GroceryList");
        rd.forward(req,resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        HttpSession session = req.getSession();
        //session.setAttribute("");
    }

}
