package Services;

import Domain.Abstract.IRepositoryGrocery;
import Domain.Concrete.GrocerySql;
import Domain.Entities.Grocery;
import Services.Models.Cart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.UUID;

/**
 * Created by raxis on 27.12.2016.
 * Класс для работы с корзиной покупок
 */
@Deprecated
public class CartService {
    private static final Logger logger = LoggerFactory.getLogger(CartService.class);

    private IRepositoryGrocery groceryHandler;

    public CartService(){
        this.groceryHandler=new GrocerySql();
    }

    /**
     * Добавление продукта в корзину
     * @param groceryid
     * @param req
     */
    public void addToCart(String groceryid, HttpServletRequest req){
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

    public void clearCart(HttpServletRequest req){
        HttpSession session=req.getSession();
        Cart cart = (Cart) session.getAttribute("cart");
        cart.clear();
    }

}
