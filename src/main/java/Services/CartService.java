package Services;

import DAO.Abstract.IRepositoryGrocery;
import DAO.Concrete.GrocerySql;
import DAO.Entities.Grocery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.UUID;

/**
 * Created by raxis on 27.12.2016.
 */
public class CartService {
    private static final Logger logger = LoggerFactory.getLogger(CartService.class);

    public void addToCart(String groceryid, HttpServletRequest req){
        IRepositoryGrocery groceryHandler = new GrocerySql();
        Grocery grocery = (Grocery) groceryHandler.getOne(UUID.fromString(groceryid));

        if(grocery!=null){
            HttpSession session = req.getSession();
            Cart cart = (Cart)session.getAttribute("cart");
            if(cart==null){
                cart=new Cart();
                session.setAttribute("cart",cart);
            }
            cart.addItem(grocery,1);
        }
    }

    public void removeFromCart(String groceryid, HttpServletRequest req){
        IRepositoryGrocery groceryHandler = new GrocerySql();
        Grocery grocery = (Grocery) groceryHandler.getOne(UUID.fromString(groceryid));

        if(grocery!=null){
            HttpSession session = req.getSession();
            Cart cart = (Cart)session.getAttribute("cart");
            cart.removeItem(grocery);
        }
    }

    public Cart cartFromSession(HttpServletRequest req){
        HttpSession session=req.getSession();
        Cart cart = (Cart) session.getAttribute("cart");
        return cart;
    }

}
