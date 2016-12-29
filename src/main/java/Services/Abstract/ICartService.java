package Services.Abstract;

import Services.Models.Cart;

/**
 * Created by raxis on 29.12.2016.
 */
public interface ICartService {
    public void addToCart(Cart cart, String groceryid);
    public void removeFromCart(Cart cart, String groceryid);
}
