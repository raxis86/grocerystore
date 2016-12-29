package Domain.Abstract;

import Domain.Entities.GroceryList;

import java.util.List;
import java.util.UUID;

/**
 * Created by raxis on 27.12.2016.
 */
public interface IRepositoryGroceryList extends IRepository<GroceryList,UUID> {
    public List<GroceryList> getListById(UUID id);
    public List<GroceryList> getListByGroceryId(UUID id);
}
