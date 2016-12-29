package Services.Concrete;

import Domain.Abstract.IRepositoryGroceryList;
import Domain.Concrete.GroceryListSql;
import Domain.Entities.Grocery;
import Domain.Entities.GroceryList;
import Domain.Entities.Order;
import Services.Abstract.IGroceryListService;
import Services.Exceptions.NoSavedInDbException;
import Services.Models.Cart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Created by raxis on 29.12.2016.
 */
public class GroceryListService implements IGroceryListService {
    private static final Logger logger = LoggerFactory.getLogger(GroceryListService.class);

    private IRepositoryGroceryList groceryListHandler;

    public GroceryListService(){
        this.groceryListHandler = new GroceryListSql();
    }


    @Override
    public void createGroceryList(Cart cart, Order order) throws NoSavedInDbException {
        for(Map.Entry entry : cart.getMap().entrySet()){
            GroceryList groceryList = new GroceryList();
            groceryList.setId(order.getGrocerylistid());
            groceryList.setGroceryId(((Grocery)entry.getKey()).getId());
            groceryList.setQuantity((int)entry.getValue());

            if(!groceryListHandler.create(groceryList)){
                throw new NoSavedInDbException();
            }
        }
    }
}
