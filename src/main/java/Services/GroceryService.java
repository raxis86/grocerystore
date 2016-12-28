package Services;

import DAO.Abstract.IRepositoryGrocery;
import DAO.Abstract.IRepositoryGroceryList;
import DAO.Concrete.GroceryListSql;
import DAO.Concrete.GrocerySql;
import DAO.Entities.Grocery;
import DAO.Entities.GroceryList;
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

    public Grocery getGrocery(HttpServletRequest req){
        IRepositoryGrocery groceryHandler = new GrocerySql();

        UUID uuid = UUID.fromString(req.getParameter("groceryid"));

        Grocery grocery = (Grocery)  groceryHandler.getOne(uuid);

        return grocery;
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

    public void groceryDelete(HttpServletRequest req) throws NoSavedInDbException {
        IRepositoryGrocery groceryHandler = new GrocerySql();
        IRepositoryGroceryList groceryListHandler = new GroceryListSql();

        Grocery grocery = (Grocery) groceryHandler.getOne(UUID.fromString(req.getParameter("groceryid")));
        List<GroceryList> groceryLists = groceryListHandler.getListByGroceryId(grocery.getId());

        if(!groceryHandler.delete(grocery.getId())){
            throw new NoSavedInDbException();
        }

        for(GroceryList gl : groceryLists){
            groceryListHandler.delete(gl.getId());
        }
    }

    public Message groceryUpdate(HttpServletRequest req) throws NoSavedInDbException {
        IRepositoryGrocery groceryHandler = new GrocerySql();

        Grocery grocery = (Grocery) groceryHandler.getOne(UUID.fromString(req.getParameter("groceryid")));

        if(grocery!=null){
            grocery.setName(req.getParameter("name"));
            grocery.setPrice(new BigDecimalStringConverter().fromString(req.getParameter("price")));
            grocery.setQuantity(Integer.parseInt(req.getParameter("quantity")));

            if(!groceryHandler.update(grocery)){
                throw new NoSavedInDbException();
            }

            return new Message("Изменения успешно сохранены!", Message.Status.OK);
        }

        return new Message("Продукт не найден в базе!", Message.Status.ERROR);
    }
}
