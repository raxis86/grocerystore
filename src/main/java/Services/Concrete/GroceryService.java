package Services.Concrete;

import Domain.Abstract.IRepositoryGrocery;
import Domain.Abstract.IRepositoryGroceryList;
import Domain.Concrete.GroceryListSql;
import Domain.Concrete.GrocerySql;
import Domain.Entities.Grocery;
import Domain.Entities.GroceryList;
import Services.Abstract.IGroceryService;
import Services.Exceptions.NoSavedInDbException;
import Services.Models.Message;
import javafx.util.converter.BigDecimalStringConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.UUID;

/**
 * Created by raxis on 29.12.2016.
 */
public class GroceryService implements IGroceryService {
    private static final Logger logger = LoggerFactory.getLogger(GroceryService.class);

    private IRepositoryGrocery groceryHandler;
    private IRepositoryGroceryList groceryListHandler;

    public GroceryService(){
        this.groceryHandler = new GrocerySql();
        this.groceryListHandler = new GroceryListSql();
    }


    @Override
    public List<Grocery> getGroceryList() {
        return groceryHandler.getAll();
    }

    @Override
    public Grocery getGrocery(String groceryid) {
        return groceryHandler.getOne(UUID.fromString(groceryid));
    }

    @Override
    public void groceryCreate(String name, String price, String quantity) throws NoSavedInDbException {
        Grocery grocery = new Grocery();

        grocery.setId(UUID.randomUUID());
        grocery.setIscategory(false);
        grocery.setParentid(new UUID(0L,0L));
        grocery.setName(name);
        grocery.setPrice(new BigDecimalStringConverter().fromString(price));
        grocery.setQuantity(Integer.parseInt(quantity));

        if(!groceryHandler.create(grocery)){
            throw new NoSavedInDbException();
        }
    }

    @Override
    public void groceryDelete(String groceryid) throws NoSavedInDbException {
        Grocery grocery = groceryHandler.getOne(UUID.fromString(groceryid));
        List<GroceryList> groceryLists = groceryListHandler.getListByGroceryId(grocery.getId());

        if(!groceryHandler.delete(grocery.getId())){
            throw new NoSavedInDbException();
        }

        for(GroceryList gl : groceryLists){
            groceryListHandler.delete(gl.getId());
        }
    }

    @Override
    public Message groceryUpdate(String groceryid, String name, String price, String quantity) throws NoSavedInDbException {
        Grocery grocery = groceryHandler.getOne(UUID.fromString(groceryid));

        if(grocery!=null){
            grocery.setName(name);
            grocery.setPrice(new BigDecimalStringConverter().fromString(price));
            grocery.setQuantity(Integer.parseInt(quantity));

            if(!groceryHandler.update(grocery)){
                throw new NoSavedInDbException();
            }

            return new Message("Изменения успешно сохранены!", Message.Status.OK);
        }

        return new Message("Продукт не найден в базе!", Message.Status.ERROR);
    }
}
