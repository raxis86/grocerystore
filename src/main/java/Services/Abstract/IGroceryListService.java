package Services.Abstract;

import Domain.Entities.Order;
import Services.Exceptions.NoSavedInDbException;
import Services.Models.Cart;

/**
 * Created by raxis on 29.12.2016.
 */
public interface IGroceryListService {
    public void createGroceryList(Cart cart, Order order) throws NoSavedInDbException;
}