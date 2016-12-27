package Services;

import DAO.Abstract.IRepositoryGrocery;
import DAO.Concrete.GrocerySql;
import DAO.Entities.Grocery;
import Services.Exceptions.NoSavedInDbException;
import javafx.util.converter.BigDecimalStringConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.UUID;

/**
 * Created by raxis on 27.12.2016.
 */
public class GroceryService {
    private static final Logger logger = LoggerFactory.getLogger(GroceryService.class);

    public List<Grocery> getGroceryList(){
        IRepositoryGrocery groceryHandler = new GrocerySql();
        return groceryHandler.getAll();
    }

    public void groceryCreate(HttpServletRequest req) throws NoSavedInDbException {
        IRepositoryGrocery groceryHandler = new GrocerySql();
        Grocery grocery = new Grocery();

        grocery.setId(UUID.randomUUID());
        grocery.setIscategory(false);
        grocery.setParentid(new UUID(0L,0L));
        grocery.setName(req.getParameter("name"));
        grocery.setPrice(new BigDecimalStringConverter().fromString(req.getParameter("price")));
        grocery.setQuantity(Integer.parseInt(req.getParameter("quantity")));

        if(!groceryHandler.create(grocery)){
            throw new NoSavedInDbException();
        }
    }
}
