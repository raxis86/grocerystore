package Services.Abstract;

import Domain.Entities.Grocery;
import Services.Exceptions.NoSavedInDbException;
import Services.Models.Message;

import java.util.List;

/**
 * Created by raxis on 29.12.2016.
 */
public interface IGroceryService {
    public List<Grocery> getGroceryList();
    public Grocery getGrocery(String groceryid);
    public void groceryCreate(String name, String price, String quantity) throws NoSavedInDbException;
    public void groceryDelete(String groceryid) throws NoSavedInDbException;
    public Message groceryUpdate(String groceryid, String name, String price, String quantity) throws NoSavedInDbException;
}
