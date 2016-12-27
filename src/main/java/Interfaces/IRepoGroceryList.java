package Interfaces;

import java.util.List;
import java.util.UUID;

/**
 * Created by raxis on 26.12.2016.
 */
@Deprecated
public interface IRepoGroceryList<T> extends IRepo<T>{
    public List<T> select(UUID id);
    public UUID getId();
    public void setId(UUID id);
    public UUID getGroceryId();
    public void setGroceryId(UUID groceryId);
    public int getQuantity();
    public void setQuantity(int quantity);
}
