package Services.Concrete;

import Domain.Abstract.IRepositoryGrocery;
import Domain.Concrete.GrocerySql;
import Domain.Entities.Grocery;
import Services.Abstract.ICartService;
import Services.Models.Cart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

/**
 * Created by raxis on 29.12.2016.
 */
public class CartService implements ICartService {
    private static final Logger logger = LoggerFactory.getLogger(CartService.class);

    private IRepositoryGrocery groceryHandler;

    public CartService(){
        this.groceryHandler=new GrocerySql();
    }

    @Override
    public void addToCart(Cart cart, String groceryid) {
        Grocery grocery = groceryHandler.getOne(UUID.fromString(groceryid));
        if(grocery!=null){
            cart.addItem(grocery,1);
        }
    }

    @Override
    public void removeFromCart(Cart cart, String groceryid) {
        Grocery grocery = groceryHandler.getOne(UUID.fromString(groceryid));
        if(grocery!=null){
            cart.removeItem(grocery);
        }
    }
}
