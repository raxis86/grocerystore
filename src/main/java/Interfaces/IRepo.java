package Interfaces;

import java.util.List;
import java.util.UUID;

/**
 * Created by raxis on 23.12.2016.
 */
public interface IRepo<T> {
    public List<T> select();
    public T selectOne(UUID id);
    public void insert();
    public void delete();
    public void update();
}
