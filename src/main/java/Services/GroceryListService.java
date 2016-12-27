package Services;

import DAO.Abstract.IRepositoryGroceryList;
import DAO.Concrete.GroceryListSql;
import DAO.Entities.Grocery;
import DAO.Entities.GroceryList;
import DAO.Entities.Order;
import Services.Exceptions.NoSavedInDbException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.UUID;

/**
 * Created by raxis on 27.12.2016.
 */
public class GroceryListService {
    private static final Logger logger = LoggerFactory.getLogger(GroceryListService.class);

    public void createGroceryList(Cart cart, Order order) throws NoSavedInDbException {
        IRepositoryGroceryList groceryListHandler = new GroceryListSql();

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
