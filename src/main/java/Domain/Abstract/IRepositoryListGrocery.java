package Domain.Abstract;

import Domain.Entities.ListGrocery;
import Domain.Exceptions.ListGroceryException;

import java.util.List;
import java.util.UUID;

/**
 * Created by raxis on 27.12.2016.
 */
public interface IRepositoryListGrocery extends IRepository<ListGrocery,UUID> {
    public List<ListGrocery> getListById(UUID id) throws ListGroceryException;
    public List<ListGrocery> getListByGroceryId(UUID id) throws ListGroceryException;
}
